package QuantumLayers.Circuits

import QuantumLayers.ArithmiticGates.Gates._
import QuantumLayers.ArithmiticGates.Pipline.{Gate, GeneratePiplinedGates, Perm}
import chisel3._
import chiseltest._
import firrtl2.options.TargetDirAnnotation
import org.scalatest.flatspec.AnyFlatSpec

//sbt "testOnly QuantumLayers.Circuits.Circuit0"
class Circuit0_3 extends AnyFlatSpec with ChiselScalatestTester {
  "Circuit0_3" should "Work" in
    test(new GeneratePiplinedGates(2, 16, "TestCircuit", Seq(Gate(H), Perm(2,1), Gate(X), Gate(H), Perm(2,1), Gate(SW)))).withAnnotations(
      Seq(WriteVcdAnnotation, TargetDirAnnotation("test_run_dir/QuantumLayer/Pipeline/Circuit0_3"))) { dut =>

      dut.io.in_valid.poke(1.B)

      dut.clock.step(1)

      dut.io.in_QSV(0)(0).poke(0x4000.S) // 1
      dut.io.in_QSV(0)(1).poke(0.S)
      dut.io.in_QSV(1)(0).poke(0.S)
      dut.io.in_QSV(1)(1).poke(0.S)
      dut.io.in_QSV(2)(0).poke(0.S)
      dut.io.in_QSV(2)(1).poke(0.S)
      dut.io.in_QSV(3)(0).poke(0.S)
      dut.io.in_QSV(3)(1).poke(0.S)

      dut.clock.step(1)

      dut.io.in_QSV(0)(0).poke(0.S)

      dut.clock.step(1)

      dut.io.in_QSV(0)(0).poke(0x4000.S)

      dut.clock.step(1)

      dut.io.in_QSV(0)(0).poke(0x4000.S)

      dut.clock.step(15)

    }
}
