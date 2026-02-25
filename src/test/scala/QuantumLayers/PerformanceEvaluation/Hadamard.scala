package QuantumLayers.PerformanceEvaluation

import QuantumLayers.ArithmiticGates._
import QuantumLayers.ArithmiticGates.Gates._
import chisel3._
import chiseltest._
import firrtl2.options.TargetDirAnnotation
import org.scalatest.flatspec.AnyFlatSpec

import scala.math.pow

//sbt "testOnly QuantumLayers.PerformanceEvaluation.Hadamard3"
class Hadamard3 extends AnyFlatSpec with ChiselScalatestTester {
  "Hadamard3" should "not disappoint" in
    test(new SpanVector(3, 16, H)).withAnnotations(
      Seq(WriteVcdAnnotation, TargetDirAnnotation("test_run_dir/QuantumLayer/Circuits/Hadamard/3Qubit"))) { dut =>

      dut.clock.step(1)

      dut.io.in_QSV(0)(0).poke(0x4000.S)
      dut.io.in_QSV(0)(1).poke(0.S)
      for(i <- 1 until pow(2,3).toInt){
        dut.io.in_QSV(i)(0).poke(0.S)
        dut.io.in_QSV(i)(1).poke(0.S)
      }
      dut.io.in_valid.poke(1.B)

      dut.clock.step(4)
    }
}