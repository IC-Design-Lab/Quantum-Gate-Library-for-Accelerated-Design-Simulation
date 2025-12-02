package QSU_Test.QSU_Architecture

import QuantumStateUnit.QSU_Architecture._
import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
/*
//Checks the mux input of the QSR
class QSR_Test extends AnyFlatSpec with ChiselScalatestTester {
  "Register" should "ProperlyUpdate" in
    test(new QuantumStateRegister(2, 2)) { dut =>
      //Initial State Given
      dut.io.in_new_state(0).poke(VecInit(0.U,0.U))
      dut.io.in_new_state(1).poke(VecInit(1.U,0.U))
      dut.io.in_new_state(2).poke(VecInit(2.U,0.U))
      dut.io.in_new_state(3).poke(VecInit(3.U,0.U))
      //Updated State
      dut.io.in_QSV(0).poke(VecInit(3.U,0.U))
      dut.io.in_QSV(1).poke(VecInit(2.U,0.U))
      dut.io.in_QSV(2).poke(VecInit(1.U,0.U))
      dut.io.in_QSV(3).poke(VecInit(0.U,0.U))

      //give new state
      dut.clock.step()
      dut.io.in_en.poke(1.B)
      dut.io.in_en_new_state.poke(1.B)

      //output === Initial given state
      dut.clock.step()
      dut.io.out_QSV(0).expect(VecInit(0.U,0.U))
      dut.io.out_QSV(1).expect(VecInit(1.U,0.U))
      dut.io.out_QSV(2).expect(VecInit(2.U,0.U))
      dut.io.out_QSV(3).expect(VecInit(3.U,0.U))

      //replace with updated state
      dut.clock.step(2)
      dut.io.in_en.poke(1.B)
      dut.io.in_en_new_state.poke(0.B)

      //output === Initial given state
      dut.clock.step()
      dut.io.out_QSV(0).expect(VecInit(3.U,0.U))
      dut.io.out_QSV(1).expect(VecInit(2.U,0.U))
      dut.io.out_QSV(2).expect(VecInit(1.U,0.U))
      dut.io.out_QSV(3).expect(VecInit(0.U,0.U))
    }
}

 */