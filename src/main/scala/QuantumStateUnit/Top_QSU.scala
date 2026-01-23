package QuantumStateUnit

import Complex_FPU.complex_conjugate_mult
import FPUMatrixMult.FPUMatrixMult
import QuantumStateUnit.GateArchitecture.FixedPointGatePool._
import QuantumStateUnit.GateArchitecture.FPUGatePool._
import FixedPointUnit._
import New_FPU_Mario.FPUnits.{FP_add, FP_mult}
import QuantumStateUnit.GateArchitecture.FPUGatePool.Matrix.FPUPool
import QuantumStateUnit.GateArchitecture.FPUGatePool._
import QuantumStateUnit.GateArchitecture.FixedPointGatePool.Matrix.MatrixMult_SquareXKet
import QuantumStateUnit.GateArchitecture.FixedPointGatePool.MeasurementGate.Components.{CollapseProbability, CompareWithRandom, GetNormalization}
import QuantumStateUnit.GateArchitecture.FixedPointGatePool.MeasurementGate.MeasurementGate
import QuantumStateUnit.OtherComponents.CondenseInputs._
import QuantumStateUnit.QSU_Architecture._
import chisel3._
import chisel3.util._

import scala.math.{ceil, log, pow}

//Five values to be adjusted (qubits, bw, length, pd, pd)
/*
Accepted bit width is:      32, 64,128,256
accepted propagation delay:  1,  3,  7,  8, 10, 13
*/

class TopQSU(val num_of_qubits : Int, val bit_width : Int, val useFPU : Boolean) extends Module{
  var FixedOrFloat = "Unknown"
  if(useFPU == false){
    FixedOrFloat = "Fixed"
  } else if(useFPU == true){
    FixedOrFloat = "FPU"
  }
  override def desiredName = s"${FixedOrFloat}TopQSU${bit_width}bit_${num_of_qubits}qubit"

  //When less than 3 qubits
  var Sequence = Seq(0, 1, 2) //Default
  var loop     = 3
  if(num_of_qubits == 1){
    Sequence  = Seq(0)
    loop      = 1
  } else if(num_of_qubits == 2){
    Sequence  = Seq(0, 1)
    loop      = 2
  }

  val io = IO(new Bundle{
    val in_QSV              = Input(Vec(pow(2,num_of_qubits).toInt, UInt((2 * bit_width).W)))
    val in_Permutaiton_Sel  = Input(MixedVec(Sequence.map(i => UInt(ceil(log(num_of_qubits - i)/log(2)).toInt.W))))
    val in_Gate_Sel         = Input(UInt(5.W))
    val in_Ugate            = Input(Vec(4, UInt((2 * bit_width).W)))
    val in_applyGate        = Input(Bool()) //Applies the input gate onto the register: doesn't work when flag is 1.B
    val in_en_replaceQSV    = Input(Bool()) //Replaces Initial state and algorithm with above inputs
    val in_noise            = Input(UInt(32.W))
    val out_state           = Output(Vec(pow(2,num_of_qubits).toInt, UInt((2 * bit_width).W)))
    val out_flag            = Output(Bool()) //says when ready to apply next gate. While 1.B, applyGate will not take any input
  })

  val manager     = Module(new QSUController)
  val gatePool    = Module(new gatepool_FixOFlo(num_of_qubits, bit_width, useFPU))
  val permutation = Module(new StackedPermutationSwitchGrids(num_of_qubits, 2 * bit_width, Sequence))
  val reversePerm = Module(new StackedPermutationSwitchGrids(num_of_qubits, 2 * bit_width, Sequence))
  val QSR         = Module(new QuantumStateRegister(num_of_qubits, 2 * bit_width))
  val hold        = Module(new QSUSelHold(num_of_qubits, Sequence))

  //Quantum state Register inputs
  QSR.io.in_QSV             := reversePerm.io.out_QSV
  QSR.io.in_new_state       := io.in_QSV
  QSR.io.in_en              := manager.io.out_update_QSR
  QSR.io.in_en_new_state    := manager.io.out_replaceQSV
  //Permutation inputs
  permutation.io.in_QSV     := QSR.io.out_QSV
  for(i <- 0 until loop){
    permutation.io.in_sel(i) := io.in_Permutaiton_Sel(i)
  }
  //reverse permutation inputs
  reversePerm.io.in_QSV     := gatePool.io.out_QSV
  for(i <- 0 until loop){
    reversePerm.io.in_sel(i)   := hold.io.out_sel_perm(i)
  }
  //Gate Pool inputs
  gatePool.io.in_QSV        := permutation.io.out_QSV
  gatePool.io.in_sel        := hold.io.out_sel_gate
  gatePool.io.in_valid      := manager.io.out_en_QGP
  gatePool.io.in_Ugate      := io.in_Ugate
  gatePool.io.in_noise      := io.in_noise
  //Manager Inputs
  manager.io.in_valid       := gatePool.io.out_valid
  manager.io.in_applygate   := io.in_applyGate
  manager.io.in_replaceQSV  := io.in_en_replaceQSV
  //hold sel
  hold.io.in_sel_gate       := io.in_Gate_Sel
  for(i <- 0 until loop){
    hold.io.in_sel_perm(i)  := io.in_Permutaiton_Sel(i)
  }
  hold.io.in_hold           := manager.io.out_readyFlag

