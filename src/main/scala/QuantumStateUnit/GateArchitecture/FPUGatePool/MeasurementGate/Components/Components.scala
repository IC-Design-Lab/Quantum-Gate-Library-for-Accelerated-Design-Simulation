package QuantumStateUnit.GateArchitecture.FPUGatePool.MeasurementGate.Components

import New_FPU_Mario.FPUnits.{FP_add, FP_mult, FP_sqrt, FloatTOFixed}
import QuantumStateUnit.GateArchitecture.QGPMuxLayer
import QuantumStateUnit.OtherComponents.PsuedoRandomGenerator.LinearCongruentialGenerator
import chisel3._
import scala.math.pow

/*
First, the magnitude of each complex number needs to be calculated by squaring both numbers then take the square.
 Then each coefficient needs to be squared to find the final probability, this cancels out the square.
 Finally, each value needs to be added together.
*/
//Shows the probability of 0 and 1 of the 0th qubit. Note: to choose a different qubit, select via 0th permutation_sel
//outputs the probability and not the vector itself.
class CollapseProbability(val num_of_qubits: Int, val bw: Int, val mult_pd: Int, val add_pd: Int, val L: Int) extends Module {
  require(bw == 32 || bw == 64 || bw == 128 || bw == 256)
  val io = IO(new Bundle {
    val in_QSV = Input(Vec(pow(2, num_of_qubits).toInt, UInt(bw.W)))
    val in_valid = Input(Bool())
    val out_valid = Output(Bool())
    val out_Measured = Output(Vec(2, UInt((bw / 2).W))) //combined probability of 0 and 1
  })

  val square = Seq.fill(pow(2, num_of_qubits + 1).toInt)(Module(new FP_mult(bw / 2, mult_pd)))
  val addlayer = Seq.fill(pow(2, num_of_qubits).toInt)(Module(new FP_add(bw / 2, add_pd))) //initial add layer

  //reduce adders uses some recursion of a(n) = a(n-1) + 2^n
  var sum = 0 //recursive starting value
  for (n <- 1 until num_of_qubits) {
    sum += pow(2, n).toInt
  }
  val reduceadders = Seq.fill(sum)(Module(new FP_add(bw / 2, add_pd))) //The add layers that add the add layers together into two outputs

  //Inputs
  for (i <- 0 until pow(2, num_of_qubits).toInt) {
    //square real
    square(2 * i).io.in_a         := io.in_QSV(i)(bw - 1, bw / 2) //square real
    square(2 * i).io.in_b         := io.in_QSV(i)(bw - 1, bw / 2)
    square(2 * i).io.in_en        := 1.B
    square(2 * i).io.in_valid     := io.in_valid
    //square imaginary
    square(2 * i + 1).io.in_a     := io.in_QSV(i)((bw / 2) - 1, 0) //square imag
    square(2 * i + 1).io.in_b     := io.in_QSV(i)((bw / 2) - 1, 0)
    square(2 * i + 1).io.in_en    := 1.B
    square(2 * i + 1).io.in_valid := io.in_valid
    //add real and imaginary together
    addlayer(i).io.in_a           := square(2 * i).io.out_s
    addlayer(i).io.in_b           := square(2 * i + 1).io.out_s
    addlayer(i).io.in_valid       := square(2 * i).io.out_valid & square(2 * i + 1).io.out_valid
    addlayer(i).io.in_en          := 1.B
  }

