package QuantumLayers.ArithmiticGates

import chisel3._
import QuantumLayers.ArithmiticGates.IO._

import scala.math.pow

sealed trait SpecifiedGate {def size : Int}
case object H extends SpecifiedGate {def size = 1} //Hadamard gate
case object T extends SpecifiedGate {def size = 1} //e^(pi/8) or T gate or phase gate
case object X extends SpecifiedGate {def size = 1} //Not gate
case object Y extends SpecifiedGate {def size = 1} //Y gate
case object Z extends SpecifiedGate {def size = 1} //Z gate
case object CN extends SpecifiedGate {def size = 2} //Controlled Not Gate
case object SW extends SpecifiedGate {def size = 2} //Swap Gate

class ChooseGate(val bitwidth : Int, val GateType : SpecifiedGate) extends Module with GIO{
  val io = IO(new GateIO(GateType.size, bitwidth))
  //Determine what gate is going to span the entire vector.
  val gate = GateType match{
    case H => Module(new HadamardGate(bitwidth))
    case T => Module(new TGate(bitwidth))
    case X => Module(new XGate(bitwidth))
    case Y => Module(new YGate(bitwidth))
    case Z => Module(new ZGate(bitwidth))
    case CN => Module(new ControlledNotGate(bitwidth))
    case SW => Module(new SwapGate(bitwidth))
  }
  gate.io.in_QSV    := io.in_QSV
  gate.io.in_valid  := io.in_valid
  io.out_valid      := gate.io.out_valid
  io.out_QSV        := gate.io.out_QSV
}

class SpanVector(val num_of_qubits : Int, val bitwidth : Int, val GateType : SpecifiedGate) extends Module with GIO{
  override def desiredName = s"${GateType}Gate${num_of_qubits}QubitVector${bitwidth}bit"
  val io = IO(new GateIO(num_of_qubits, bitwidth))

  //Determine what gate is going to span the entire vector.
  val numberOfGates = pow(2, num_of_qubits - GateType.size).toInt
  val gate          = Seq.fill(numberOfGates)(Module(new ChooseGate(bitwidth, GateType)))

  //connect valid signals
  for(i <- 0 until numberOfGates){
    gate(i).io.in_valid := io.in_valid
    io.out_valid  := gate(i).io.out_valid.andR
  }

  //connect ket vector
  val singleGateVectorSize = pow(2, GateType.size).toInt
  for(rank <- 0 until numberOfGates){
    for(input <- 0 until singleGateVectorSize){
      gate(rank).io.in_QSV(input) := io.in_QSV(singleGateVectorSize * rank + input)
      io.out_QSV(singleGateVectorSize * rank + input) := gate(rank).io.out_QSV(input)
    }
  }
}
