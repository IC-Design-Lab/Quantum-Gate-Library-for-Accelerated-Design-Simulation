package QuantumLayers.ArithmiticGates

import chisel3._
import chisel3.util._
import QuantumLayers.ArithmiticGates.IO._
import FixedPointUnit.ComplexFixedPoint._

import scala.math._

/*
      Hadamard Gate
 */
class HadamardGate(val bitwidth : Int) extends Module with GIO{
  val pointLoc = bitwidth - 2
  val io = IO{new GateIO(1, bitwidth)}

  val multiplier  = Seq.fill(2)(Module(new FixedComplexMultiplier(bitwidth, pointLoc)))
  val adder       = Module(new FixedComplexAdder(bitwidth))
  val subber      = Module(new FixedComplexSubber(bitwidth))

  /*
  sqrt(1/2) | 1  1 | | a |  = sqrt(1/2) | a + b |
            | 1 -1 | | b |              | a - b |
  Aout = sqrt(1/2)
   */

  val sqrtOneHalf     = "h2D413CCD022A3B62".U(63, 64 - bitwidth).asSInt //sqrt(2) = 0.707...
  val zero            = 0.S(63, 64 - bitwidth).asSInt

  //multiply a and b with sqrt(1/2)
  multiplier(0).io.in_a := io.in_QSV(0)
  multiplier(0).io.in_b := VecInit(sqrtOneHalf, zero)
  multiplier(1).io.in_a := io.in_QSV(1)
  multiplier(1).io.in_b := VecInit(sqrtOneHalf, zero)

  // a + b = Aout
  adder.io.in_a := multiplier(0).io.out
  adder.io.in_b := multiplier(1).io.out
  io.out_QSV(0) := adder.io.out

  // a - b = Bout
  subber.io.in_a := multiplier(0).io.out
  subber.io.in_b := multiplier(1).io.out
  io.out_QSV(1) := adder.io.out

  //valid travel
  //multiplier = 2 reg | add/sub = 1 reg | tot = 3
  val delayed = ShiftRegister(io.in_valid, 3)
  io.out_valid := delayed
}

/*
      Phase pi/8 Gate or T gate
 */
class TGate(val bitwidth : Int) extends Module with GIO{
  val pointLoc = bitwidth - 2
  val io = IO{new GateIO(1, bitwidth)}

  val multiplier  = Module(new FixedComplexMultiplier(bitwidth, pointLoc))

  /*
  | 1     0    | | a |  = |      a       |
  | 0 e^(pi/8) | | b |    | b * e^(pi/4) |
  Aout = sqrt(1/2)
   */

  val sqrtOneHalf     = "h2D413CCD022A3B62".U(63, 64 - bitwidth).asSInt //sqrt(1/2) = 0.707...

  //e^(pi/4) = sqrt(1/2) + j sqrt(1/2)
  multiplier.io.in_a := io.in_QSV(1)
  multiplier.io.in_b := VecInit(sqrtOneHalf, sqrtOneHalf)

  //io out
  val regout  = RegInit(io.in_QSV(0))
  regout      := io.in_QSV(0)
  io.out_QSV := VecInit(regout, multiplier.io.out)

  //valid travel
  //multiplier = 2 reg | tot = 2
  val delayed = ShiftRegister(io.in_valid, 2)
  io.out_valid := delayed
}

class XGate(val bitwidth : Int) extends Module with GIO{
  val io = IO{new GateIO(1, bitwidth)}
  /*
    | 0 1 | | a | = | b |
    | 1 0 | | b |   | a |
   */
  val reg   = RegInit(io.in_QSV)
  val delay = RegInit(io.in_valid)
  reg := io.in_QSV
  io.out_QSV    := VecInit(reg(1), reg(0)) //swap values
  io.out_valid  := delay
}

class YGate(val bitwidth : Int) extends Module with GIO{
  val io = IO{new GateIO(1, bitwidth)}
  /*
Matrix:   | 0  j |
          | -j 0 |
QSV:      | a+jb |  -> new QSV ->  |-d+jc |
          | c+jd |                 | b-ja |
   */
  val negA   = WireInit((io.in_QSV(0)(0)*(-1.S)).asSInt)
  val negD   = WireInit((io.in_QSV(1)(1)*(-1.S)).asSInt)

  val value0 = RegInit(VecInit(negD, io.in_QSV(1)(0)))
  val value1 = RegInit(VecInit(io.in_QSV(0)(1), negA))

  value0  := VecInit(negD, io.in_QSV(1)(0))
  value1  := VecInit(io.in_QSV(0)(1), negA)

  io.out_QSV := VecInit(value0, value1)
  val delay  = RegInit(io.in_valid)
  io.out_valid  := delay
}

class ZGate(val bitwidth : Int) extends Module with GIO{
  val io = IO{new GateIO(1, bitwidth)}
  /*
Matrix:   | 1  0 |
          | 0 -1 |
QSV:      | a+jb |  -> new QSV ->  | a+jb |
          | c+jd |                 |-c-jd |
   */
  val negC   = WireInit((io.in_QSV(1)(0)*(-1.S)).asSInt)
  val negD   = WireInit((io.in_QSV(1)(1)*(-1.S)).asSInt)

  val value0 = RegInit(io.in_QSV(0))
  val value1 = RegInit(VecInit(negC, negD))

  value0 := io.in_QSV(0)
  value1 := VecInit(negD, negC)

  io.out_QSV := VecInit(value0, value1)
  val delay  = RegInit(io.in_valid)
  io.out_valid  := delay
}

class ControlledNotGate(val bitwidth : Int) extends Module with GIO {
  val io = IO {new GateIO(2, bitwidth)}
  /*
  Matrix: | 1 0 0 0 |
          | 0 1 0 0 |
          | 0 0 0 1 |
          | 0 0 1 0 |
   */
  val reg   = RegInit(io.in_QSV)
  val delay = RegInit(io.in_valid)
  reg := io.in_QSV
  io.out_QSV := VecInit(reg(0), reg(1), reg(3), reg(2))
  io.out_valid := delay
}

class SwapGate(val bitwidth : Int) extends Module with GIO {
  val io = IO {new GateIO(2, bitwidth)}
/*
Matrix: | 1 0 0 0 |
        | 0 0 1 0 |
        | 0 1 0 0 |
        | 0 0 0 1 |
 */
  val reg   = RegInit(io.in_QSV)
  val delay = RegInit(io.in_valid)
  reg := io.in_QSV
  io.out_QSV := VecInit(reg(0), reg(2), reg(1), reg(3 ))
  io.out_valid := delay
}