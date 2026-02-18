package QuantumLayers.ArithmiticGates.TestVariant

import QuantumLayers.ArithmiticGates.IO._
import chisel3._
import QuantumLayers.ArithmiticGates._

/*
Adds Registers to both ends in order for vivado to give the WNS for the MOF
 */
class TestVariant(val num_of_qubits : Int, val bitwidth : Int, val GateType : SpecifiedGate) extends Module{
  override def desiredName = s"RegIO_${num_of_qubits}QubitVector${bitwidth}bit_${GateType}Gate"

  val io        =  IO(new GateIO(num_of_qubits, bitwidth))
  val regIO     = Reg(new GateData(num_of_qubits, bitwidth))

  // io and reg
  io.out_QSV    := regIO.out_QSV
  io.out_valid  := regIO.out_valid
  regIO.in_QSV  := io.in_QSV
  regIO.in_valid:= io.in_valid


  val gateVec   = Module(new SpanVector(num_of_qubits, bitwidth, GateType))

  //
  gateVec.io.in_QSV   := regIO.in_QSV
  gateVec.io.in_valid := regIO.in_valid
  regIO.out_QSV          := gateVec.io.out_QSV
  regIO.out_valid        := gateVec.io.out_valid
}
