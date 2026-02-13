package QuantumStateUnit.GateArchitecture

import chisel3._

import scala.math.pow

class GatePoolInterface(val num_of_qubits : Int, val bitwidth : Int) extends Bundle{
  val in_QSV    = Input(Vec(pow(2, num_of_qubits).toInt, UInt((2 * bitwidth).W)))
  val in_Ugate  = Input(Vec(4, UInt((2 * bitwidth).W)))
  val in_sel    = Input(UInt(5.W)) // 1 bit sel between nonfpu and fpu and 4 bit sel for the actual select
  val in_noise  = Input(UInt(32.W))
  val in_valid  = Input(Bool())
  val out_valid = Output(Bool())
  val out_QSV   = Output(Vec(pow(2, num_of_qubits).toInt, UInt((2 * bitwidth).W)))
  val out_MQ    = Output(Bool()) //The measured Qubits Value
}

trait GPInterface { val io: GatePoolInterface }

