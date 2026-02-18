package QuantumLayers.ArithmiticGates.IO

import chisel3._

import scala.math._

//interface for each gate
class GateIO(val num_of_qubits : Int, val bitwidth : Int) extends Bundle{
  val in_QSV    =  Input(Vec(pow(2,num_of_qubits).toInt, Vec(2, SInt(bitwidth.W))))
  val in_valid  =  Input(Bool())
  val out_QSV   = Output(Vec(pow(2,num_of_qubits).toInt, Vec(2, SInt(bitwidth.W))))
  val out_valid = Output(Bool())
}

class GateData(val num_of_qubits : Int, val bitwidth : Int) extends Bundle{
  val in_QSV    =  Vec(pow(2,num_of_qubits).toInt, Vec(2, SInt(bitwidth.W)))
  val in_valid  =  Bool()
  val out_QSV   =  Vec(pow(2,num_of_qubits).toInt, Vec(2, SInt(bitwidth.W)))
  val out_valid =  Bool()
}

trait GIO{val io: GateIO}
