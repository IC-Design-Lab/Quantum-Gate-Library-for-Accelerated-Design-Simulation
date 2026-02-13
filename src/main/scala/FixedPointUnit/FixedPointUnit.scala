package FixedPointUnit

import chisel3._
import chisel3.util.ShiftRegister


class FixedAdder(val bitwidth : Int, val regOut : Boolean) extends Module {
  override def desiredName = s"Fixed${bitwidth}BitAdder"
  val io = IO(new Bundle{
    val in  =  Input(Vec(2, SInt(bitwidth.W)))
    val out = Output(SInt(bitwidth.W))
  })
  if(regOut){
    val reg = RegInit(0.S(bitwidth.W))
    reg    :=  io.in(0) + io.in(1)
    io.out := reg
  }else {
    io.out := io.in(0) + io.in(1)
  }
}

class FixedMultiplier(val bitwidth : Int, val pointLoc : Int) extends Module{
  override def desiredName = s"Fixed${bitwidth}BitMultiplier"
  val io = IO(new Bundle{
    val in  =  Input(Vec(2, SInt(bitwidth.W)))
    val out = Output(SInt(bitwidth.W))
  })
  val wire0   = Wire(SInt((bitwidth + pointLoc).W))
  val wire1   = Wire(SInt((bitwidth + pointLoc).W))
  val wireout = Wire(SInt((bitwidth + pointLoc).W))

  wire0 := io.in(0).pad(2 * pointLoc)
  wire1 := io.in(1).pad(2 * pointLoc)
  wireout := wire0 * wire1

  val outreg = RegInit(0.S(bitwidth.W))
  outreg := wireout(bitwidth - 1 + pointLoc, pointLoc).asSInt
  io.out := outreg
}

class FixedDivision(val bitwidth : Int, val pointLoc : Int) extends Module{
  override def desiredName = s"Fixed${bitwidth}BitDivider"
  val io = IO(new Bundle{
    val in_num  =  Input(SInt(bitwidth.W))
    val in_den  =  Input(SInt(bitwidth.W))
    val out     = Output(SInt(bitwidth.W))
  })
  val wirenum   = WireInit(0.S((bitwidth + pointLoc).W))
  val wireden   = WireInit(0.S((bitwidth).W))
  val wireout   = WireInit(0.S((bitwidth + pointLoc).W))

  wirenum := io.in_num << (pointLoc)
  wireden := io.in_den
  wireout := wirenum * wireden

  val outreg = RegInit(0.S(bitwidth.W))
  outreg := wireout(bitwidth - 1 + pointLoc, pointLoc).asSInt
  io.out := outreg
}

//This function is the bain of existance
class FixedSquareRoot(val bitwidth : Int, val pointLoc : Int) extends Module{
  override def desiredName = s"Fixed${bitwidth}SquareRoot"
  val io = IO(new Bundle{
    val in  =  Input(SInt(bitwidth.W))
    val out = Output(SInt(bitwidth.W))
  })
  /*
  First the number will be found using Chebyshev polynomials,
    then the answer will be designed by raphsedy method.

    The Chebyshev polynomials recurrence definition is:
      T0(x)   = 1
      T1(x)   = x
      Tn+1(x) = 2xTn(x)-Tn-1(x)

      Refine using the newton rapson method
   */
  val EndOfClass = 0.U
}


/*
This is a simple square root unit that will recursivly add to the square root.
Has the time estamate of O(n) where n is the number of bits.
 */
class SimpleFixedSquareRoot(val bitwidth : Int, val pointLoc : Int) extends Module {
  override def desiredName = s"Fixed${bitwidth}SquareRoot"
  val io = IO(new Bundle {
    val in        =  Input(SInt(bitwidth.W))
    val in_valid  =  Input(Bool())
    val out_valid = Output(Bool())
    val out       = Output(SInt(bitwidth.W))
  })

  /*
  Continously add 1 to a number until the result squared is equal to or slightly greater than the true square:
      x <- input
      y <- ouput
  This action will be split into multiple chunks of 4 bits.
   */

  /*
  Below is algorithm of the process. It's not the best, but it should do for
  now as a unit that will only show up in one location in the gate multiplier.
       y = 0
       while(x^2  <  y){
       x++;
       }
   */

  //Register
  val currentValue  = RegInit(0.S(bitwidth.W))
  val outReg        = RegInit(0.S(bitwidth.W))
  val validReg      = RegInit(0.B)
  io.out        := currentValue
  io.out_valid  := validReg

  // x*x = x^2
  val multiplier    = Module(new FixedMultiplier(bitwidth, pointLoc))
  val multiplierReg = RegInit(0.S(bitwidth.W))
  multiplier.io.in  :=VecInit(currentValue, currentValue)
  multiplierReg     := multiplier.io.out

  //The long process of calculating the square
  when(multiplierReg < io.in){
    currentValue  := currentValue + 1.S
    validReg      := 0.B
  }
  //valid
  when((multiplierReg <= io.in) & io.in_valid){
    validReg := 1.B
    outReg   := currentValue
  }
  //if the input valid is 0, it's assumed that there is a new input
  when(!io.in_valid){
    currentValue := 0.S
    validReg     := 0.B
  }
  //If the value becomes to large
  when(multiplier.io.out > io.in + 1.S){
    currentValue := currentValue + -1.S
  }
}