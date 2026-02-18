package QuantumLayers.PerformanceEvaluation

import QuantumLayers.ArithmiticGates._
import chisel3._
import chiseltest._
import firrtl2.options.TargetDirAnnotation
import org.scalatest.flatspec.AnyFlatSpec

import scala.math.pow

//sbt "testOnly QuantumLayers.PerformanceEvaluation.Hadamard3"
class Hadamard3 extends AnyFlatSpec with ChiselScalatestTester {
  "QSU" should "Create state, then mesure" in
    test(new SpanVector(3, 16, H)).withAnnotations(
      Seq(WriteVcdAnnotation, TargetDirAnnotation("test_run_dir/QuantumLayer/Circuits/Hadamard/3Qubit"))) { dut =>

      dut.clock.step(1)

      dut.io.in_QSV(0)(0).poke(0x2000.S)
      dut.io.in_QSV(0)(1).poke(0.S)
      for(i <- 1 until pow(2,3).toInt){
        dut.io.in_QSV(i)(0).poke(0.S)
        dut.io.in_QSV(i)(1).poke(0.S)
      }
      dut.io.in_valid.poke(1.B)

      dut.clock.step(4)
    }
}

class Hadamard7 extends AnyFlatSpec with ChiselScalatestTester {
  "QSU" should "Create state, then mesure" in
    test(new SpanVector(7, 16, H)).withAnnotations(
      Seq(WriteVcdAnnotation, TargetDirAnnotation("test_run_dir/QuantumLayer/Circuits/Hadamard/7Qubit"))) { dut =>

      dut.clock.step(1)

      dut.io.in_QSV(0).poke(VecInit(0x2000.S,0.S))
      for(i <- 1 until pow(2,7).toInt){
        dut.io.in_QSV(i).poke(VecInit(0.S, 0.S))
      }
      dut.io.in_valid := 1.B

      dut.clock.step(4)
    }
}

class Hadamard15 extends AnyFlatSpec with ChiselScalatestTester {
  "QSU" should "Create state, then mesure" in
    test(new SpanVector(15, 16, H)).withAnnotations(
      Seq(WriteVcdAnnotation, TargetDirAnnotation("test_run_dir/QuantumLayer/Circuits/Hadamard/3Qubit"))) { dut =>

      dut.clock.step(1)

      dut.io.in_QSV(0).poke(VecInit(0x2000.S,0.S))
      for(i <- 1 until pow(2,15).toInt){
        dut.io.in_QSV(i).poke(VecInit(0.S, 0.S))
      }
      dut.io.in_valid := 1.B

      dut.clock.step(4)
    }
}

class Hadamard extends AnyFlatSpec with ChiselScalatestTester {
  "QSU" should "Create state, then mesure" in
    test(new SpanVector(23, 16, H)).withAnnotations(
      Seq(WriteVcdAnnotation, TargetDirAnnotation("test_run_dir/QuantumLayer/Circuits/Hadamard/3Qubit"))) { dut =>

      dut.clock.step(1)

      dut.io.in_QSV(0).poke(VecInit(0x2000.S,0.S))
      for(i <- 1 until pow(2,23).toInt){
        dut.io.in_QSV(i).poke(VecInit(0.S, 0.S))
      }
      dut.io.in_valid := 1.B

      dut.clock.step(4)
    }
}