package QuantumLayers.ArithmiticGates.Permutation.tie

import chisel3._
import chisel3.util.Cat



/*
...
Initialize what comes in and out of the muxes
Moving a qubit to the 2nd, 3rd, ect, follows the same algorithm but moves 2^qubit_position at a time

!!tie amount has to be one less than the total of vectors being put together
!!tie amount encapusulates 2^tie_amount vectors
...
 */
//combines n amount of vectors into one coefficient
class tieVec(val bit_width : Int, val tie_amount : Int) extends Module{
  val io = IO(new Bundle{
    val in  =  Input(Vec(tie_amount, Vec(2, UInt(bit_width.W))))
    val out = Output(Vec(2, UInt((tie_amount*bit_width).W)))
  })

  //Collecting all vectors into one input
  val realinputs: Seq[UInt] = Seq.fill(tie_amount)(Wire(UInt(bit_width.W)))
  val imaginputs: Seq[UInt] = Seq.fill(tie_amount)(Wire(UInt(bit_width.W)))
  for(i <- 0 until tie_amount){
    realinputs(i) := io.in(i)(0)
    imaginputs(i) := io.in(i)(1)
  }
  //correcting the direction of the data
  io.out(0) := Cat(realinputs.reverse)
  io.out(1) := Cat(imaginputs.reverse)
}

class tieVecLayer(val ketSizeExponent : Int, val bit_width : Int, val tie_amount : Int) extends Module{
  val io = IO(new Bundle{
    val in_QSV  =  Input(Vec((1 << (ketSizeExponent)) , Vec(2, UInt(bit_width.W))))
    val out_QSV = Output(Vec((1 << (ketSizeExponent - tie_amount)), Vec(2, UInt(( bit_width * (1 << tie_amount)).W))))
  })
  //the zip tie to connect to vectors together
  val tieVector = Seq.fill((1 << (ketSizeExponent - tie_amount)))(Module(new tieVec(bit_width, 1 << (tie_amount))))
  //putting into respective inputs and outputs
  for(j <- 0 until (1 << ketSizeExponent - tie_amount)) {//number of tieVector
    for (i <- 0 until (1 << (tie_amount))) { //number of input
      tieVector(j).io.in(i) := io.in_QSV((1 << tie_amount) * j + i)
    }
    io.out_QSV(j) := tieVector(j).io.out
  }
}


/*-----------------------------
    Redefine Signals: 32 bit + 32 bit = 64 bit // 2 numbers in one value
 ----------------------------*/
class untieVec( val bit_width : Int, val tie_amount : Int) extends Module{
  val io = IO(new Bundle{
    val in  =  Input(Vec(2, UInt((tie_amount * bit_width).W)))
    val out = Output(Vec(tie_amount , Vec(2, UInt(bit_width.W))))
  })
  for(i <- 0 until tie_amount){ //Combine real and imag seperately
    io.out(i)(0) := io.in(0)((i+1)*bit_width-1,i*bit_width)
    io.out(i)(1) := io.in(1)((i+1)*bit_width-1,i*bit_width)
  }
}
class untieVecLayer(val ketSizeExponent : Int, val bit_width : Int, val tie_amount  : Int) extends Module{
  val io = IO(new Bundle{
    val in_QSV  =  Input(Vec(1 << (ketSizeExponent - tie_amount), Vec(2, UInt((bit_width * (1 << (tie_amount))).W))))
    val out_QSV = Output(Vec(1 << (ketSizeExponent), Vec(2, UInt(bit_width.W))))
  })
  //To separate the vectors from each other
  val untieVector = Seq.fill(1 << (ketSizeExponent - tie_amount))(Module(new untieVec(bit_width, (1 << (tie_amount)))))
  //putting into respective inputs and outputs
  for(j <- 0 until (1 << (ketSizeExponent - tie_amount))) {//number of untieVector
    // 1 input 2^x outputs
    untieVector(j).io.in := io.in_QSV(j)
    for (i <- 0 until (1 << (tie_amount))) { //number of output
      io.out_QSV(((1 << (tie_amount)) * j) + i) := untieVector(j).io.out(i)
    }
  }
}