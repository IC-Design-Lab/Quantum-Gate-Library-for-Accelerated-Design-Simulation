package QuantumLayers.PerformanceEvaluation

import QuantumLayers.ArithmiticGates.Permutation
import QuantumLayers.ArithmiticGates.Permutation.Gate.CompatiblePerm
import QuantumLayers.ArithmiticGates.Permutation.{SwapPositionXandY, rearrangeTo1stPosition}
import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

//sbt "testOnly QuantumLayers.PerformanceEvaluation.PermutationTest"
class PermutationTest extends AnyFlatSpec with ChiselScalatestTester {
  "SwitchPermutation0" should "GeneratePattern" in
    test(new SwapPositionXandY(3, 16, 3, 2)) { dut =>

      val numElems = dut.io.in_ket.length  // number of rows (e.g., 4)
      val complexSize = dut.io.in_ket(0).length  // should be 2 (real, imag)

      // Example input data
      val inputValues =
        (0 until numElems).map { i =>
          Seq((i).U, (i).U)
        }

      // Poke all inputs using loops
      for (i <- 0 until numElems) {
        for (j <- 0 until complexSize) {
          dut.io.in_ket(i)(j).poke(inputValues(i)(j))
        }
      }

      dut.clock.step(2)

      // Read all inputs and outputs using loops
      val inputRead =
        (0 until numElems).map { i =>
          (0 until complexSize).map { j =>
            dut.io.in_ket(i)(j).peek().litValue
          }
        }

      val outputRead =
        (0 until numElems).map { i =>
          (0 until complexSize).map { j =>
            dut.io.out_ket(i)(j).peek().litValue
          }
        }

      // Print everything
      println("=== INPUTS ===")
      for (i <- 0 until numElems) {
        println(s"Input  $i: Real = ${inputRead(i)(0)}   Imag = ${inputRead(i)(1)}")
      }

      println("=== OUTPUTS ===")
      for (i <- 0 until numElems) {
        println(s"Output $i: Real = ${outputRead(i)(0)}   Imag = ${outputRead(i)(1)}")
      }
    }
}


class PermutationTest0 extends AnyFlatSpec with ChiselScalatestTester {
  "SwitchPermutation0" should "GeneratePattern" in
    test(new CompatiblePerm(3, 16, 2, 1)) { dut =>

      val numElems = dut.io.in_QSV.length  // number of rows (e.g., 4)
      val complexSize = dut.io.in_QSV(0).length  // should be 2 (real, imag)

      // Example input data
      val inputValues =
        (0 until numElems).map { i =>
          Seq((i).S, (i).S)
        }

      // Poke all inputs using loops
      for (i <- 0 until numElems) {
        for (j <- 0 until complexSize) {
          dut.io.in_QSV(i)(j).poke(inputValues(i)(j))
        }
      }

      dut.clock.step(5)

      // Read all inputs and outputs using loops
      val inputRead =
        (0 until numElems).map { i =>
          (0 until complexSize).map { j =>
            dut.io.in_QSV(i)(j).peek().litValue
          }
        }

      val outputRead =
        (0 until numElems).map { i =>
          (0 until complexSize).map { j =>
            dut.io.out_QSV(i)(j).peek().litValue
          }
        }

      // Print everything
      println("=== INPUTS ===")
      for (i <- 0 until numElems) {
        println(s"Input  $i: Real = ${inputRead(i)(0)}   Imag = ${inputRead(i)(1)}")
      }

      println("=== OUTPUTS ===")
      println(s"Out Valid Signal: ${dut.io.out_valid.peek().litValue}")
      for (i <- 0 until numElems) {
        println(s"Output $i: Real = ${outputRead(i)(0)}   Imag = ${outputRead(i)(1)}")
      }
    }
}