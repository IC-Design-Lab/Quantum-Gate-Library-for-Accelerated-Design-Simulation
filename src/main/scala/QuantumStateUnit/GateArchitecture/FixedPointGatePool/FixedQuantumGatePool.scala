package QuantumStateUnit.GateArchitecture.FixedPointGatePool

import QuantumStateUnit.GateArchitecture.{AcceleratedGatePool, GPInterface, GatePoolInterface, QGPMuxLayer}
import chisel3._
import QuantumStateUnit.GateArchitecture.FixedPointGatePool.Matrix._
import QuantumStateUnit.GateArchitecture.FixedPointGatePool.MeasurementGate._

import scala.math._
/*
val io = IO(new Bundle {
  val in_QSV    = Input(Vec(pow(2, num_of_qubits).toInt, Vec(2, SInt(bitwidth.W))))
  val in_Ugate  = Input(Vec(4, Vec(2, SInt(bitwidth.W))))
  val in_sel    = Input(UInt(5.W)) // 1 bit sel between nonfpu and fpu and 4 bit sel for the actual select
  val in_noise  = Input(UInt(32.W))
  val in_valid  = Input(Bool())
  val out_valid = Output(Bool())
  val out_QSV   = Output(Vec(pow(2, num_of_qubits).toInt, Vec(2, SInt(bitwidth.W))))
  val out_MQ    = Output(Bool()) //The measured Qubits Value
})
 */

class FixedQuantumGatePool(val num_of_qubits : Int ,val bitwidth : Int) extends Module with GPInterface{
  val io = IO(new GatePoolInterface(num_of_qubits, bitwidth))
  //Reformating data to fit into IO
  val QSUinput = Wire(Vec(pow(2, num_of_qubits).toInt, Vec(2, SInt(bitwidth.W))))
  for(i <- 0 until pow(2, num_of_qubits).toInt) {
    QSUinput(i)(0) := io.in_QSV(i)((bitwidth * 2) - 1, bitwidth).asSInt
    QSUinput(i)(1) := io.in_QSV(i)(bitwidth - 1, 0).asSInt
  }


  //two gate pools and measurement
  val FixedGatePool       = Module(new FixedGateMult(num_of_qubits, bitwidth))
  val acceleratedGatePool = Module(new AcceleratedGatePool(num_of_qubits, bitwidth * 2)) //Both real and imag are concatenated
  val measure             = Module(new MeasurementGate(num_of_qubits, bitwidth))
  //Mux output
  val realoutputmux = Module(new QGPMuxLayer(num_of_qubits, bitwidth, 2))
  val imagoutputmux = Module(new QGPMuxLayer(num_of_qubits, bitwidth, 2))

  //Use Normalize gate if measuring.
  val enNormalize             = (io.in_sel === 16.U) || (io.in_sel === 31.U)

  //connecting layers: in -> Gate -> mux -> out
  for(i <- 0 until pow(2, num_of_qubits).toInt){
    //accelerated input
    acceleratedGatePool.io.in_QSV(i)   := io.in_QSV(i)
    //real
    realoutputmux.io.in_QSV(0)(i) := acceleratedGatePool.io.out_QSV(i)((bitwidth*2)-1, bitwidth)
    realoutputmux.io.in_QSV(1)(i) := FixedGatePool.io.out_QSV(i)(0).asUInt
    //imag
    imagoutputmux.io.in_QSV(0)(i) := acceleratedGatePool.io.out_QSV(i)(bitwidth-1, 0)
    imagoutputmux.io.in_QSV(1)(i) := FixedGatePool.io.out_QSV(i)(1).asUInt
    FixedGatePool.io.in_QSV       := Mux(io.in_sel === 31.U, measure.io.out_QSV, QSUinput)
    measure.io.in_QSV             := QSUinput
    //mux output
    io.out_QSV(i)                 := realoutputmux.io.out_QSV(i) ## imagoutputmux.io.out_QSV(i)
  }

  //select
  realoutputmux.io.in_sel       := io.in_sel(4)   //picks between pools
  imagoutputmux.io.in_sel       := io.in_sel(4)
  acceleratedGatePool.io.in_sel := io.in_sel(3,0) //ID of gate
  FixedGatePool.io.in_sel       := Mux(enNormalize, 0.U, io.in_sel(3,0)) //ID of gate
  measure.io.in_sendNorm        := io.in_sel === 16.U

  //valid
  acceleratedGatePool.io.in_valid := Mux(io.in_sel(4), 0.B, io.in_valid)
  FixedGatePool.io.in_valid       := Mux(enNormalize, measure.io.out_valid, Mux(io.in_sel(4), io.in_valid, 0.B))
  measure.io.in_valid             := Mux(enNormalize, io.in_valid, 0.B)

  //Output valid
  io.out_valid                := Mux(io.in_sel(4), FixedGatePool.io.out_valid, acceleratedGatePool.io.out_valid)

  //other - with data conversion from uint to sint
  val UGateInput = Wire(Vec(4, Vec(2, SInt(bitwidth.W))))
  for(i <- 0 until 4) {
    UGateInput(i)(0) := io.in_Ugate(i)((bitwidth * 2) - 1, bitwidth).asSInt
    UGateInput(i)(1) := io.in_Ugate(i)(bitwidth - 1, 0).asSInt

  }
  FixedGatePool.io.in_Ugate := UGateInput
  //A vector contains 2 value despite being one coefficient. The Imaginary part should be 0.

  FixedGatePool.io.in_normalize := measure.io.out_Normalize
  measure.io.in_noise           := io.in_noise
  io.out_MQ                     := measure.io.out_measured
}
