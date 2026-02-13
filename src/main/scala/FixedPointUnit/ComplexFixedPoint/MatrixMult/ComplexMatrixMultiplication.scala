package FixedPointUnit.ComplexFixedPoint.MatrixMult

import chisel3._
import chisel3.util._
import FixedPointUnit.ComplexFixedPoint._
import scala.math._

/*
Resource intensive Ket Unit Vector Multiplication
Size Growth in Big O notation:
Resources: O(2^x)
latency:   O(log(x))
 */
class ComplexMatrixMult_FullOne(val N /*2^N*/ : Int, val bitwidth : Int) extends Module{
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
  io.out_valid := ShiftRegister(io.in_valid, 1) // The only register in current design is the one connected to out
}

/*
Resource intensive Ket Unit Vector Multiplication
Size Growth in Big O notation:
Resources: O(log(x)) //There will still exist 2^x registers
latency:   O(2^x)
 */
class ComplexMatrixMult_Full(val N /*2^N*/ : Int, val bitwidth : Int) extends Module {
  val io = IO(new Bundle {
    val in_QSV = Input(Vec(pow(2, N).toInt, Vec(2, SInt(bitwidth.W))))
    val in_Ugate = Input(Vec(4, Vec(2, SInt(bitwidth.W))))
    val in_apply = Input(Bool()) //Won't Update output until apply is given
    val in_valid = Input(Bool())
    val out_apply = Input(Bool())
    val out_valid = Output(Bool())
    val out_QSV = Output(Vec(pow(2, N).toInt, Vec(2, SInt(bitwidth.W))))
  })

}
