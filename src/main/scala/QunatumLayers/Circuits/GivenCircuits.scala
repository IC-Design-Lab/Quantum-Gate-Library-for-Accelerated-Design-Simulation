package QunatumLayers.Circuits

import chisel3._
import QunatumLayers.ArithmiticGates.Pipline._

//GeneratePiplinedGates(val num_of_qubits : Int, val bitwidth : Int, val Circuit : Seq[GateVsPerm])
/*
case object H   extends SpecifiedGate {def size = 1} //Hadamard gate
case object T   extends SpecifiedGate {def size = 1} //e^(pi/8) or T gate or phase gate
case object X   extends SpecifiedGate {def size = 1} //Not gate
case object Y   extends SpecifiedGate {def size = 1} //Y gate
case object Z   extends SpecifiedGate {def size = 1} //Z gate
case object SX  extends SpecifiedGate {def size = 1} //sqrt x gate or v gate
case object SXD extends SpecifiedGate {def size = 1} //sqrt(x) dagger gate or v dagger gate
case object CN  extends SpecifiedGate {def size = 2} //Controlled Not/X Gate
case object SW  extends SpecifiedGate {def size = 2} //Swap Gate
 */

//GeneratePiplinedGates(2, 16, (Gate(H), Gate(SW))