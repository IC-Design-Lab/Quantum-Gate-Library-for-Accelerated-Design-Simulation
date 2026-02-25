package QuantumLayers.PerformanceEvaluation

import QuantumLayers.ArithmiticGates._
import QuantumLayers.ArithmiticGates.Gates._
import chisel3._
import chiseltest._
import firrtl2.options.TargetDirAnnotation
import org.scalatest.flatspec.AnyFlatSpec

import scala.math.pow

//sbt "testOnly QuantumLayers.PerformanceEvaluation.TGate2"
class TGate2 extends AnyFlatSpec with ChiselScalatestTester {
  "TGate2" should "not disappoint" in
    test(new SpanVector(2, 16, T)).withAnnotations(
      Seq(WriteVcdAnnotation, TargetDirAnnotation("test_run_dir/QuantumLayer/Circuits/TGate/2Qubit"))) { dut =>

      dut.clock.step(1)

      dut.io.in_QSV(0)(0).poke(0x2D41.S) //sqrt(1/2)
      dut.io.in_QSV(0)(1).poke(0.S)
      dut.io.in_QSV(1)(0).poke(0x2D41.S) //sqrt(1/2)
      dut.io.in_QSV(1)(1).poke(0.S)

      dut.io.in_valid.poke(1.B)

      dut.clock.step(4)
    }
}
