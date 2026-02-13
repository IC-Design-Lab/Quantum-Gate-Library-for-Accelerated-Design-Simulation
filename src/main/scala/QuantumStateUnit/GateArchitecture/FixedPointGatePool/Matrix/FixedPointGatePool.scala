package QuantumStateUnit.GateArchitecture.FixedPointGatePool.Matrix

import FixedPointUnit.ComplexFixedPoint._
import chisel3._
import chisel3.util._

import scala.math._

/*
The multiplication itself without controls.
 Recieves inputs and outputs,
 When addressing an input: MatrixMult(*individual value*)(*0 = Real, 1 = Imag*)
 2^n X 2^n * 2^n X 1
 */
class MatrixMult_SquareXKet(val N /*2^N*/ : Int, val bitwidth : Int) extends Module{
  override def desiredName = s"MatrixMult_Square${pow(2,N).toInt}Ket${bitwidth}Bitwidth"
  val io = IO(new Bundle{
    val in_QSV        =  Input(Vec(pow(2,N).toInt, Vec( 2, SInt(bitwidth.W))))
    val in_Ugate      =  Input(Vec(4, Vec( 2, SInt(bitwidth.W))))
    val in_valid      =  Input(Bool())
    val out_valid     = Output(Bool())
    val out_QSV       = Output(Vec(pow(2,N).toInt, Vec( 2, SInt(bitwidth.W))))
  })

  val Mult      = Seq.fill(pow(2,N+1).toInt)(Module(new FixedComplexMultiplier(bitwidth, bitwidth-2)))
  val Add       = Seq.fill(pow(2,N).toInt)(Module(new FixedComplexAdder(bitwidth)))
  val reglayer  = Reg(Vec(pow(2,N).toInt, Vec( 2, SInt(bitwidth.W))))

  for(i<-0 until pow(2,N-1).toInt){
    //Number inputs for each multiplication:
    Mult(4*i  ).io.in_a       := io.in_QSV(2*i  ) //
    Mult(4*i  ).io.in_b       := io.in_Ugate(0)   //U real
    Mult(4*i+1).io.in_a       := io.in_QSV(2*i+1) //REAL-
    Mult(4*i+1).io.in_b       := io.in_Ugate(1)   //U real
    Mult(4*i+2).io.in_a       := io.in_QSV(2*i  ) //REAL-
    Mult(4*i+2).io.in_b       := io.in_Ugate(2)   //U real
    Mult(4*i+3).io.in_a       := io.in_QSV(2*i+1) //REAL-
    Mult(4*i+3).io.in_b       := io.in_Ugate(3)   //U real

    //Attach Multiplier to the Adder
    Add(2*i  ).io.in_a         := Mult(4*i  ).io.out
    Add(2*i  ).io.in_b         := Mult(4*i+1).io.out
    Add(2*i+1).io.in_a         := Mult(4*i+2).io.out
    Add(2*i+1).io.in_b         := Mult(4*i+3).io.out

    //To reg
    reglayer(2*i  )            := Add(2*i  ).io.out
    reglayer(2*i+1)            := Add(2*i+1).io.out
  }
  //reg to out
  io.out_QSV := reglayer

  //in valid to out
  io.out_valid := ShiftRegister(io.in_valid, 2) // The only register in current design is the one connected to out
}

//====================================================================================================================

//-----------------------------GATE ID--------------------------------------------------//
/*
GateName                        ID

Normalization...................0
Hadamard........................1
sqrtNot.........................2
sqrtY...........................3
gateT...........................4
inverseT........................5
Ugate...........................6

 */

