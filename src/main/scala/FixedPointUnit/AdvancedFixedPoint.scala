
package FixedPointUnit.Advanced

import chisel3._
import chisel3.util.{Cat, Fill, ShiftRegister}

class FixedMult(val bitwidth : Int, val latency : Int, val pointlocation : Int) extends Module{
  require(bitwidth % (latency+1) == 0, "The bitwidth should be dividable by the latency + 1.")
  val io = IO(new Bundle{
    val in_multiplicant   =  Input(Vec(2, SInt(bitwidth.W)))
    val in_valid          =  Input(Bool())
    val out_data          = Output(SInt((2*bitwidth).W))
    val out_fixed_data    = Output(SInt(bitwidth.W))
    val out_valid         = Output(Bool())
  })

  //calculating register locations
  def evenSplits(n: Int, splits: Int): Seq[Int] = {
    (1 to splits).map(i => (i * n) / (splits + 1))
  }
  val reglocation = evenSplits(bitwidth, latency)

  //Seperate one of the values of one multiplicant into several bits that multiply against the other
  val inputs  = Wire(Vec(bitwidth, UInt(bitwidth.W)))
  for(i <- 0 until bitwidth){
    val bitwise = Fill(bitwidth, io.in_multiplicant(1)(i)) //fill each bit with the same bit
    inputs(i) := io.in_multiplicant(0).asUInt & bitwise
  }

  val LSB           = Wire(Vec(bitwidth-1, Bool()))
  var prev : UInt   = inputs(0)
  for(i <- 1 until bitwidth){
    //add each mutliplication result
    val next              = Wire(UInt((bitwidth+1).W))//+1 for the carry bit
    LSB(i-1) := prev(0)
    next     := (0.U(1.W) ## inputs(i)) + (0.U(2.W) ## prev(bitwidth - 1, 1))

    //add register latency in the middle to improve MOF
    val insertReg = reglocation.contains(i)
    val updated = if (insertReg) RegNext(next) else next
    prev = updated
    //prev = next
  }

  //output
  val output = Reg(UInt((2*bitwidth).W)) //register output
  output     := prev ## Cat(LSB.reverse)

  io.out_data       := output.asSInt
  io.out_fixed_data := output(bitwidth - 1 + pointlocation, pointlocation).asSInt

  //valid
  io.out_valid := ShiftRegister(io.in_valid, latency + 1)
}