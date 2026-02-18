package FixedPointUnit.ComplexFixedPoint

import chisel3._
import chisel3.util._
import FixedPointUnit._

class FixedComplexAdder(val bitwidth : Int) extends Module{
  val io = IO(new Bundle{
    val in_a =  Input(Vec(2, SInt(bitwidth.W)))
    val in_b =  Input(Vec(2, SInt(bitwidth.W)))
    val out  = Output(Vec(2, SInt(bitwidth.W)))
  })
  val Adder = Seq.fill(2)(Module(new FixedAdder(bitwidth, true)))

  // a + bj + c + dj = (a + c) + (bj + dj)
  Adder(0).io.in(0) := io.in_a(0)
  Adder(0).io.in(1) := io.in_b(0)

  Adder(1).io.in(0) := io.in_a(1)
  Adder(1).io.in(1) := io.in_b(1)

  io.out(0) := Adder(0).io.out
  io.out(1) := Adder(1).io.out
}

class FixedComplexSubber(val bitwidth : Int) extends Module{
  val io = IO(new Bundle{
    val in_a =  Input(Vec(2, SInt(bitwidth.W)))
    val in_b =  Input(Vec(2, SInt(bitwidth.W)))
    val out  = Output(Vec(2, SInt(bitwidth.W)))
  })
  val Subber = Seq.fill(2)(Module(new FixedSubber(bitwidth, true)))

  // (a + bj) - (c + dj) = (a - c) + (bj - dj)
  Subber(0).io.in(0) := io.in_a(0)
  Subber(0).io.in(1) := io.in_b(0)

  Subber(1).io.in(0) := io.in_a(1)
  Subber(1).io.in(1) := io.in_b(1)

  io.out(0) := Subber(0).io.out
  io.out(1) := Subber(1).io.out
}


class FixedComplexMultiplier(val bitwidth : Int, val pointLoc : Int) extends Module{
  val io = IO(new Bundle{
    val in_a =  Input(Vec(2, SInt(bitwidth.W))) // a + bj
    val in_b =  Input(Vec(2, SInt(bitwidth.W))) // c + dj
    val out  = Output(Vec(2, SInt(bitwidth.W)))
  })
  val Subber      = Module(new FixedSubber(bitwidth, true))
  val Adder       = Module(new FixedAdder(bitwidth, true))
  val Multiplier  = Seq.fill(4)(Module(new FixedMultiplier(bitwidth, pointLoc)))

  // (a + bj)(c + dj) = (a*c - b*d) + (a*d + b*c)j

  // a * c
  Multiplier(0).io.in(0) := io.in_a(0) //a
  Multiplier(0).io.in(1) := io.in_b(0) //c
  // b * d
  Multiplier(1).io.in(0) := io.in_a(1) //b
  Multiplier(1).io.in(1) := io.in_b(1) //d
  // a*c - b*d
  Subber.io.in(0) := Multiplier(0).io.out
  Subber.io.in(1) := Multiplier(1).io.out

  // a * d
  Multiplier(2).io.in(0) := io.in_a(0) //a
  Multiplier(2).io.in(1) := io.in_b(1) //d
  // b * c
  Multiplier(3).io.in(0) := io.in_a(1) //b
  Multiplier(3).io.in(1) := io.in_b(0) //c
  // a*d + b*c
  Adder.io.in(0) := Multiplier(2).io.out
  Adder.io.in(1) := Multiplier(3).io.out

  //output
  io.out(0) := Subber.io.out
  io.out(1) := Adder.io.out
}