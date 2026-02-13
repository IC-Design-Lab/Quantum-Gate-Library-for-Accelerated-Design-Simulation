package Complex_FPU.KetMatrixMult

import Complex_FPU.{complex_adder, complex_conjugate_mult}
import QuantumStateUnit.GateArchitecture.FPUGatePool.Matrix.AndGate
import chisel3._
import chisel3.util._

import scala.math._

/*
2 x 2 matrix times a 2 size ket vector
Resource intensive Ket Vector Multiplication
Size Growth in Big O notation:
Resources: O(2^x)
latency:   O(log(x))
 */
class FPUSize2SquareKetMatrixMult(val bw :Int, val mult_pd : Int, val add_pd : Int) extends Module {
  require(bw == 32 || bw == 64 || bw == 128 || bw == 256)
  val vectorSize = 2
  val io = IO(new Bundle {
    val in_Ket = Input(Vec(vectorSize, UInt(bw.W)))
    val in_Ugate = Input(Vec(vectorSize, Vec(vectorSize , UInt(bw.W))))
    val in_valid = Input(Bool())
    val out_valid = Output(Bool())
    val out_QSV = Output(Vec(vectorSize, UInt(bw.W)))
  })

  val FPUMultiplier = Seq.fill(4)(Module(new complex_conjugate_mult(bw,mult_pd,add_pd)))
  val FPUAdder      = Seq.fill(2)(Module(new complex_adder(bw, add_pd)))
  val FPUvalid      = Module(new AndGate(vectorSize))

  //Multipliers
  for(i <- 0 until vectorSize){    //Selects Row of the Ugate
    for(j <- 0 until vectorSize){  //Selects Column of the Ugate
      FPUMultiplier(vectorSize*i + j).io.complexA := io.in_Ugate(vectorSize*i + j)
      FPUMultiplier(vectorSize*i + j).io.complexB := io.in_Ket(i)
      FPUMultiplier(vectorSize*i + j).io.in_valid := io.in_valid
      FPUMultiplier(vectorSize*i + j).io.in_en    := 1.B
    }
  }

  //Adders Attaching to Output
  for(i <- 0 until vectorSize){   //Selects Row of the Ket Vector To be added together
    FPUAdder(i).io.complexA                       := FPUMultiplier(vectorSize*i + 0).io.out_s
    FPUAdder(i).io.complexB                       := FPUMultiplier(vectorSize*i + 1).io.out_s
    FPUAdder(i).io.in_valid                       := FPUMultiplier(vectorSize*i + 0).io.out_valid && FPUMultiplier(vectorSize*i + 0).io.out_valid
    FPUAdder(i).io.in_en                          := 1.B
  }

  //Output
  for(i <- 0 until vectorSize){
    io.out_QSV(i)                                 := FPUAdder(i).io.out_s
  }
  io.out_valid                                    := FPUAdder(0).io.out_valid && FPUAdder(1).io.out_valid
}


/*
2 x 2 matrix times a 4 size ket vector
Resource intensive Ket Vector Multiplication
Size Growth in Big O notation:
Resources: O(2^x)
latency:   O(log(x))
 */
class FPUSize4SquareKetMatrixMult(val size /*2^size*/ : Int, val bw :Int, val mult_pd : Int, val add_pd : Int) extends Module {
  require(bw == 32 || bw == 64 || bw == 128 || bw == 256)
  val vectorSize = pow(2, size).toInt
  val io = IO(new Bundle {
    val in_Ket = Input(Vec(vectorSize, UInt(bw.W)))
    val in_Ugate = Input(Vec(vectorSize, Vec(vectorSize , UInt(bw.W))))
    val in_valid = Input(Bool())
    val out_valid = Output(Bool())
    val out_Ket = Output(Vec(vectorSize, UInt(bw.W)))
  })

  //Determine the number of adders per row recursively
  var numOfAdders = 0 //The amount of adders per row
  for (n <- 0 until size) {
    numOfAdders += pow(2, n).toInt
  }
  //number of each = number per row * number of rows
  val FPUMultiplier = Seq.fill(vectorSize)(Seq.fill(vectorSize)(Module(new complex_conjugate_mult(bw,mult_pd,add_pd))))
  val addlayer      = Seq.fill(vectorSize)(Seq.fill(vectorSize/2)(Module(new complex_adder(bw, add_pd)))) //first layer
  val reduceadders  = Seq.fill(vectorSize)(Seq.fill(numOfAdders)(Module(new complex_adder(bw, add_pd)))) //middle layers
  val finalAdder    = Seq.fill(vectorSize)(Module(new complex_adder(bw, add_pd))) //top layer connected to out
  val FPUvalid      = Module(new AndGate(vectorSize))


