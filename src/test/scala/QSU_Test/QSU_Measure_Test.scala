package QSU_Test

import QuantumStateUnit.GateArchitecture.FPUGatePool.MeasurementGate.Components.CompareWithRandom
import QuantumStateUnit._
import chisel3._
import chiseltest._
import firrtl2.options.TargetDirAnnotation
import org.scalatest.flatspec.AnyFlatSpec

import scala.util.Random

class OneQubitMeasure_Test extends AnyFlatSpec with ChiselScalatestTester {
  "QSU" should "Measure_1Qubit" in
    test(new TopQSU(/*qubits*/ 1, /*bw*/ 32, true)).withAnnotations(
      Seq(WriteVcdAnnotation, TargetDirAnnotation("test_run_dir/Measurement_Timing"))) { dut =>
      dut.io.in_QSV(0).poke("h39a80000".U) //000 : 0.707...
      dut.io.in_QSV(1).poke("h39a80000".U) //001 : 0.707...
      dut.clock.step()
      dut.io.in_en_replaceQSV.poke(1.B)
      dut.clock.step()
      dut.io.in_en_replaceQSV.poke(0.B)
      dut.clock.step(5)

      dut.io.in_Gate_Sel.poke(31.U)
      dut.io.in_noise.poke("hab348cf0".U)
      dut.clock.step()
      dut.io.in_applyGate.poke(1.B)
      dut.clock.step()
      dut.io.in_applyGate.poke(0.B)
      dut.clock.step(80)
      dut.io.out_flag.expect(1.B)
    }
}

class TwoQubitMeasure_Test extends AnyFlatSpec with ChiselScalatestTester {
    "QSU" should "Measure_2Qubit" in
      test(new TopQSU(/*qubits*/ 2, /*bw*/ 32, true)).withAnnotations(
        Seq(WriteVcdAnnotation, TargetDirAnnotation("test_run_dir/Measurement_Timing"))) { dut =>
          dut.io.in_QSV(0).poke("h38000000".U) //000 : 0.5...
          dut.io.in_QSV(1).poke("h38000000".U) //001 : 0.5...
          dut.io.in_QSV(2).poke("h38000000".U) //000 : 0.5...
          dut.io.in_QSV(3).poke("h38000000".U) //001 : 0.5...
          dut.clock.step()
          dut.io.in_en_replaceQSV.poke(1.B)
          dut.clock.step()
          dut.io.in_en_replaceQSV.poke(0.B)
          dut.clock.step(5)

          dut.io.in_Gate_Sel.poke(31.U)
          dut.io.in_noise.poke("hab348cf0".U)
          dut.clock.step()
          dut.io.in_applyGate.poke(1.B)
          dut.clock.step()
          dut.io.in_applyGate.poke(0.B)
          dut.clock.step(80)
          dut.io.out_flag.expect(1.B)
      }
}

class ThreeQubitMeasure_Test extends AnyFlatSpec with ChiselScalatestTester {
  "QSU" should "Measure_3Qubit" in
    test(new TopQSU(/*qubits*/ 3, /*bw*/ 32, true)).withAnnotations(
      Seq(WriteVcdAnnotation, TargetDirAnnotation("test_run_dir/Measurement_Timing"))) { dut =>
      dut.io.in_QSV(0).poke("h35a80000".U) //000 : 0.25...
      dut.io.in_QSV(1).poke("h35a80000".U) //001 : 0.25...
      dut.io.in_QSV(2).poke("h35a80000".U) //000 : 0.25...
      dut.io.in_QSV(3).poke("h35a80000".U) //001 : 0.25...
      dut.io.in_QSV(4).poke("h35a80000".U) //000 : 0.25...
      dut.io.in_QSV(5).poke("h35a80000".U) //001 : 0.25...
      dut.io.in_QSV(6).poke("h35a80000".U) //000 : 0.25...
      dut.io.in_QSV(7).poke("h35a80000".U) //001 : 0.25...
      dut.clock.step()
      dut.io.in_en_replaceQSV.poke(1.B)
      dut.clock.step()
      dut.io.in_en_replaceQSV.poke(0.B)
      dut.clock.step(5)

      dut.io.in_Gate_Sel.poke(31.U)
      dut.io.in_noise.poke("hab348cf0".U)
      dut.clock.step()
      dut.io.in_applyGate.poke(1.B)
      dut.clock.step()
      dut.io.in_applyGate.poke(0.B)
      dut.clock.step(80)
      dut.io.out_flag.expect(1.B)
    }
}

class TestProbability extends AnyFlatSpec with ChiselScalatestTester {
  "Probability" should "be_equal_to_input" in
    test(new CompareWithRandom(16,1)).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.io.in_probability.poke("h0000".U)
      dut.io.in_en.poke(1.B)
      dut.io.in_seed.poke(Random.nextInt(65536))
      dut.io.in_sel.poke(0.B) //Probability of 0
      dut.io.in_valid.poke(1.B)

      dut.clock.step()

      var out0 = 0
      var out1 = 0
      for(i <- 0 until 100){
        if(dut.io.out_valid.peekBoolean()){
          println(s"At clk $i - output value: ${dut.io.out_value.peekBoolean()}")
          if(dut.io.out_value.peekBoolean()){
            out1 = out1+1
          }else{
            out0 = out0+1
          }
          //reset enable
          dut.clock.step()
          dut.io.in_valid.poke(0.B)
          while(dut.io.out_valid.peekBoolean()){
            dut.clock.step()
          }
          dut.io.in_valid.poke(1.B)
          dut.io.in_seed.poke(Random.nextInt(i))

        }
        dut.clock.step() //Every Step
      }
      println(s"------------END----------")
      println(s"Occurence of 0 = $out0 \tOccurence of 1 = $out1")
    }
}