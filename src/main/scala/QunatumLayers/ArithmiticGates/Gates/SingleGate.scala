package QuantumLayers.ArithmiticGates.Gates

import FixedPointUnit.Advanced.{FixedMult, SplitMultiplier}
import FixedPointUnit.ComplexFixedPoint._
import FixedPointUnit.FixedMultiplier
import chisel3._
import chisel3.util._

/*
      Hadamard Gate
 */
/*
//Old Multiplier
class HadamardGate(val bitwidth : Int) extends Module with GIO{
  val pointLoc = bitwidth - 2
  //val multiplierLatency = (bitwidth / 4)
  val io = IO{new GateIO(1, bitwidth)}

  val multiplier  = Seq.fill(4)(Module(new FixedMultiplier(bitwidth, pointLoc)))
  val adder       = Module(new FixedComplexAdder(bitwidth))
  val subber      = Module(new FixedComplexSubber(bitwidth))

  /*
  sqrt(1/2) | 1  1 | | a |  = sqrt(1/2) | a + b |
            | 1 -1 | | b |              | a - b |
  Aout = sqrt(1/2)
   */

  val sqrtOneHalf     = "h2D413CCD022A3B62".U(63, 64 - bitwidth).asSInt //sqrt(2) = 0.707...

  //multiply a and b with sqrt(1/2)
  multiplier(0).io.in(0) := io.in_QSV(0)(0)
  multiplier(0).io.in(1) := sqrtOneHalf
  multiplier(1).io.in(0) := io.in_QSV(0)(1)
  multiplier(1).io.in(1) := sqrtOneHalf
  multiplier(2).io.in(0) := io.in_QSV(1)(0)
  multiplier(2).io.in(1) := sqrtOneHalf
  multiplier(3).io.in(0) := io.in_QSV(1)(1)
  multiplier(3).io.in(1) := sqrtOneHalf

  // a + b = Aout
  adder.io.in_a(0) := multiplier(0).io.out_fixed.asSInt
  adder.io.in_a(1) := multiplier(1).io.out_fixed.asSInt
  adder.io.in_b(0) := multiplier(2).io.out_fixed.asSInt
  adder.io.in_b(1) := multiplier(3).io.out_fixed.asSInt
  io.out_QSV(0) := adder.io.out

  // a - b = Bout
  subber.io.in_a(0) := multiplier(0).io.out_fixed.asSInt
  subber.io.in_a(1) := multiplier(1).io.out_fixed.asSInt
  subber.io.in_b(0) := multiplier(2).io.out_fixed.asSInt
  subber.io.in_b(1) := multiplier(3).io.out_fixed.asSInt
  io.out_QSV(1) := subber.io.out

  //valid travel
  //multiplier = x reg | add/sub = 1 reg | tot = x + 1
  /*
  for(i <- 0 until 4) {
    multiplier(i).io.valid_in := io.in_valid
  }
  val delay = ShiftRegister(
    multiplier(0).io.valid_out &
    multiplier(1).io.valid_out &
    multiplier(2).io.valid_out &
    multiplier(3).io.valid_out, 1)
    */
  val delay = ShiftRegister(io.in_valid, 3)
  io.out_valid := delay
}
*/
//new Multiplier
class HadamardGate(val bitwidth : Int) extends Module with GIO {
  val pointLoc = bitwidth - 2
  val multiplierLatency = (bitwidth / 4)
  val io = IO {
    new GateIO(1, bitwidth)
  }

  val multiplier = Seq.fill(4)(Module(new SplitMultiplier(bitwidth, multiplierLatency, pointLoc)))
  val adder = Module(new FixedComplexAdder(bitwidth))
  val subber = Module(new FixedComplexSubber(bitwidth))

  /*
  sqrt(1/2) | 1  1 | | a |  = sqrt(1/2) | a + b |
            | 1 -1 | | b |              | a - b |
  Aout = sqrt(1/2)
   */

  val sqrtOneHalf = "h2D413CCD022A3B62".U(63, 64 - bitwidth).asUInt //sqrt(2) = 0.707...

  //multiply a and b with sqrt(1/2)
  multiplier(0).io.a := io.in_QSV(0)(0).asUInt
  multiplier(0).io.b := sqrtOneHalf
  multiplier(1).io.a := io.in_QSV(0)(1).asUInt
  multiplier(1).io.b := sqrtOneHalf
  multiplier(2).io.a := io.in_QSV(1)(0).asUInt
  multiplier(2).io.b := sqrtOneHalf
  multiplier(3).io.a := io.in_QSV(1)(1).asUInt
  multiplier(3).io.b := sqrtOneHalf

  // a + b = Aout
  adder.io.in_a(0) := multiplier(0).io.out_fixed.asSInt
  adder.io.in_a(1) := multiplier(1).io.out_fixed.asSInt
  adder.io.in_b(0) := multiplier(2).io.out_fixed.asSInt
  adder.io.in_b(1) := multiplier(3).io.out_fixed.asSInt
  io.out_QSV(0) := adder.io.out

  // a - b = Bout
  subber.io.in_a(0) := multiplier(0).io.out_fixed.asSInt
  subber.io.in_a(1) := multiplier(1).io.out_fixed.asSInt
  subber.io.in_b(0) := multiplier(2).io.out_fixed.asSInt
  subber.io.in_b(1) := multiplier(3).io.out_fixed.asSInt
  io.out_QSV(1) := subber.io.out

