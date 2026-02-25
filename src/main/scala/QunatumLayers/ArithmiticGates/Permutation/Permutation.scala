package QuantumLayers.ArithmiticGates.Permutation

import chisel3._
import QuantumLayers.ArithmiticGates.Permutation.tie._

/*---------------------------------
      Reroute signals: |Kx> <-> |K1>
 --------------------------------*/
//Swap target ket vector position to the beginning of the tensor product.
class rearrangeTo1stPosition(val ketsizeexponent : Int, val bitwidth : Int, val target : Int) extends Module {
  val size = 1 << (ketsizeexponent)
  val io = IO(new Bundle{
    val in_ket  =  Input(Vec(size, Vec(2, UInt(bitwidth.W))))
    val out_ket = Output(Vec(size, Vec(2, UInt(bitwidth.W))))
  })
  //To get rid of initialization error
  io.out_ket := io.in_ket


  //copying the algorithm
  val x = ketsizeexponent
  val s = target
  for (i <- 0 until (1 << (x - s))) { //skips the mirrored part of the pattern
    for (j <- 0 until (1 << (s - 2))) { //the pattern
      //switching values
      io.out_ket((2*j) + (1<<(s-1)) + (i*(1<<s)))    := io.in_ket((2*j+1) + (i*(1<<s)))
      io.out_ket((2*j+1) + (i*(1<<s)))               := io.in_ket((2*j) + (1<<(s-1)) + (i*(1<<s)))

      //same
      io.out_ket((i*(1<<s)) + (j*(1<<(x-1))))        := io.in_ket((i*(1<<s)) + (j*(1<<(x-1))))
      io.out_ket((2*j+1) + (1<<(s-1)) + (i*(1<<s)))  := io.in_ket((2*j+1) + (1<<(s-1)) + (i*(1<<s)))
    }
  }
}


//Swap the position of 2 tensor products
class SwapPositionXandY(val ketsizeexponent : Int, val bitwidth : Int, val target0 : Int, val target1 : Int) extends Module{

  val HigherRank  = math.max(target0, target1)
  val LesserRank = math.min(target0, target1)
  val size = 1 << (ketsizeexponent)

  override def desiredName = s"SwapPosition${HigherRank}and${LesserRank}atSize${size}"

  val io = IO(new Bundle{
    val in_ket  =  Input(Vec(size, Vec(2, UInt(bitwidth.W))))
    val out_ket = Output(Vec(size, Vec(2, UInt(bitwidth.W))))
  })

  val outReg = RegInit(io.out_ket)

  val   tieVectorLayer = Module(new   tieVecLayer(ketsizeexponent, bitwidth, LesserRank - 1))
  val untieVectorLayer = Module(new untieVecLayer(ketsizeexponent, bitwidth, LesserRank - 1))
  val algorithm        = Module(new rearrangeTo1stPosition(
    ketsizeexponent - LesserRank + 1,
    bitwidth * (1 << (LesserRank)),
    HigherRank - LesserRank))

  //io -> tie
  tieVectorLayer.io.in_QSV    := io.in_ket
  //tie -> algorithm -> untie
  algorithm.io.in_ket         := tieVectorLayer.io.out_QSV
  untieVectorLayer.io.in_QSV  := algorithm.io.out_ket
  //untie -> io
  outReg                      := untieVectorLayer.io.out_QSV
  io.out_ket                  := outReg
}
