package FixedGatePool_Test

import QuantumStateUnit.GateArchitecture.FixedPointGatePool.Matrix.{FixedGateMult, MatrixMult_SquareXKet}
import QuantumStateUnit.GateArchitecture.FixedPointGatePool._
import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class TestFixedMatrixMult extends AnyFlatSpec with ChiselScalatestTester {
  "Fixed" should "MatrixMultiply" in
    test(new MatrixMult_SquareXKet(2, 16)) { dut =>

      var vecIn  =    Seq(Seq(0x2D41, 0x0000), // sqrt(1/2)
                          Seq(0x2D41, 0x0000), // sqrt(1/2)
                          Seq(0x0000, 0x0000), // sqrt(1/2)
                          Seq(0x0000, 0x0000)) // sqrt(1/2)
        for(i <- 0 until 4) {
        dut.io.in_QSV.zip(vecIn).foreach { case (vector, complexNum) =>
          vector.zip(complexNum).foreach{case (prob, values) =>
            prob.poke(values.S)
          }
        }
      }

      /* Combine values into a single matrix
      U gate =  | a  b |
                | c  d |
       */
      var gateIn =   Seq( Seq(0x0000, 0x0000), // a = 0
                          Seq(0x4000, 0x0000), // b = 1
                          Seq(0x4000, 0x0000), // c = 1
                          Seq(0x0000, 0x0000)) // d = 0
      for(i <- 0 until 4) {
        dut.io.in_Ugate.zip(gateIn).foreach { case (vector, complexNum) =>
          vector.zip(complexNum).foreach{case (prob, values) =>
            prob.poke(values.S)
          }
        }
      }
      for(i <- 0 until  2){
        println(s"\nValue at clock: $i clk; The flag is: ${dut.io.out_valid.peek().litValue} " +
          s"\n a1 = ${dut.io.out_QSV(0)(0).peek().litValue.toString(16)} + j${dut.io.out_QSV(0)(1).peek().litValue.toString(16)}" +
          s"\n a1 = ${dut.io.out_QSV(1)(0).peek().litValue.toString(16)} + j${dut.io.out_QSV(1)(1).peek().litValue.toString(16)}" +
          s"\n a1 = ${dut.io.out_QSV(2)(0).peek().litValue.toString(16)} + j${dut.io.out_QSV(2)(1).peek().litValue.toString(16)}" +
          s"\n a1 = ${dut.io.out_QSV(2)(0).peek().litValue.toString(16)} + j${dut.io.out_QSV(3)(1).peek().litValue.toString(16)}")
        dut.io.in_valid.poke(1.B)
        dut.clock.step()
      }

      var gateIn1=   Seq( Seq(0x2D41, 0x0000),
                          Seq(0x2D41, 0x0000),
                          Seq(0x2D41, 0x0000),
                          Seq((0x2D41 * -1), 0x0000))
      for(i <- 0 until 4) {
        dut.io.in_Ugate.zip(gateIn1).foreach { case (vector, complexNum) =>
          vector.zip(complexNum).foreach{case (prob, values) =>
            prob.poke(values.S)
          }
        }
      }
      for(i <- 0 until  2){
        println(s"\nValue at clock: $i clk; The flag is: ${dut.io.out_valid.peek().litValue} " +
          s"\n a1 = ${dut.io.out_QSV(0)(0).peek().litValue.toString(16)} + j${dut.io.out_QSV(0)(1).peek().litValue.toString(16)}" +
          s"\n a1 = ${dut.io.out_QSV(1)(0).peek().litValue.toString(16)} + j${dut.io.out_QSV(1)(1).peek().litValue.toString(16)}" +
          s"\n a1 = ${dut.io.out_QSV(2)(0).peek().litValue.toString(16)} + j${dut.io.out_QSV(2)(1).peek().litValue.toString(16)}" +
          s"\n a1 = ${dut.io.out_QSV(2)(0).peek().litValue.toString(16)} + j${dut.io.out_QSV(3)(1).peek().litValue.toString(16)}")
        dut.io.in_valid.poke(1.B)
        dut.clock.step()
      }
    }
}

class TestFixedGateMult extends AnyFlatSpec with ChiselScalatestTester {
  "Fixed" should "MatrixMultiply" in
    test(new FixedGateMult(2, 16)) { dut =>

      //INPUT VECTOR: sqrt(1/2)|00> + sqrt(1/2)|01>
      var vecIn = Seq(Seq(0x2D41, 0x0000), // sqrt(1/2)
        Seq(0x2D41, 0x0000), // sqrt(1/2)
        Seq(0x0000, 0x0000), // sqrt(1/2)
        Seq(0x0000, 0x0000)) // sqrt(1/2)
      for (i <- 0 until 4) {
        dut.io.in_QSV.zip(vecIn).foreach { case (vector, complexNum) =>
          vector.zip(complexNum).foreach { case (prob, values) =>
            prob.poke(values.S)
          }
        }
      }
      //No operation gate
      var gateIn = Seq(Seq(0x4000, 0x0000), // a = 0
        Seq(0x0000, 0x0000), // b = 1
        Seq(0x0000, 0x0000), // c = 1
        Seq(0x4000, 0x0000)) // d = 0
      for (i <- 0 until 4) {
        dut.io.in_Ugate.zip(gateIn).foreach { case (vector, complexNum) =>
          vector.zip(complexNum).foreach { case (prob, values) =>
            prob.poke(values.S)
          }
        }
      }
      dut.io.in_sel.poke(6.U)

      for (i <- 0 until 2) {
        println(s"\nValue at clock: $i clk; The flag is: ${dut.io.out_valid.peek().litValue} " +
          s"\n a1 = ${dut.io.out_QSV(0)(0).peek().litValue.toString(16)} + j${dut.io.out_QSV(0)(1).peek().litValue.toString(16)}" +
          s"\n a1 = ${dut.io.out_QSV(1)(0).peek().litValue.toString(16)} + j${dut.io.out_QSV(1)(1).peek().litValue.toString(16)}" +
          s"\n a1 = ${dut.io.out_QSV(2)(0).peek().litValue.toString(16)} + j${dut.io.out_QSV(2)(1).peek().litValue.toString(16)}" +
          s"\n a1 = ${dut.io.out_QSV(2)(0).peek().litValue.toString(16)} + j${dut.io.out_QSV(3)(1).peek().litValue.toString(16)}")
        dut.io.in_valid.poke(1.B)
        dut.clock.step()
      }
      dut.io.in_sel.poke(1.U)
      for (i <- 0 until 2) {
        println(s"\nValue at clock: $i clk; The flag is: ${dut.io.out_valid.peek().litValue} " +
          s"\n a1 = ${dut.io.out_QSV(0)(0).peek().litValue.toString(16)} + j${dut.io.out_QSV(0)(1).peek().litValue.toString(16)}" +
          s"\n a1 = ${dut.io.out_QSV(1)(0).peek().litValue.toString(16)} + j${dut.io.out_QSV(1)(1).peek().litValue.toString(16)}" +
          s"\n a1 = ${dut.io.out_QSV(2)(0).peek().litValue.toString(16)} + j${dut.io.out_QSV(2)(1).peek().litValue.toString(16)}" +
          s"\n a1 = ${dut.io.out_QSV(2)(0).peek().litValue.toString(16)} + j${dut.io.out_QSV(3)(1).peek().litValue.toString(16)}")
        dut.io.in_valid.poke(1.B)
        dut.clock.step()
      }
      }
}