  //Attach reduce layer outputs to the io.out
  if(num_of_qubits == 1){
    io.out_Measured(0) := addlayer(0).io.out_s
    io.out_Measured(1) := addlayer(1).io.out_s
  }else {
    io.out_Measured(0) := reduceadders(0).io.out_s
    io.out_Measured(1) := reduceadders(1).io.out_s
  }
  //Start attaching wires from the output to the initial input
  var currentRow = 0 //initial value of recursion
  var nextRow = 2
  for (n <- 1 until num_of_qubits - 1) { //determines which layer we are one
    for (i <- 0 until pow(2, n - 1).toInt) { //determines the inputs of the adders in the layer
      //Add probability of 0
      reduceadders(currentRow + i * 2).io.in_a := reduceadders(nextRow + i * 4).io.out_s
      reduceadders(currentRow + i * 2).io.in_b := reduceadders(nextRow + i * 4 + 2).io.out_s
      reduceadders(currentRow + i * 2).io.in_en := 1.B
      reduceadders(currentRow + i * 2).io.in_valid := reduceadders(nextRow + i * 4).io.out_valid & reduceadders(nextRow + i * 4 + 2).io.out_valid
      //Add probability of 1
      reduceadders(currentRow + i * 2 + 1).io.in_a := reduceadders(nextRow + i * 4 + 1).io.out_s
      reduceadders(currentRow + i * 2 + 1).io.in_b := reduceadders(nextRow + i * 4 + 3).io.out_s
      reduceadders(currentRow + i * 2 + 1).io.in_en := 1.B
      reduceadders(currentRow + i * 2 + 1).io.in_valid := reduceadders(nextRow + i * 4 + 1).io.out_valid & reduceadders(nextRow + i * 4 + 3).io.out_valid
    }
    currentRow += pow(2, n).toInt
    nextRow += pow(2, n + 1).toInt
  }
  //add layer to the reduce layers
  for (i <- 0 until pow(2, num_of_qubits - 2).toInt) {
    //Add probability of 0
    reduceadders(currentRow + (2 * i)).io.in_a := addlayer(4 * i).io.out_s
    reduceadders(currentRow + (2 * i)).io.in_b := addlayer(4 * i + 2).io.out_s
    reduceadders(currentRow + (2 * i)).io.in_en := 1.B
    reduceadders(currentRow + (2 * i)).io.in_valid := addlayer(4 * i).io.out_valid & addlayer(4 * i + 2).io.out_valid
    //Add probability of 1
    reduceadders(currentRow + (2 * i) + 1).io.in_a := addlayer(4 * i + 1).io.out_s
    reduceadders(currentRow + (2 * i) + 1).io.in_b := addlayer(4 * i + 3).io.out_s
    reduceadders(currentRow + (2 * i) + 1).io.in_en := 1.B
    reduceadders(currentRow + (2 * i) + 1).io.in_valid := addlayer(4 * i + 1).io.out_valid & addlayer(4 * i + 3).io.out_valid
  }

  //Valid will come from the normalization module instead if it's chosen
  if(num_of_qubits == 1){
    io.out_valid := addlayer(0).io.out_valid && addlayer(1).io.out_valid
  } else {
    io.out_valid := reduceadders(0).io.out_valid && reduceadders(1).io.out_valid
  }

}

/*
Ideally the input of io.in_random would be random noise between 0 and 100.
  For now, I will use scala random number generator as the input for the random decision between 0 and 1.

FloatToFixed converts 50 to 32 and 25 to 16, so 100 is 64.
If the ratio between the conversions is constant, then it doesn't matter
As long as the input is a random number between 0 and 64, this won't matter.
 */
class CompareWithRandom(val bw: Int, val mult_pd: Int) extends Module {
  val io = IO(new Bundle {
    val in_probability  =  Input(UInt(bw.W))
    val in_seed         =  Input(UInt(32.W)) //32 bit number
    val in_en           =  Input(Bool()) //0 stops updating output AND feeds a new seed
    val in_sel          =  Input(Bool()) //Determines if 0 or 1 takes the lower number / has the probability of input
    val in_valid        =  Input(Bool())
    val out_valid       = Output(Bool())
    val out_value       = Output(Bool())
  })
  val IEEENumber_Hundred = bw match { // 100
    case 16 => "h5640".U(bw.W)
    case 32 => "h42C80000".U(bw.W)
    case 64 => "h4059000000000000".U(bw.W)
    case 128 => "h40059000000000000000000000000000".U(bw.W)
  }

  //Multiply both numbers by 100, then convert to "fixed point" to be compared
  val multiplier0 = Module(new FP_mult(bw, mult_pd))
  val toFixed0    = Module(new FloatTOFixed(bw, 8, 0)) //Probability of chance for 0

  //pseudo random number generator
  val prev        = RegInit(0.B)
  prev           := io.in_en
  val RNG         = Module(new LinearCongruentialGenerator(32, 64, 5, 1)) //32 bit Linear Congruent RNG
  RNG.io.in_next := io.in_en //continues down current sequence until enabled
  RNG.io.in_feed := io.in_en && !prev //changes sequence when enabled
  RNG.io.in_seed := io.in_seed//initialization


  //Convert values to a number within the range of 100
  multiplier0.io.in_a     := io.in_probability
  multiplier0.io.in_b     := IEEENumber_Hundred
  multiplier0.io.in_en    := 1.B
  multiplier0.io.in_valid := io.in_valid
  //Convert Number to fixedPoint
  toFixed0.io.in_float    := multiplier0.io.out_s
  toFixed0.io.in_en       := 1.B
  toFixed0.io.in_valid    := multiplier0.io.out_valid

