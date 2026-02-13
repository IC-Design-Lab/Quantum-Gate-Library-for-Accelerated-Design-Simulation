package QuantumStateUnit.GateArchitecture.FixedPointGatePool.MeasurementGate.Components

import FixedPointUnit._
import QuantumStateUnit.GateArchitecture.QGPMuxLayer
import QuantumStateUnit.OtherComponents.PsuedoRandomGenerator.LinearCongruentialGenerator
import chisel3._
import chisel3.util.ShiftRegister

import scala.math.pow

class CompareWithRandom(val bw: Int, val pointlocation : Int) extends Module {
  val io = IO(new Bundle {
    val in_probability  =  Input(SInt(bw.W))
    val in_seed         =  Input(UInt(32.W)) //32 bit number
    val in_en           =  Input(Bool()) //0 stops updating output AND feeds a new seed
    val in_sel          =  Input(Bool()) //Determines if 0 or 1 takes the lower number / has the probability of input
    val in_valid        =  Input(Bool())
    val out_valid       = Output(Bool())
    val out_value       = Output(Bool())
  })
  //An adjustable 1
  val one             = "h4000000000000000".U(63, 64 - bw).asSInt //1


  //pseudo random number generator
  val prev        = RegInit(0.B)
  prev           := io.in_en
  val RNG         = Module(new LinearCongruentialGenerator(32, 32, 5, 1)) //32 bit Linear Congruent RNG
  RNG.io.in_next := io.in_en //continues down current sequence until enabled
  RNG.io.in_feed := io.in_en && !prev //changes sequence when enabled
  RNG.io.in_seed := io.in_seed//initialization

  /*
  RNG's current range is between 0 - 63
    1. adjust range from 0 - 63 to 1 - 64 <- 0 is outside the proper range of comparison
    2. move range from 1 - 64 to 0.01 - 1 <- The correct format, so it could be comparable
  This can be expressed as:      ( RNG + 1 ) * ( ( 1 / 100 ) * ( 100 / 64 ) ) = ( RNG + 1 ) * ( 1 / 64 )
    3. The output number will become outside of range, so the bitwidth will be doubled temporarly
          |-> new range 0000 0000 . 0000 0000
                |-> ex) base 10: 64 = base 2: 0100 0000 -> 0100 0000 . 0000 0000 -> 0000 0001 . 0000 0000
   */
  val RNGnumber   = RegInit(0.U(bw.W))
  if(bw < 6){
    RNGnumber := RNG.io.out_Value(6,bw) // If the bitwidth is 2 bits for some unforsaken reason
  }else {
    RNGnumber := RNG.io.out_Value(6, 0) ## 0.U((bw-7).W)
  }

  //The output that tells when 0 or 1
  val determinedValue = RegInit(0.B)
  io.out_value := determinedValue
  io.out_valid := ShiftRegister(io.in_valid, 1)
    //The chosen value will get the lower value0369
    //              input is lower number         enable
    when((RNGnumber < io.in_probability.asUInt) && io.in_en && io.in_valid){
      determinedValue := Mux(io.in_sel, 1.B ,0.B)
    }
    //  other probability is higher number       enable
    when((RNGnumber > io.in_probability.asUInt) && io.in_en && io.in_valid){
      determinedValue := Mux(io.in_sel, 0.B, 1.B)
    }
}


//------------------------------------------------------------------------------------------------Collapse
/*
  Takes the input Quantum State Vector and outputs 2 values of the probabilities of
      the qubit being the value 0 and 1
 */
class CollapseProbability(val num_of_qubits: Int, val bw /*BITWIDTH*/: Int, val pointLocation : Int) extends Module {
  val io = IO(new Bundle {
    val in_QSV        =  Input(Vec(pow(2,num_of_qubits).toInt, Vec( 2, SInt(bw.W))))
    val in_valid = Input(Bool())
    val out_valid = Output(Bool())
    val out_Measured = Output(Vec(2, SInt(bw.W))) //combined probability of 0 and 1
  })

  val square = Seq.fill(pow(2, num_of_qubits + 1).toInt)(Module(new FixedMultiplier(bw, pointLocation)))
  val addlayer = Seq.fill(pow(2, num_of_qubits).toInt)(Module(new FixedAdder(bw, true))) //initial add layer

  //reduce adders uses some recursion of a(n) = a(n-1) + 2^n
  var sum = 0 //recursive starting value
  for (n <- 1 until num_of_qubits) {
    sum += pow(2, n).toInt
  }
  val reduceadders = Seq.fill(sum)(Module(new FixedAdder(bw, true))) //The add layers that add the add layers together into two outputs

  //Inputs
  for (i <- 0 until pow(2, num_of_qubits).toInt) {
    //square real
    square(2 * i).io.in(0)        := io.in_QSV(i)(0) //square real
    square(2 * i).io.in(1)        := io.in_QSV(i)(0)
    //square imaginary
    square(2 * i + 1).io.in(0)    := io.in_QSV(i)(1) //square imag
    square(2 * i + 1).io.in(1)    := io.in_QSV(i)(1)
    //add real and imaginary together
    addlayer(i).io.in(0)          := square(2 * i).io.out
    addlayer(i).io.in(1)          := square(2 * i + 1).io.out
  }

