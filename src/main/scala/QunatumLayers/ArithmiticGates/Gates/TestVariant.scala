package QuantumLayers.ArithmiticGates.Gates

import QuantumLayers.ArithmiticGates.Permutation.Gate.CompatiblePerm
import QuantumLayers.ArithmiticGates.Permutation.SwapPositionXandY
import chisel3._

/*
Replaces Inputs with registers in order to avoid not enough IO errors and get the WNS for
 */
class TestVariant(val ketSizeExponent : Int, val bitwidth : Int, val GateType : SpecifiedGate) extends Module{
  override def desiredName = s"RegIO_${ketSizeExponent}QubitVector${bitwidth}bit_${GateType}Gate"
  val io = IO(new Bundle{
    val out_QSV   = Output(Vec((2 << ketSizeExponent - 1), Vec(2, SInt(bitwidth.W))))
    val out_valid = Output(Bool())
  })
  // There is no real IO here. This is not functional.
  val regIO     = Reg(new GateData(ketSizeExponent, bitwidth))
  io.out_QSV    := regIO.out_QSV
  io.out_valid  := regIO.out_valid

  val gateVec   = Module(new SpanVector(ketSizeExponent, bitwidth, GateType))

  gateVec.io.in_QSV   := regIO.in_QSV
  gateVec.io.in_valid := regIO.in_valid
  regIO.out_QSV          := gateVec.io.out_QSV
  regIO.out_valid        := gateVec.io.out_valid
}

class TestPermVariant(val ketSizeExponent : Int, val bitwidth : Int, val location0 : Int, val location1 : Int) extends Module{
  override def desiredName = s"RegIO_${ketSizeExponent}QubitVector_Swap_${location0}_${location1}"
  val io = IO(new Bundle{
    val out_QSV   = Output(Vec((1 << ketSizeExponent), Vec(2, SInt(bitwidth.W))))
    val out_valid = Output(Bool())
  })
  // There is no real IO here. This is not functional.
  val regIO     = Reg(new GateData(ketSizeExponent, bitwidth))
  io.out_QSV    := regIO.out_QSV
  io.out_valid  := regIO.out_valid


  val gateVec   = Module(new CompatiblePerm(ketSizeExponent, bitwidth, location0, location1))

  for(i <- 0 until (1 << ketSizeExponent)) {
    gateVec.io.in_QSV(i)(0) := regIO.in_QSV(i)(0)
    gateVec.io.in_QSV(i)(1) := regIO.in_QSV(i)(1)
    regIO.out_QSV(i)(0) := gateVec.io.out_QSV(i)(0)
    regIO.out_QSV(i)(1) := gateVec.io.out_QSV(i)(1)
  }
  gateVec.io.in_valid := regIO.in_valid
  regIO.out_valid     := gateVec.io.out_valid
}
