package QuantumLayers.Circuits

import QuantumLayers.ArithmiticGates.Gates._
import QunatumLayers.ArithmiticGates.Pipline.{Gate, GeneratePiplinedGates}
import chisel3._
import chiseltest._
import firrtl2.options.TargetDirAnnotation
import org.scalatest.flatspec.AnyFlatSpec

//sbt "testOnly QuantumLayers.Circuits.Circuit0"
class Circuit0 extends AnyFlatSpec with ChiselScalatestTester {
  "Circuit0" should "Work" in
    test(new GeneratePiplinedGates(2, 16, "TestCircuit", Seq(Gate(H), Gate(SW)))).withAnnotations(
      Seq(WriteVcdAnnotation, TargetDirAnnotation("test_run_dir/QuantumLayer/Pipeline/Circuit0"))) { dut =>

      dut.clock.step(1)

      dut.io.in_QSV(0)(0).poke(0x4000.S) //sqrt(1/2)
      dut.io.in_QSV(0)(1).poke(0.S)
      dut.io.in_QSV(1)(0).poke(0.S) //sqrt(1/2)
      dut.io.in_QSV(1)(1).poke(0.S)
      dut.io.in_QSV(2)(0).poke(0.S) //sqrt(1/2)
      dut.io.in_QSV(2)(1).poke(0.S)
      dut.io.in_QSV(3)(0).poke(0.S) //sqrt(1/2)
      dut.io.in_QSV(3)(1).poke(0.S)

      dut.io.in_valid.poke(1.B)

      dut.clock.step(4)
    }
}