  //Multipliers
  for(i <- 0 until vectorSize){    //Selects Row of the final vector
    for(j <- 0 until vectorSize/2){  //Multiplies by the first two values and
      //2 multipliers: row + every 2 multipliers
      FPUMultiplier(i)(2*j + 0).io.complexA := io.in_Ugate(vectorSize*i + 2*j + 0)
      FPUMultiplier(i)(2*j + 0).io.complexB := io.in_Ket(2*i + 0)
      FPUMultiplier(i)(2*j + 0).io.in_valid := io.in_valid
      FPUMultiplier(i)(2*j + 0).io.in_en    := 1.B
      FPUMultiplier(i)(2*j + 1).io.complexA := io.in_Ugate(vectorSize*i + 2*j + 1)
      FPUMultiplier(i)(2*j + 1).io.complexB := io.in_Ket(2*i + 1)
      FPUMultiplier(i)(2*j + 1).io.in_valid := io.in_valid
      FPUMultiplier(i)(2*j + 1).io.in_en    := 1.B
      //1 adder: row + previous multipliers
      addlayer(i)(j).io.complexA            := FPUMultiplier(i)(2*j + 0).io.out_s
      addlayer(i)(j).io.complexB            := FPUMultiplier(i)(2*j + 1).io.out_s
      addlayer(i)(j).io.in_valid            := FPUMultiplier(i)(2*j + 0).io.out_valid && FPUMultiplier(i)(vectorSize*i + 2*j + 1).io.out_valid
      addlayer(i)(j).io.in_en               := 1.B
    }
    var currentRow = 0 //initial value of recursion
    var nextRow = 2
    for (n <- 1 until size - 1) { //determines which layer we are one
      for (k <- 0 until pow(2, n - 1).toInt) { //determines the inputs of the adders in the layer
        //Add probability of 0
        reduceadders(i)(currentRow + k * 2    ).io.complexA := reduceadders(i)(nextRow + k * 4).io.out_s
        reduceadders(i)(currentRow + k * 2    ).io.complexB := reduceadders(i)(nextRow + k * 4 + 2).io.out_s
        reduceadders(i)(currentRow + k * 2    ).io.in_en    := 1.B
        reduceadders(i)(currentRow + k * 2    ).io.in_valid := reduceadders(i)(nextRow + k * 4).io.out_valid & reduceadders(i)(nextRow + k * 4 + 2).io.out_valid
        //Add probability of 1
        reduceadders(i)(currentRow + k * 2 + 1).io.complexA := reduceadders(i)(nextRow + k * 4 + 1).io.out_s
        reduceadders(i)(currentRow + k * 2 + 1).io.complexB := reduceadders(i)(nextRow + k * 4 + 3).io.out_s
        reduceadders(i)(currentRow + k * 2 + 1).io.in_en    := 1.B
        reduceadders(i)(currentRow + k * 2 + 1).io.in_valid := reduceadders(i)(nextRow + k * 4 + 1).io.out_valid & reduceadders(i)(nextRow + k * 4 + 3).io.out_valid
      }
      currentRow += pow(2, n).toInt
      nextRow += pow(2, n + 1).toInt
    }
  }
  //The Final adder
  if(size == 1){
    for(i <- 0 until vectorSize) {
      finalAdder(i).io.complexA := addlayer(i)(0).io.out_s
      finalAdder(i).io.complexB := addlayer(i)(1).io.out_s
    }
  }else {
    for(i <- 0 until vectorSize) {
      finalAdder(i).io.complexA := reduceadders(i)(0).io.out_s
      finalAdder(i).io.complexB := reduceadders(i)(1).io.out_s
    }
  }
  for(i <- 0 until vectorSize) {
    finalAdder(i).io.in_en      := 1.B
    FPUvalid.io.in_valid(i)     := finalAdder(i).io.in_valid
  }

  //Output
  for(i <- 0 until vectorSize) {
    io.out_Ket(i) := finalAdder(i).io.out_s
  }
  io.out_valid    := FPUvalid.io.out_valid

}