package QuantumLayers.ArithmiticGates.Permutation.Gate

import QuantumLayers.ArithmiticGates.Gates.{GIO, GateIO}
import QuantumLayers.ArithmiticGates.Permutation.SwapPositionXandY
import chisel3._

class CompatiblePerm(
                      val num_of_qubits: Int,
                      val bitwidth: Int,
                      val target0: Int,
                      val target1: Int
                    ) extends Module with GIO {

  val io = IO(new GateIO(num_of_qubits, bitwidth))

  val perm = Module(new SwapPositionXandY(num_of_qubits, bitwidth, target0, target1))

  val dim = 1 << num_of_qubits

  // SInt -> UInt adapter for perm input
  val inUInt = Wire(Vec(dim, Vec(2, UInt(bitwidth.W))))
  for (i <- 0 until dim) {
    inUInt(i)(0) := io.in_QSV(i)(0).asUInt
    inUInt(i)(1) := io.in_QSV(i)(1).asUInt
  }
  perm.io.in_ket := inUInt

  // UInt -> SInt adapter for perm output
  val outSInt = Wire(Vec(dim, Vec(2, SInt(bitwidth.W))))
  for (i <- 0 until dim) {
    outSInt(i)(0) := perm.io.out_ket(i)(0).asSInt
    outSInt(i)(1) := perm.io.out_ket(i)(1).asSInt
  }
  io.out_QSV := outSInt

  io.out_valid := RegNext(io.in_valid, init = false.B)
}