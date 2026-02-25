package QuantumLayers.ArithmiticGates.Gates

import chisel3._

import scala.math._

//interface for each gate
class GateIO(val num_of_qubits : Int, val bitwidth : Int) extends Bundle{
  private val size = 1 << num_of_qubits
  val in_QSV    =  Input(Vec(size, Vec(2, SInt(bitwidth.W))))
  val in_valid  =  Input(Bool())
  val out_QSV   = Output(Vec(size, Vec(2, SInt(bitwidth.W))))
  val out_valid = Output(Bool())
}

class GateData(val num_of_qubits : Int, val bitwidth : Int) extends Bundle{
  private val size = 1 << num_of_qubits
  val in_QSV    =  Vec(size, Vec(2, SInt(bitwidth.W)))
  val in_valid  =  Bool()
  val out_QSV   =  Vec(size, Vec(2, SInt(bitwidth.W)))
  val out_valid =  Bool()
}

trait GIO{val io: GateIO}
