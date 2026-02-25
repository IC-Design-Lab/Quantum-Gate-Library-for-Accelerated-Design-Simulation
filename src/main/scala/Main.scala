import FixedPointUnit.FixedAdder
import QuantumLayers.ArithmiticGates.Gates.{H, SW}
import QuantumLayers.ArithmiticGates.Permutation.Gate.CompatiblePerm
import QuantumLayers.ArithmiticGates.Permutation.{SwapPositionXandY, rearrangeTo1stPosition}
import QuantumLayers.ArithmiticGates.Permutation.tie._
import QunatumLayers.ArithmiticGates.Pipline.{Gate, GeneratePiplinedGates}
import chisel3._
import circt.stage.ChiselStage



object main extends App{
  println(">>> Starting elaboration")
  emitVerilog(new GeneratePiplinedGates(2, 16, Seq(Gate(H), Gate(SW))))
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