  //Attach reduce layer outputs to the io.out
  if(num_of_qubits == 1){
    io.out_Measured(0) := addlayer(0).io.out
    io.out_Measured(1) := addlayer(1).io.out
  }else {
    io.out_Measured(0) := reduceadders(0).io.out
    io.out_Measured(1) := reduceadders(1).io.out
  }
  //Start attaching wires from the output to the initial input
  var currentRow = 0 //initial value of recursion
  var nextRow = 2
  for (n <- 1 until num_of_qubits - 1) { //determines which layer we are one
    for (i <- 0 until pow(2, n - 1).toInt) { //determines the inputs of the adders in the layer
      //Add probability of 0
      reduceadders(currentRow + i * 2).io.in(0) := reduceadders(nextRow + i * 4).io.out
      reduceadders(currentRow + i * 2).io.in(1) := reduceadders(nextRow + i * 4 + 2).io.out
      //Add probability of 1
      reduceadders(currentRow + i * 2 + 1).io.in(0)   := reduceadders(nextRow + i * 4 + 1).io.out
      reduceadders(currentRow + i * 2 + 1).io.in(1)   := reduceadders(nextRow + i * 4 + 3).io.out
    }
    currentRow += pow(2, n).toInt
    nextRow += pow(2, n + 1).toInt
  }
  //add layer to the reduce layers
  for (i <- 0 until pow(2, num_of_qubits - 2).toInt) {
    //Add probability of 0
    reduceadders(currentRow + (2 * i)).io.in(0):= addlayer(4 * i).io.out
    reduceadders(currentRow + (2 * i)).io.in(1):= addlayer(4 * i + 2).io.out
    //Add probability of 1
    reduceadders(currentRow + (2 * i) + 1).io.in(0):= addlayer(4 * i + 1).io.out
    reduceadders(currentRow + (2 * i) + 1).io.in(1):= addlayer(4 * i + 3).io.out
  }

  //Valid Signal
  io.out_valid := ShiftRegister( io.in_valid, num_of_qubits + 2)
}

//------------------------------------------------------------------------------------------------Normalization
/*
Does two inputs and applies the following equation:
    out = 1/sqrt( in1 + in2 )
 */
class GetNormalization(val bw /*BITWIDTH*/ : Int, val pointLocation : Int) extends Module {
  val io = IO(new Bundle {
    val in_probability  =  Input(Vec(2, SInt(bw.W)))
    val in_en           =  Input(Bool())
    val in_valid        =  Input(Bool())
    val out_valid       = Output(Bool())
    val out_Normalize   = Output(SInt(bw.W))
  })

  //one with the 0's being adjustable
  val one             = "h4000000000000000".U(63, 64 - bw).asSInt //1

  // basically get total probability -> square root it -> find the recipricol
  //The output will be sent to the FPU units to be multiplied out into the system.
  val addtogether = Module(new FixedAdder(bw, true))
  val reciprocol  = Module(new FixedDivision(bw, pointLocation))
  val sqrt        = Module(new SimpleFixedSquareRoot(bw, pointLocation))

  addtogether.io.in(0)      := io.in_probability(0)
  addtogether.io.in(1)      := io.in_probability(1)
  val addValidReg = RegInit(0.B)
  addValidReg               := io.in_valid

  reciprocol.io.in_num      := one
  reciprocol.io.in_den      := addtogether.io.out
  val divValidReg = RegInit(0.B)
  divValidReg               := addValidReg

  sqrt.io.in                := reciprocol.io.out
  sqrt.io.in_valid          := divValidReg

  //output
  val RegNormalizeOut = RegInit(0.S(bw.W))
  when(io.in_en){
    RegNormalizeOut         := sqrt.io.out
  }
  io.out_Normalize          := RegNormalizeOut
  io.out_valid              := sqrt.io.out_valid
}

class  NewQSV(val num_of_qubits: Int, val bw: Int) extends Module {
  val io = IO(new Bundle {
    val in_QSV = Input(Vec(pow(2, num_of_qubits).toInt, Vec(2, SInt(bw.W))))
    val in_sel = Input(Bool())
    val out_QSV = Output(Vec(pow(2, num_of_qubits).toInt, Vec(2, SInt(bw.W))))
  })
  val reallayerofMux = Module(new QGPMuxLayer(num_of_qubits, bw, 2))
  val imaglayerofMux = Module(new QGPMuxLayer(num_of_qubits, bw, 2))

  for (i <- 0 until pow(2, num_of_qubits - 1).toInt) {
    //real
    reallayerofMux.io.in_QSV(0)(2 * i)     := io.in_QSV(2 * i)(0).asUInt
    reallayerofMux.io.in_QSV(0)(2 * i + 1) := 0.U
    reallayerofMux.io.in_QSV(1)(2 * i)     := 0.U
    reallayerofMux.io.in_QSV(1)(2 * i + 1) := io.in_QSV(2 * i + 1)(0).asUInt
    //imag
    imaglayerofMux.io.in_QSV(0)(2 * i)     := io.in_QSV(2 * i)(1).asUInt
    imaglayerofMux.io.in_QSV(0)(2 * i + 1) := 0.U
    imaglayerofMux.io.in_QSV(1)(2 * i)     := 0.U
    imaglayerofMux.io.in_QSV(1)(2 * i + 1) := io.in_QSV(2 * i + 1)(1).asUInt
  }
  reallayerofMux.io.in_sel := io.in_sel
  imaglayerofMux.io.in_sel := io.in_sel
  //output from mux to out
  for(i <- 0 until pow(2, num_of_qubits).toInt){
    io.out_QSV(i)(0) := reallayerofMux.io.out_QSV(i).asSInt
    io.out_QSV(i)(1) := imaglayerofMux.io.out_QSV(i).asSInt
  }
}