  //The output that tells when 0 or 1
  val determinedValue = RegInit(0.B)
  val valid           = RegInit(0.B)
  valid               := toFixed0.io.out_valid
  when(!io.in_en){
    valid := toFixed0.io.out_valid //Stay valid until change is made with en being 0
  }
  io.out_value := determinedValue
  io.out_valid := valid

  //Compare to Unsigned: the input should be positive anyway because all numbers were squared.
  val probabilityUInt = toFixed0.io.out_fixed.asUInt
  val RNGnumber       = RNG.io.out_Value + 1.U
  //The chosen value will get the lower value
  //              input is lower number         enable
  when((RNGnumber < probabilityUInt) && io.in_en && toFixed0.io.out_valid){
    determinedValue := Mux(io.in_sel, 1.B ,0.B)
  }
  //  other probability is higher number       enable
  when((RNGnumber > probabilityUInt) && io.in_en && toFixed0.io.out_valid){
    determinedValue := Mux(io.in_sel, 0.B, 1.B)
  }
}

/*
  When the qubit is measured, all of the states where the qubit is opposite of the measured state becomes 0.
    Takes a binary input and outputs either all of the probability of 0 or 1. The opposite probability becomes 0.
 */
class NewQSV(val num_of_qubits: Int, val bw: Int) extends Module {
  val io = IO(new Bundle {
    val in_QSV = Input(Vec(pow(2, num_of_qubits).toInt, UInt(bw.W)))
    val in_sel = Input(Bool())
    val out_QSV = Output(Vec(pow(2, num_of_qubits).toInt, UInt(bw.W)))
  })
  val layerofMux = Module(new QGPMuxLayer(num_of_qubits, bw, 2))

  for (i <- 0 until pow(2, num_of_qubits - 1).toInt) {
    layerofMux.io.in_QSV(0)(2 * i) := io.in_QSV(2 * i)
    layerofMux.io.in_QSV(0)(2 * i + 1) := 0.U
    layerofMux.io.in_QSV(1)(2 * i) := 0.U
    layerofMux.io.in_QSV(1)(2 * i + 1) := io.in_QSV(2 * i + 1)
  }
  layerofMux.io.in_sel := io.in_sel
  io.out_QSV := layerofMux.io.out_QSV
}

/*
To get the normalized number, we can use the output from the collapsing probability and combine it to one number.
If the final number is 1, then it's fine; if not, then the normalization constant will be found to multiply the QSV.
The number will be sent to the FP gate pool to be multiplied to the rest of the QSV
*/
//L is a thing listed in the FPUnits, I have no idea what it means. Maybe it's room for operation?
class GetNormalization(val bw: Int, val add_pd: Int, val L: Int) extends Module {
  require(bw == 16 || bw == 32 || bw == 64 || bw == 128)
  //       L <= 10     L <= 23     L <= 52     L <= 112
  val io = IO(new Bundle {
    val in_probability  =  Input(Vec(2, UInt(bw.W)))
    val in_en           =  Input(Bool())
    val in_valid        =  Input(Bool())
    val out_valid       = Output(Bool())
    val out_Normalize   = Output(UInt(bw.W))
  })

  //One in IEEE 754 format
  val IEEENumber_One = bw match { // 1
    case 16 => "h3C00".U(bw.W)
    case 32 => "h3F800000".U(bw.W)
    case 64 => "h3FF0000000000000".U(bw.W)
    case 128 => "h3FFF0000000000000000000000000000".U(bw.W)
  }
  // basically get total probability -> square root it -> find the recipricol
  //The output will be sent to the FPU units to be multiplied out into the system.
  val addtogether = Module(new FP_add(bw, add_pd))
  val sqrt        = Module(new FP_sqrt(bw, L))
  //val recipricolNum = Module(new FP_div(bw, L)) //divide 1 by number


  addtogether.io.in_a       := io.in_probability(0)
  addtogether.io.in_b       := io.in_probability(1)
  addtogether.io.in_en      := io.in_en
  addtogether.io.in_valid   := io.in_valid

  sqrt.io.in_a              := addtogether.io.out_s
  sqrt.io.in_en             := io.in_en
  sqrt.io.in_valid          := addtogether.io.out_valid

  //recipricolNum.io.in_a     := IEEENumber_One //numerator
  //recipricolNum.io.in_b     := sqrt.io.out_s //denominator
  //recipricolNum.io.in_en    := io.in_en
  //recipricolNum.io.in_valid := sqrt.io.out_valid

  //io.out_Normalize          := recipricolNum.io.out_s
  //io.out_valid              := recipricolNum.io.out_valid

  io.out_Normalize          := sqrt.io.out_s
  io.out_valid              := sqrt.io.out_valid
}