  //valid travel
  //multiplier = x reg | add/sub = 1 reg | tot = x + 1
  for (i <- 0 until 4) {
    multiplier(i).io.valid_in := io.in_valid
  }
  val delay = ShiftRegister(
    multiplier(0).io.valid_out &
      multiplier(1).io.valid_out &
      multiplier(2).io.valid_out &
      multiplier(3).io.valid_out, 1)

  //val delay = ShiftRegister(io.in_valid, 3)
  io.out_valid := delay
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

class SqrtXGate(val bitwidth : Int) extends Module with GIO{
  val io = IO(new GateIO(1, bitwidth))
  /*
1/2 | 1+i 1-i | | a+ib |  = 1/2 | ( a-b+c+d )+i( a+b-c+d ) |
    | 1-i 1+i | | c+id |        | ( a+b+c-d )+i(-a+b+c+d ) |
   */

  //first layer of adder and subber
  //add
  val AaddB = Reg(SInt(bitwidth.W))
  val CaddD = Reg(SInt(bitwidth.W))
  AaddB := io.in_QSV(0)(0) + io.in_QSV(0)(1)
  CaddD := io.in_QSV(1)(0) + io.in_QSV(1)(1)
  //subtract
  val AsubB = Reg(SInt(bitwidth.W))
  val CsubD = Reg(SInt(bitwidth.W))
  val BsubA = Reg(SInt(bitwidth.W))
  val DsubC = Reg(SInt(bitwidth.W))
  AsubB := io.in_QSV(0)(0) - io.in_QSV(0)(1)
  CsubD := io.in_QSV(1)(0) - io.in_QSV(1)(1)
  BsubA := io.in_QSV(0)(1) - io.in_QSV(0)(0)
  DsubC := io.in_QSV(1)(1) - io.in_QSV(1)(0)

  //second layer of Adders
  val Real0 = Reg(SInt(bitwidth.W))
  val Imag0 = Reg(SInt(bitwidth.W))
  val Real1 = Reg(SInt(bitwidth.W))
  val Imag1 = Reg(SInt(bitwidth.W))
  Real0 := AsubB + CaddD
  Imag0 := AaddB + DsubC
  Real1 := AaddB + CsubD
  Imag1 := BsubA + CaddD

  //outsignal
  io.out_QSV(0)(0) := Real0 >> 1
  io.out_QSV(0)(1) := Imag0 >> 1
  io.out_QSV(1)(0) := Real1 >> 1
  io.out_QSV(1)(1) := Imag1 >> 1

  //valid signal
  val delayed = ShiftRegister(io.in_valid, 2)
  io.out_valid := delayed
}

class SqrtXDaggerGate(val bitwidth : Int) extends Module with GIO{
  val io = IO(new GateIO(1, bitwidth))
  /*
1/2 | 1-i 1+i | | a+ib |  = 1/2 | ( a+b+c-d )+i(-a+b+c+d ) |
    | 1+i 1-i | | c+id |        | ( a-b+c-d )+i( a+b-c+d ) |
   */

  //first layer of adder and subber
  //add
  val AaddB = Reg(SInt(bitwidth.W))
  val CaddD = Reg(SInt(bitwidth.W))
  AaddB := io.in_QSV(0)(0) + io.in_QSV(0)(1)
  CaddD := io.in_QSV(1)(0) + io.in_QSV(1)(1)
  //subtract
  val AsubB = Reg(SInt(bitwidth.W))
  val CsubD = Reg(SInt(bitwidth.W))
  val BsubA = Reg(SInt(bitwidth.W))
  val DsubC = Reg(SInt(bitwidth.W))
  AsubB := io.in_QSV(0)(0) - io.in_QSV(0)(1)
  CsubD := io.in_QSV(1)(0) - io.in_QSV(1)(1)
  BsubA := io.in_QSV(0)(1) - io.in_QSV(0)(0)
  DsubC := io.in_QSV(1)(1) - io.in_QSV(1)(0)

  //second layer of Adders
  val Real0 = Reg(SInt(bitwidth.W))
  val Imag0 = Reg(SInt(bitwidth.W))
  val Real1 = Reg(SInt(bitwidth.W))
  val Imag1 = Reg(SInt(bitwidth.W))
  Real0 := AaddB + CsubD
  Imag0 := BsubA + CaddD
  Real1 := AsubB + CaddD
  Imag1 := AaddB + DsubC

  //outsignal
  io.out_QSV(0)(0) := Real0 >> 1
  io.out_QSV(0)(1) := Imag0 >> 1
  io.out_QSV(1)(0) := Real1 >> 1
  io.out_QSV(1)(1) := Imag1 >> 1
  //valid signal
  val delayed = ShiftRegister(io.in_valid, 1)
  io.out_valid := delayed
}


class XGate(val bitwidth : Int) extends Module with GIO{
  val io = IO{new GateIO(1, bitwidth)}
  /*
    | 0 1 | | a | = | b |
    | 1 0 | | b |   | a |
   */
  val reg   = RegInit(io.in_QSV)
  reg := io.in_QSV
  io.out_QSV    := VecInit(reg(1), reg(0)) //swap values
  io.out_valid  := ShiftRegister(io.in_valid, 1)
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
  io.out_valid  := ShiftRegister(io.in_valid, 1)
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
  io.out_valid  := ShiftRegister(io.in_valid, 1)
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
  reg := io.in_QSV
  io.out_QSV := VecInit(reg(0), reg(1), reg(3), reg(2))
  io.out_valid := ShiftRegister(io.in_valid, 1)
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
  delay := io.in_valid
  reg := io.in_QSV
  io.out_QSV := VecInit(reg(0), reg(2), reg(1), reg(3 ))
  io.out_valid := delay
}