/*
package FixedPointUnit.Multiplier

import chisel3._
import chisel3.util.ShiftRegister

class FixedMult(val bitwidth : Int, val pointLoc : Int) extends Module{
  require((bitwidth & (bitwidth - 1)) == 0, s"Width $bitwidth is not a power of 2")
  val io = IO(new Bundle{
    val in_multiplicant   =  Input(Vec(2, SInt(bitwidth.W)))
    val in_valid          =  Input(Bool())
    val out_data          = Output(SInt(bitwidth.W))
    val out_valid         = Output(Bool())
  })

  //Seperate one of the values of one multiplicant into several bits that multiply against the other
  val inputs  = Wire(Vec(bitwidth, SInt(bitwidth.W)))
  for(i <- 0 until bitwidth){
    inputs(i) := io.in_multiplicant(0) & io.in_multiplicant(1)(i).asSInt
  }

  //Connect layers of adders to each other
  var regPerLayer = bitwidth >> 1 // divide by 2
  var prev        = inputs
  //the amount of times ran
  var layer       = 0

  //final result
  val product = WireInit(0.U((2*bitwidth).W)) // result

  while(regPerLayer >= 1){
    val next = RegInit(VecInit(Seq.fill(regPerLayer)(0.U(bitwidth.W))))

    //One of the numbers is left shifted or multiplied by 2
    for(regValue <- 0 until regPerLayer / 2){
      val shift = WireInit(0.U(bitwidth.W))
      shift := (prev(2*regValue + 1)(bitwidth - 2, 0) ## 0.U(1.W))
      next(regValue) := prev(2*regValue).asUInt + shift.asUInt
    }

    //Connecting results to output
    if(regPerLayer > 1) {
      product(2*bitwidth - layer - 1) := prev((regPerLayer * 2) - 1)(bitwidth - 1).asBool
      product(layer) := prev(0)(0)
    }else if(regPerLayer == 1){
      product(bitwidth - (bitwidth / 4) - 1, bitwidth / 4) := next(0)
    }

    regPerLayer = regPerLayer >> 1
    layer = layer + 1
  }

  //result
  val productReg = RegInit(0.S(bitwidth.W))
  productReg := product(bitwidth - 1 + pointLoc, pointLoc).asSInt
  io.out_data := productReg

  //valid --- calculating logerithm
  var logerithmValue = 0
  var logvalue = bitwidth
  while(logvalue > 0){
    logerithmValue = logerithmValue +1
    logvalue = logvalue >> 1
  }
  io.out_valid  := ShiftRegister(io.in_valid, logerithmValue)
}
 */