class FixedGateMult(val num_of_qubits : Int, val bitwidth : Int) extends Module {
  require(bitwidth <= 64) // Could get a number if I set up a calculator that would keep track of converting 1Einf decimal places.
  val io = IO(new Bundle {
    val in_QSV = Input(Vec(pow(2, num_of_qubits).toInt, Vec(2, SInt(bitwidth.W))))
    val in_Ugate = Input(Vec(4, Vec(2, SInt(bitwidth.W))))
    val in_normalize = Input(SInt(bitwidth.W))
    val in_sel = Input(UInt(4.W))
    val in_valid = Input(Bool())
    val out_valid = Output(Bool())
    val out_QSV = Output(Vec(pow(2, num_of_qubits).toInt, Vec(2, SInt(bitwidth.W))))
  })
  val FixedMatrix = Module(new MatrixMult_SquareXKet(num_of_qubits, bitwidth))
  //Direct connections in and out
  FixedMatrix.io.in_valid := io.in_valid
  FixedMatrix.io.in_QSV := io.in_QSV
  io.out_QSV := FixedMatrix.io.out_QSV
  io.out_valid := FixedMatrix.io.out_valid

  // ----------------------------------Gates------------------------------------

  //wires
  val sqrtOneHalfNeg  = Wire(SInt((bitwidth).W))
  val oneHalfNeg      = Wire(SInt((bitwidth).W))

  //Common Values
  val one             = "h4000000000000000".U(63, 64 - bitwidth).asSInt //1
  val sqrtOneHalf     = "h2D413CCD022A3B62".U(63, 64 - bitwidth).asSInt //sqrt(2) = 0.707...
      sqrtOneHalfNeg  := -1.S * sqrtOneHalf
  val oneHalf         = "h2000000000000000".U(63, 64 - bitwidth).asSInt //0.5
      oneHalfNeg      := -1.S * oneHalf
  val zero            = 0.S(63, 64 - bitwidth).asSInt

  //Outside inputs
  val normalizationvalue = Reg(SInt(bitwidth.W))
  normalizationvalue  :=  io.in_normalize
  val normalization = VecInit(VecInit(normalizationvalue, normalizationvalue),
                              VecInit(normalizationvalue, normalizationvalue),
                              VecInit(normalizationvalue, normalizationvalue),
                              VecInit(normalizationvalue, normalizationvalue))

  //Hadamard gate
  /*  Matrix: sqrt(1/2)*| 1  1 |
                        | 1 -1 |  */
  val hadamard = VecInit( VecInit(sqrtOneHalf.asSInt, zero),
                          VecInit(sqrtOneHalf.asSInt, zero),
                          VecInit(sqrtOneHalf.asSInt, zero),
                          VecInit(sqrtOneHalfNeg.asSInt, zero))

  //V or sqrt(NOT) or sqrt(X) gate
  /*   Matrix:   (1/2) * | (1+j) (1-j) |
                         | (1-j) (1+j) |  */
  val sqrtNot  =  VecInit(VecInit(oneHalf, oneHalf),
                          VecInit(oneHalf, oneHalfNeg),
                          VecInit(oneHalf, oneHalfNeg),
                          VecInit(oneHalf, oneHalf))


  //sqrt(Y)
  /*  Matrix: (1/2)*| (1+j) -(1+j) |
                    | (1+j)  (1+j) |  */
  val sqrtY    =  VecInit(VecInit(oneHalf, oneHalf),
                          VecInit(oneHalfNeg, oneHalfNeg),
                          VecInit(oneHalf, oneHalf),
                          VecInit(oneHalf, oneHalf))

  //T gate or sqrt(s) gate
  /*  Matrix: | 1        0     |
              | 0 e^(j*pi/4))  |  */
  val gateT    =  VecInit(VecInit(one, zero),
                          VecInit(zero, zero),
                          VecInit(zero, zero),
                          VecInit(sqrtOneHalf, sqrtOneHalf))

  //T gate or sqrt(s) gate
  /*  Matrix: | 1        0      |
              | 0 e^(-j*pi/4))  |  */
  val inverseT =  VecInit(VecInit(one, zero),
                          VecInit(zero, zero),
                          VecInit(zero, zero),
                          VecInit(sqrtOneHalf, sqrtOneHalfNeg))

  //select
  val FPUInput  = VecInit(normalization, hadamard, sqrtNot, sqrtY, gateT, inverseT, io.in_Ugate)

  //Ugate input
  FixedMatrix.io.in_Ugate := FPUInput(io.in_sel(2,0))
}