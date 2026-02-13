package Misc

import FixedPointUnit._
import FixedPointUnit.ComplexFixedPoint._
import QuantumStateUnit.GateArchitecture.FixedPointGatePool.MeasurementGate.Components.CompareWithRandom
import QuantumStateUnit.OtherComponents.PsuedoRandomGenerator.LinearCongruentialGenerator
import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

import scala.util.Random

class TestProbabilityOfCompare extends AnyFlatSpec with ChiselScalatestTester {
  "Compare" should "Have_Equal_Prob" in
    test(new CompareWithRandom(16,14)) { dut =>
      dut.io.in_probability.poke(0x2000.S)
      dut.io.in_en.poke(1.B)
      dut.io.in_seed.poke(0.U)
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
          dut.io.in_seed.poke(i.U)

        }
        dut.clock.step() //Every Step
      }
      println(s"------------END----------")
      println(s"Occurence of 0 = $out0 \tOccurence of 1 = $out1")

    }
}

/*
class linearConvergence extends AnyFlatSpec with ChiselScalatestTester {
  "Generator" should "create_pseudoRand" in
    test(new LinearCongruentialGenerator(32,32,5,1)) { dut =>
      dut.io.in
      for(i <- 0 until 100){

      }
    }
}

 */