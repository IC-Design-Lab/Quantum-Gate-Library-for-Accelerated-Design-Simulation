import FixedPointUnit.FixedAdder
import FixedPointUnit.Advanced.{FixedMult, SplitMultiplier}
import QuantumLayers.ArithmiticGates.Gates.{H, SW, SX, TestPermVariant, X}
import QuantumLayers.ArithmiticGates.Permutation.Gate.CompatiblePerm
import QuantumLayers.ArithmiticGates.Permutation.{SwapPositionXandY, rearrangeTo1stPosition}
import QuantumLayers.ArithmiticGates.Permutation.tie._
import QuantumLayers.ArithmiticGates.Pipline.{Gate, GeneratePiplinedGates, Perm}
import chisel3._
import circt.stage.ChiselStage

object main extends App{
  println(">>> Starting elaboration Group 0")
  emitVerilog(new GeneratePiplinedGates(2, 16, "NewMultCircuit0_0-6", Seq(Gate(H), Gate(SW))))
  emitVerilog(new GeneratePiplinedGates(2, 16, "NewMultCircuit0_1-7", Seq(Gate(H), Perm(2,1), Gate(X), Perm(2,1), Gate(SW))))
  emitVerilog(new GeneratePiplinedGates(2, 16, "NewMultCircuit0_2", Seq(Gate(H), Perm(2,1), Gate(H), Perm(2,1), Gate(SW))))
  emitVerilog(new GeneratePiplinedGates(2, 16, "NewMultCircuit0_3", Seq(Gate(H), Perm(2,1), Gate(X), Gate(H), Perm(2,1), Gate(SW))))
  emitVerilog(new GeneratePiplinedGates(2, 16, "NewMultCircuit0_4", Seq(Gate(H), Perm(2,1), Gate(SX), Perm(2,1), Gate(SW))))
  emitVerilog(new GeneratePiplinedGates(2, 16, "NewMultCircuit0_5", Seq(Gate(H), Perm(2,1), Gate(X), Gate(SX), Perm(2,1), Gate(SW))))

  println(">>> Starting elaboration Group 1")
  emitVerilog(new GeneratePiplinedGates(2, 16, "NewMultCircuit1_0-1-6-7", Seq(Gate(H), Perm(2,1), Gate(SW), Perm(2,1))))
  emitVerilog(new GeneratePiplinedGates(2, 16, "NewMultCircuit1_2-3", Seq(Gate(H), Perm(2,1), Gate(SW), Perm(2,1), Gate(H))))
  emitVerilog(new GeneratePiplinedGates(2, 16, "NewMultCircuit1_4-5", Seq(Gate(H), Perm(2,1), Gate(SW), Perm(2,1), Gate(SX))))

  println(">>> Verilog emission complete")
}

/*
object main extends App {
  println(">>> Starting elaboration")
  emitVerilog(new GeneratePiplinedGates(2, 16, "NewMultiplierCircuit0_3", Seq(Gate(H), Perm(2,1), Gate(X), Gate(H), Perm(2,1), Gate(SW))))
  println(">>> Elaboration finished")
}
*/
/*

object main extends App {
  println(">>> Starting elaboration")
  emitVerilog(new SplitMultiplier(16,4,14))
  println(">>> Elaboration finished")
}

 */

