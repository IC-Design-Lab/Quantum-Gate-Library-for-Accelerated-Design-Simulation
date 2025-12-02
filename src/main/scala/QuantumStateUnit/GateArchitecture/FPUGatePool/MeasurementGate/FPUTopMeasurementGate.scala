package QuantumStateUnit.GateArchitecture.FPUGatePool.MeasurementGate

import QuantumStateUnit.GateArchitecture.FPUGatePool.MeasurementGate.Components._
import chisel3._
import chisel3.util._

import scala.math._

/*
Measurement is the top design of multiple components: retrieve probability, random number generator.
  When a qubit is measured, a new QSV is generated alongside with its normalization number.
  The measurement gate should also be able to gather the normalization constant if needed.
 */
/*
Measurement Module Valid RollerCoaster
  io.in -> collapsePossibilities -> RNG -> Trigger Normalization register -> when false valid trigger collapsvalid register
  -> collapsePossibilities -> Calculate Normalization Number -> io.out
 */
//Top of the design
class Measurement(val num_of_qubits : Int, val bw : Int, val mult_pd : Int, val add_pd : Int, val L : Int) extends Module {
  val io = IO(new Bundle {
    val in_QSV        = Input(Vec(pow(2, num_of_qubits).toInt, UInt(bw.W)))
    val in_noise      = Input(UInt(32.W))
    val in_sendNorm   =  Input(Bool())
    val in_valid      =  Input(Bool())
    val out_valid     = Output(Bool())
    val out_QSV       = Output(Vec(pow(2, num_of_qubits).toInt, UInt(bw.W)))
    val out_Normalize = Output(UInt(bw.W))
    val out_measured  = Output(Bool())
  })
  //Reducing and normalization is found in CollapseProbability: probably a mistake due to weird valid outputs
  val findProbability = Module(new CollapseProbability(num_of_qubits, bw, mult_pd, add_pd, L))
  val numberGenerator = Module(new CompareWithRandom(bw / 2, mult_pd))
  val QSVout          = Module(new NewQSV(num_of_qubits, bw))
  val normalizeNumber = Module(new GetNormalization(bw / 2, add_pd, L))

  //Normalize output is 1 if sendNorm is 0
  val IEEENumber_One = bw match { //This is to prevent accidental 'normalization'
    case 32 => "h3C00".U(bw.W)
    case 64 => "h3F800000".U(bw.W)
    case 128 => "h3FF0000000000000".U(bw.W)
    case 256 => "h3FFF0000000000000000000000000000".U(bw.W)
  }

  //The valid nightmare for reusing the same Module for two different task in a sequence
  val normalize = RegInit(0.B) //Bool to keep track of when we are finding the normalization. Reset when input is invalid
  val collapsevalid = RegInit(0.B) //An invalid 'valid' signal is goes through the collapseProb. When valid goes from high to low, then 1.B
  when(numberGenerator.io.out_valid || io.in_sendNorm) { //Switches focus to the normalization
    normalize := 1.B
  }
  when((!findProbability.io.out_valid && ShiftRegister(findProbability.io.out_valid, 1)) || io.in_sendNorm){ //detect falling edge
    collapsevalid := 1.B
  }
  when(!io.in_valid) {
    normalize     := 0.B
    collapsevalid := 0.B
  }

  normalizeNumber.io.in_en := !(normalize && normalizeNumber.io.out_valid) //disable to hold value

  //Initial Input: Collapsing inputs into two outputs ... switches to the unnormalized QSV
  val collapseNumberSel = numberGenerator.io.out_valid & !io.in_sendNorm //Only take in other vector if measuring
  findProbability.io.in_QSV         := Mux(collapseNumberSel, QSVout.io.out_QSV, io.in_QSV)
  findProbability.io.in_valid       := Mux(collapseNumberSel, ShiftRegister(normalize, 1), io.in_valid)
    //The shift register fixes a problem with the output flipping between two different values: one is the true value and the other is just 1

  //Part one - Actually obtaining the measurement
  numberGenerator.io.in_probability := findProbability.io.out_Measured(0) //Compare against prob of 0
  numberGenerator.io.in_sel         := 0.B                                //Compare against prob of 0
  numberGenerator.io.in_valid       := findProbability.io.out_valid || normalize
  numberGenerator.io.in_en          := normalize                          //hold value until QSR updated
  numberGenerator.io.in_seed        := io.in_noise

  QSVout.io.in_QSV                  := io.in_QSV
  QSVout.io.in_sel                  := numberGenerator.io.out_value //From the RNG

  //Part two - obtaining the Normalization number
  normalizeNumber.io.in_valid         := collapsevalid && findProbability.io.out_valid//Only starts when told to and input is valid
  normalizeNumber.io.in_probability(0):= findProbability.io.out_Measured(0)
  normalizeNumber.io.in_probability(1):= findProbability.io.out_Measured(1)

  //Part three - output
  io.out_QSV                        := QSVout.io.out_QSV
  io.out_valid                      := normalizeNumber.io.out_valid
  io.out_Normalize                  := Mux(normalize, normalizeNumber.io.out_Normalize, IEEENumber_One)
  io.out_measured                   := numberGenerator.io.out_value
  //normalization will be the last thing to be finished from this gates actions.
}

