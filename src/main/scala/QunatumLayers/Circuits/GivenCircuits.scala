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

/*
Group0_0 & Group0_6
GeneratePiplinedGates(2, 16, Seq(Gate(H), Gate(SW))

Group0_1 & Group0_7
GeneratePiplinedGates(2, 16, Seq(Gate(H), Perm(2,1), Gate(X), Perm(2,1), Gate(SW))

Group0_2
GeneratePiplinedGates(2, 16, Seq(Gate(H), Perm(2,1), Gate(H), Perm(2,1), Gate(SW))

Group0_3
GeneratePiplinedGates(2, 16, Seq(Gate(H), Perm(2,1), Gate(X), Gate(H), Perm(2,1), Gate(SW))

Group0_4
GeneratePiplinedGates(2, 16, Seq(Gate(H), Perm(2,1), Gate(SX), Perm(2,1), Gate(SW))

Group0_5
GeneratePiplinedGates(2, 16, Seq(Gate(H), Perm(2,1), Gate(X), Gate(SX), Perm(2,1), Gate(SW))


Group1_0 & Group1_1 & Group1_6 & Group1_7
GeneratePiplinedGates(2, 16, Seq(Gate(H), Perm(2,1), Gate(SW), Perm(2,1))

Group1_2 & Group1_3
GeneratePiplinedGates(2, 16, Seq(Gate(H), Perm(2,1), Gate(SW), Perm(2,1), Gate(H))

Group1_4 & Group1_5
GeneratePiplinedGates(2, 16, Seq(Gate(H), Perm(2,1), Gate(SW), Perm(2,1), Gate(SX))
 */