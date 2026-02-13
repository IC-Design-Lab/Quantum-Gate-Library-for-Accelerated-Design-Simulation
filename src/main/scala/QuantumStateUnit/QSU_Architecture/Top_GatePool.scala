package QuantumStateUnit.QSU_Architecture

import QuantumStateUnit.GateArchitecture.FPUGatePool.FPUQuantumGatePool
import chisel3._
import QuantumStateUnit.GateArchitecture.FixedPointGatePool._
import QuantumStateUnit.GateArchitecture.GatePoolInterface
import chisel3.util._

import scala.math._

class gatepool_FixOFlo(val num_of_qubits : Int, val bitwidth : Int, val useFPU : Boolean) extends Module{
  val io = IO(new Bundle {
    val in_QSV    = Input(Vec(pow(2, num_of_qubits).toInt, UInt((2 * bitwidth).W)))
    val in_Ugate  = Input(Vec(4, UInt(bitwidth.W)))
    val in_sel    = Input(UInt(5.W)) // 1 bit sel between nonfpu and fpu and 4 bit sel for the actual select
    val in_noise  = Input(UInt(32.W))
    val in_valid  = Input(Bool())
    val out_valid = Output(Bool())
    val out_QSV   = Output(Vec(pow(2, num_of_qubits).toInt, UInt((2 * bitwidth).W)))
    val out_MQ    = Output(Bool()) //The measured Qubits Value
  })
/*
  val GatePool =
    if (useFPU) Module(new FPUQuantumGatePool(num_of_qubits, bitwidth))
    else Module(new FixedQuantumGatePool(num_of_qubits, bitwidth))
 */
  //val GatePool = Module(new FPUQuantumGatePool(num_of_qubits, bitwidth))
  val GatePool = Module(new FixedQuantumGatePool(num_of_qubits, bitwidth))

   for(i <- 0 until pow(2, num_of_qubits).toInt) {
      GatePool.io.in_QSV(i):= io.in_QSV(i)
      io.out_QSV(i)     := GatePool.io.out_QSV(i)
    }
    GatePool.io.in_Ugate   := VecInit( io.in_Ugate(0)(0) ## io.in_Ugate(0)(1),
                                    io.in_Ugate(1)(0) ## io.in_Ugate(1)(1),
                                    io.in_Ugate(2)(0) ## io.in_Ugate(2)(1),
                                    io.in_Ugate(3)(0) ## io.in_Ugate(3)(1))
    GatePool.io.in_sel     := io.in_sel
    GatePool.io.in_noise   := io.in_noise
    GatePool.io.in_valid   := io.in_valid
    io.out_valid        := GatePool.io.out_valid
    io.out_MQ           := GatePool.io.out_MQ
}