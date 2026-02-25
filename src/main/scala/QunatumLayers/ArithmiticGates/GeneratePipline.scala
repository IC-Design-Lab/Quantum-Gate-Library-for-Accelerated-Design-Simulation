package QunatumLayers.ArithmiticGates.Pipline

import QuantumLayers.ArithmiticGates.Gates.{GateIO, SpanVector, SpecifiedGate}
import QuantumLayers.ArithmiticGates.Permutation.Gate.CompatiblePerm
import chisel3._

//In order to differentiate between the two designs when generating the pipeline
sealed trait GateVsPerm
case class Gate(Gate : SpecifiedGate)         extends GateVsPerm
case class Perm(target0 : Int, target1 : Int) extends GateVsPerm

// Bundle that carries pipeline state
class PipeState(numQubits: Int, bitwidth: Int) extends Bundle {
  val QSV   = Vec(1 << numQubits, Vec(2, SInt(bitwidth.W)))
  val valid = Bool()
}

class GeneratePiplinedGates(val num_of_qubits : Int, val bitwidth : Int, val Circuit : Seq[GateVsPerm]) extends Module{

  val io = IO(new GateIO(num_of_qubits, bitwidth))

  //This will hold the outputs of the previous layer
  var current = Wire(new PipeState(num_of_qubits, bitwidth))
  current.QSV   := io.in_QSV
  current.valid := io.in_valid

  for(layer <- Circuit){      // Generates the each layer of the circuit
    val next = Wire(new PipeState(num_of_qubits, bitwidth))

    layer match{

      case Gate(gate) =>
        val m = Module(new SpanVector(num_of_qubits, bitwidth, gate))
        m.io.in_QSV   := current.QSV
        m.io.in_valid := current.valid
        next.QSV      := m.io.out_QSV
        next.valid    := m.io.out_valid

      case Perm(target0, target1) =>
        val m = Module(new CompatiblePerm(num_of_qubits, bitwidth, target0, target1))
        m.io.in_QSV   := current.QSV
        m.io.in_valid := current.valid
        next.QSV      := m.io.out_QSV
        next.valid    := m.io.out_valid

    }

    current = next
  }

  io.out_QSV    := current.QSV
  io.out_valid  := current.valid
}
