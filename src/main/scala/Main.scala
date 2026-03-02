import FixedPointUnit.FixedAdder
import QuantumLayers.ArithmiticGates.Gates.{H, SW, SX, X}
import QuantumLayers.ArithmiticGates.Permutation.Gate.CompatiblePerm
import QuantumLayers.ArithmiticGates.Permutation.{SwapPositionXandY, rearrangeTo1stPosition}
import QuantumLayers.ArithmiticGates.Permutation.tie._
import QunatumLayers.ArithmiticGates.Pipline.{Gate, GeneratePiplinedGates, Perm}
import chisel3._
import circt.stage.ChiselStage


object main extends App{
  println(">>> Starting elaboration Group 0")
  emitVerilog(new GeneratePiplinedGates(2, 16, "Circuit0_0-6", Seq(Gate(H), Gate(SW))))
  emitVerilog(new GeneratePiplinedGates(2, 16, "Circuit0_1-7", Seq(Gate(H), Perm(2,1), Gate(X), Perm(2,1), Gate(SW))))
  emitVerilog(new GeneratePiplinedGates(2, 16, "Circuit0_2", Seq(Gate(H), Perm(2,1), Gate(H), Perm(2,1), Gate(SW))))
  emitVerilog(new GeneratePiplinedGates(2, 16, "Circuit0_3", Seq(Gate(H), Perm(2,1), Gate(X), Gate(H), Perm(2,1), Gate(SW))))
  emitVerilog(new GeneratePiplinedGates(2, 16, "Circuit0_4", Seq(Gate(H), Perm(2,1), Gate(SX), Perm(2,1), Gate(SW))))
  emitVerilog(new GeneratePiplinedGates(2, 16, "Circuit0_5", Seq(Gate(H), Perm(2,1), Gate(X), Gate(SX), Perm(2,1), Gate(SW))))

  println(">>> Starting elaboration Group 1")
  emitVerilog(new GeneratePiplinedGates(2, 16, "Circuit1_0-1-6-7", Seq(Gate(H), Perm(2,1), Gate(SW), Perm(2,1))))
  emitVerilog(new GeneratePiplinedGates(2, 16, "Circuit1_2-3", Seq(Gate(H), Perm(2,1), Gate(SW), Perm(2,1), Gate(H))))
  emitVerilog(new GeneratePiplinedGates(2, 16, "Circuit1_4-5", Seq(Gate(H), Perm(2,1), Gate(SW), Perm(2,1), Gate(SX))))

  println(">>> Verilog emission complete")
}


/*
object main extends App {
  println(">>> Starting elaboration of CompatablePerm")

  val verilog = (new chisel3.stage.ChiselStage).emitVerilog(
    new CompatablePerm(2, 16, 2, 1),
    Array("--target-dir", "generated")
  )

  println(">>> Elaboration finished")
}

 */
/*
object main extends App {
  println(">>> Starting elaboration")

  ChiselStage.emitSystemVerilog(
    new FixedAdder(16, true),
    Array("--target-dir", "generated")
  )

  println(">>> Finished elaboration")
}

 */
/*
object main extends App{ //3 7 15 23
  emitVerilog(new CompatiblePerm(3,16,3,2))
  println(">>> Elaborated. Starting FIRRTL emission...")
}

 */
