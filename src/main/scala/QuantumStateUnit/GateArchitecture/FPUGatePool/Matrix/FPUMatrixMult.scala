package FPUMatrixMult

import Complex_FPU.{complex_adder, complex_conjugate_mult}
import QuantumStateUnit.GateArchitecture.FPUGatePool.Matrix.AndGate
import chisel3._

import scala.math.pow

class FPUMatrixMult(val num_of_qubits : Int, val bw :Int, val mult_pd : Int, val add_pd : Int) extends Module{
  require(bw == 32 || bw == 64 || bw == 128 || bw == 256)
  override def desiredName = s"FPUMatrixMult_Square${pow(2,num_of_qubits).toInt}Ket${bw/2}Bitwidth"
  val io = IO(new Bundle{
    val in_QSV        =  Input(Vec(pow(2,num_of_qubits).toInt, UInt(bw.W)))
    val in_Ugate      =  Input(Vec(4, UInt(bw.W)))
    val in_valid      =  Input(Bool())
    val out_valid     = Output(Bool())
    val out_QSV       = Output(Vec(pow(2,num_of_qubits).toInt, UInt(bw.W)))
  })

//complexA will be the input numbers while complexB will be the gate
val FPUMultiplier = Seq.fill(pow(2,num_of_qubits+1).toInt)(Module(new complex_conjugate_mult(bw,mult_pd,add_pd)))
val FPUAdder      = Seq.fill(pow(2,num_of_qubits).toInt)(Module(new complex_adder(bw, add_pd)))
val FPUvalid      = Module(new AndGate(num_of_qubits))

for(i<-0 until pow(2,num_of_qubits-1).toInt){
  //Number inputs for each multiplication
  FPUMultiplier(4*i  ).io.complexA := io.in_QSV(2*i  )
  FPUMultiplier(4*i  ).io.complexB := io.in_Ugate(0)
  FPUMultiplier(4*i+1).io.complexA := io.in_QSV(2*i+1)
  FPUMultiplier(4*i+1).io.complexB := io.in_Ugate(1)
  FPUMultiplier(4*i+2).io.complexA := io.in_QSV(2*i  )
  FPUMultiplier(4*i+2).io.complexB := io.in_Ugate(2)
  FPUMultiplier(4*i+3).io.complexA := io.in_QSV(2*i+1)
  FPUMultiplier(4*i+3).io.complexB := io.in_Ugate(3)
  //Bool inputs
  FPUMultiplier(4*i  ).io.in_en    := 1.B
  FPUMultiplier(4*i  ).io.in_valid := io.in_valid
  FPUMultiplier(4*i+1).io.in_en    := 1.B
  FPUMultiplier(4*i+1).io.in_valid := io.in_valid
  FPUMultiplier(4*i+2).io.in_en    := 1.B
  FPUMultiplier(4*i+2).io.in_valid := io.in_valid
  FPUMultiplier(4*i+3).io.in_en    := 1.B
  FPUMultiplier(4*i+3).io.in_valid := io.in_valid
  //Attach Multiplier to the Adder
  FPUAdder(2*i  ).io.complexA      := FPUMultiplier(4*i  ).io.out_s
  FPUAdder(2*i  ).io.complexB      := FPUMultiplier(4*i+1).io.out_s
  FPUAdder(2*i+1).io.complexA      := FPUMultiplier(4*i+2).io.out_s
  FPUAdder(2*i+1).io.complexB      := FPUMultiplier(4*i+3).io.out_s
  //Multiplier Bool out into Adder Bool in
  FPUAdder(2*i  ).io.in_en         := 1.B
  FPUAdder(2*i+1).io.in_en         := 1.B
  FPUAdder(2*i  ).io.in_valid      := FPUMultiplier(4*i  ).io.out_valid && FPUMultiplier(4*i+1).io.out_valid
  FPUAdder(2*i+1).io.in_valid      := FPUMultiplier(4*i+2).io.out_valid && FPUMultiplier(4*i+3).io.out_valid
  //Output
  io.out_QSV(2*i  )                := FPUAdder(2*i  ).io.out_s
  io.out_QSV(2*i+1)                := FPUAdder(2*i+1).io.out_s
  //and gate for valid
  FPUvalid.io.in_valid(2*i  )      := FPUAdder(2*i  ).io.out_valid
  FPUvalid.io.in_valid(2*i+1)      := FPUAdder(2*i+1).io.out_valid
}
//out is valid
io.out_valid := FPUvalid.io.out_valid
}