  //Outputs
  io.out_state := QSR.io.out_QSV
  io.out_flag  := manager.io.out_readyFlag
}


/*
The TopQSU has too many IOs to actually be implemented onto an FPGA.
This design adds shift registers to the IOs in order to reduce the amount of IOs.
 */
class TopQSU_ShftIO(val num_of_qubits : Int, val bit_width : Int, val useFPU : Boolean) extends Module {
  var FixedOrFloat = "Unknown"
  if(useFPU == false){
    FixedOrFloat = "Fixed"
  } else if(useFPU == true){
    FixedOrFloat = "FPU"
  }
  override def desiredName = s"${FixedOrFloat}TopQSU${bit_width}bit_${num_of_qubits}qubit_ShftRegIO"

  //When less than 3 qubits
  var Sequence = Seq(0, 1, 2) //Default
  if (num_of_qubits == 1) {
    Sequence = Seq(0)
  } else if (num_of_qubits == 2) {
    Sequence = Seq(0, 1)
  }

  val io = IO(new Bundle {
    val in_QSV            = Input(Vec(pow(2, num_of_qubits).toInt, Bool()))
    val in_Permutaiton_Sel= Input(MixedVec(Sequence.map(i => UInt(ceil(log(num_of_qubits - i) / log(2)).toInt.W))))
    val in_Gate_Sel       = Input(UInt(5.W))
    val in_Ugate          = Input(Vec(4, Bool()))
    val in_applyGate      = Input(Bool()) //Applies the input gate onto the register: doesn't work when flag is 1.B
    val in_en_replaceQSV  = Input(Bool()) //Replaces Initial state and algorithm with above inputs
    val in_noise          = Input(Bool())
    val out_state         = Output(Vec(pow(2, num_of_qubits).toInt, Bool()))
    val out_flag          = Output(Bool()) //says when ready to apply next gate. While 1.B, applyGate will not take any input
    //add enable to control the input and ouput of the QSU
    val in_feedEn         = Input(Bool()) //
    val in_UgateEn        = Input(Bool()) //
    val in_outfeedEn      = Input(Bool()) //
    val in_noiseEn        = Input(Bool()) //
  })

  //help manages the inputs and outputs
  val feedQSV     = Module(new ShiftRegisterInput(pow(2,num_of_qubits).toInt, bit_width))
  val feedUgate   = Module(new ShiftRegisterInput(4, bit_width))
  val feedNoise   = Module(new ShiftRegisterInput(1, 32))
  val outfeed     = Module(new ShiftRegisterOutput(pow(2,num_of_qubits).toInt, bit_width))
  val QSU         = Module(new TopQSU(num_of_qubits, bit_width, useFPU))

  //feed into device
  feedQSV.io.in   := io.in_QSV
  feedQSV.io.en   := io.in_feedEn
  feedUgate.io.in := io.in_Ugate
  feedUgate.io.en := io.in_UgateEn
  feedNoise.io.in(0) := io.in_noise
  feedNoise.io.en := io.in_noiseEn

  //QSU Inputs
  QSU.io.in_QSV             := feedQSV.io.out
  QSU.io.in_Permutaiton_Sel := io.in_Permutaiton_Sel
  QSU.io.in_Gate_Sel        := io.in_Gate_Sel
  QSU.io.in_applyGate       := io.in_applyGate
  QSU.io.in_en_replaceQSV   := io.in_en_replaceQSV
  QSU.io.in_noise.asUInt    := feedNoise.io.out.asUInt
  QSU.io.in_Ugate           := feedUgate.io.out

  //Outfeed
  outfeed.io.in             := QSU.io.out_state
  outfeed.io.en             := io.in_outfeedEn

  //Output
  io.out_state              := outfeed.io.out
  io.out_flag               := QSU.io.out_flag
}

object main extends App{
  //TopQSU(num_of_qubits : Int, bit_width : Int, mult_pd : Int, add_pd : Int, L : Int)
  emitVerilog(new TopQSU(10, 32, false))
}
/*
object main extends App{
  emitVerilog(new FPUMatrixMult(1, 32, 1, 1))
}
 */