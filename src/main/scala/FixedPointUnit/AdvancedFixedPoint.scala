
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
/*
class SplitMultiplier(val width: Int, val stages: Int, val pointloc : Int) extends Module {
  require(width % stages == 0, "Width must divide evenly by stages")

  val io = IO(new Bundle {
    val a         = Input(UInt(width.W))
    val b         = Input(UInt(width.W))
    val out       = Output(UInt((2*width).W))
    val out_fixed = Output(UInt(width.W))
    val valid_in  = Input(Bool())
    val valid_out = Output(Bool())
  })

  val chunk = width / stages

  val partials: Seq[UInt] = (0 until stages).map { i =>
    val hi = (i+1)*chunk - 1
    val lo = i*chunk
    val bChunk = io.b(hi, lo)
    ((io.a * bChunk ) << lo).asUInt
  }

  var acc: UInt   = partials.head
  var partialreg  = Reg(Vec(stages))
  var valid = io.valid_in

  for (i <- 1 until stages) {
    val partialsReg = Reg(Vec(stages-i, UInt(width.W)))
    val regnext = Reg(UInt((2*width).W))
    regnext := acc + partials(i)
    acc = regnext
    valid = RegNext(valid)
  }

  io.out := acc
  io.out_fixed := acc(width - 1 + pointloc, pointloc).asUInt

  io.valid_out := valid
}

 */

class SplitMultiplier(val width: Int, val stages: Int, val pointloc: Int) extends Module {
  require(width % stages == 0, "Width must divide evenly by stages")

  val io = IO(new Bundle {
    val a         = Input(UInt(width.W))
    val b         = Input(UInt(width.W))
    val out       = Output(UInt((2*width).W))
    val out_fixed = Output(UInt(width.W))
    val valid_in  = Input(Bool())
    val valid_out = Output(Bool())
  })

  val chunk = width / stages

  // -----------------------------
  // Initial partial products
  // -----------------------------
  val initialPartials = Wire(Vec(stages, UInt((2*width).W)))

  for (i <- 0 until stages) {
    val hi = (i + 1) * chunk - 1
    val lo = i * chunk
    val bChunk = io.b(hi, lo)
    initialPartials(i) := ((io.a * bChunk) << lo).asUInt
  }

  // -----------------------------
  // Pipeline registers for partials
  // -----------------------------
  var partials = RegNext(initialPartials)   // <-- first stage registers
  var acc      = RegNext(partials(0))       // accumulator starts with first partial
  var valid    = RegNext(io.valid_in)

  // -----------------------------
  // Pipeline loop
  // -----------------------------
  for (i <- 1 until stages) {

    // Build next-stage partial vector (one shorter each iteration)
    val nextPartials = Wire(Vec(stages - i, UInt((2*width).W)))

    // Shift remaining partials down
    for (j <- 0 until (stages - i)) {
      nextPartials(j) := partials(j + 1)
    }

    // Register the new partial vector
    partials = RegNext(nextPartials)

    // Accumulate next partial
    acc = RegNext(acc + partials(0))

    // Pipeline valid
    valid = RegNext(valid)
  }

  // -----------------------------
  // Outputs
  // -----------------------------
  io.out := acc
  io.out_fixed := acc(width - 1 + pointloc, pointloc)
  io.valid_out := valid
}

class PipelinedMul(width: Int, stages: Int) extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(width.W))
    val b = Input(UInt(width.W))
    val y = Output(UInt((2*width).W))
  })

  // 1) Partial products
  val partials : Seq[UInt] = (0 until width).map { i =>
    (io.a & Fill(width, io.b(i))) << i
  }

  // 2) Build a reduction tree with pipeline regs between levels
  def pipeReduce(xs: Seq[UInt], stagesLeft: Int): UInt = {
    if (xs.length == 1) xs.head
    else {
      val summed = xs.grouped(2).map {
        case Seq(x, y) => x +& y
        case Seq(x)    => x
      }.toSeq

      val next = if (stagesLeft > 0) summed.map(RegNext(_)) else summed
      pipeReduce(next, stagesLeft - 1)
    }
  }

  io.y := pipeReduce(partials, stages)
}