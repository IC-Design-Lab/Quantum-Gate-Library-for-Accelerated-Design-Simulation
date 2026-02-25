FIRRTL version 1.2.0
circuit TGate2QubitVector16bit :
  module Fixed16BitAdder : @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 22:7]
    input clock : Clock @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 22:7]
    input reset : UInt<1> @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 22:7]
    input io_in_0 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 24:14]
    input io_in_1 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 24:14]
    output io_out : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 24:14]

    reg reg : SInt<16>, clock with :
      reset => (UInt<1>("h0"), reg) @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 29:22]
    node _reg_T = sub(io_in_0, io_in_1) @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 30:25]
    node _reg_T_1 = tail(_reg_T, 1) @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 30:25]
    node _reg_T_2 = asSInt(_reg_T_1) @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 30:25]
    io_out <= reg @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 31:12]
    reg <= mux(reset, asSInt(UInt<16>("h0")), _reg_T_2) @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 29:{22,22} 30:12]

  module Fixed16BitAdder_1 : @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 7:7]
    input clock : Clock @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 7:7]
    input reset : UInt<1> @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 7:7]
    input io_in_0 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 9:14]
    input io_in_1 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 9:14]
    output io_out : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 9:14]

    reg reg : SInt<16>, clock with :
      reset => (UInt<1>("h0"), reg) @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 14:22]
    node _reg_T = add(io_in_0, io_in_1) @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 15:25]
    node _reg_T_1 = tail(_reg_T, 1) @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 15:25]
    node _reg_T_2 = asSInt(_reg_T_1) @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 15:25]
    io_out <= reg @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 16:12]
    reg <= mux(reset, asSInt(UInt<16>("h0")), _reg_T_2) @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 14:{22,22} 15:12]

  module Fixed16BitMultiplier : @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 37:7]
    input clock : Clock @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 37:7]
    input reset : UInt<1> @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 37:7]
    input io_in_0 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 39:14]
    input io_in_1 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 39:14]
    output io_out : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 39:14]

    node _wire0_T = pad(io_in_0, 28) @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 47:24]
    node _wire1_T = pad(io_in_1, 28) @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 48:24]
    node wire0 = pad(_wire0_T, 30) @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 43:21 47:9]
    node wire1 = pad(_wire1_T, 30) @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 44:21 48:9]
    node _wireout_T = mul(wire0, wire1) @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 49:20]
    reg outreg : SInt<16>, clock with :
      reset => (UInt<1>("h0"), outreg) @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 51:23]
    node wireout = asSInt(bits(_wireout_T, 29, 0)) @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 45:21 49:11]
    node _outreg_T = bits(wireout, 29, 14) @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 52:20]
    node _outreg_T_1 = asSInt(_outreg_T) @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 52:56]
    io_out <= outreg @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 53:10]
    outreg <= mux(reset, asSInt(UInt<16>("h0")), _outreg_T_1) @[\\src\\main\\scala\\FixedPointUnit\\FixedPointUnit.scala 51:{23,23} 52:10]

  module FixedComplexMultiplier : @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 46:7]
    input clock : Clock @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 46:7]
    input reset : UInt<1> @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 46:7]
    input io_in_a_0 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 47:14]
    input io_in_a_1 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 47:14]
    input io_in_b_0 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 47:14]
    input io_in_b_1 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 47:14]
    output io_out_0 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 47:14]
    output io_out_1 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 47:14]

    inst Subber of Fixed16BitAdder @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 52:27]
    inst Adder of Fixed16BitAdder_1 @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 53:27]
    inst Multiplier_0 of Fixed16BitMultiplier @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 54:39]
    inst Multiplier_1 of Fixed16BitMultiplier @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 54:39]
    inst Multiplier_2 of Fixed16BitMultiplier @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 54:39]
    inst Multiplier_3 of Fixed16BitMultiplier @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 54:39]
    io_out_0 <= Subber.io_out @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 79:13]
    io_out_1 <= Adder.io_out @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 80:13]
    Subber.clock <= clock
    Subber.reset <= reset
    Subber.io_in_0 <= Multiplier_0.io_out @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 65:19]
    Subber.io_in_1 <= Multiplier_1.io_out @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 66:19]
    Adder.clock <= clock
    Adder.reset <= reset
    Adder.io_in_0 <= Multiplier_2.io_out @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 75:18]
    Adder.io_in_1 <= Multiplier_3.io_out @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 76:18]
    Multiplier_0.clock <= clock
    Multiplier_0.reset <= reset
    Multiplier_0.io_in_0 <= io_in_a_0 @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 59:26]
    Multiplier_0.io_in_1 <= io_in_b_0 @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 60:26]
    Multiplier_1.clock <= clock
    Multiplier_1.reset <= reset
    Multiplier_1.io_in_0 <= io_in_a_1 @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 62:26]
    Multiplier_1.io_in_1 <= io_in_b_1 @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 63:26]
    Multiplier_2.clock <= clock
    Multiplier_2.reset <= reset
    Multiplier_2.io_in_0 <= io_in_a_0 @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 69:26]
    Multiplier_2.io_in_1 <= io_in_b_1 @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 70:26]
    Multiplier_3.clock <= clock
    Multiplier_3.reset <= reset
    Multiplier_3.io_in_0 <= io_in_a_1 @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 72:26]
    Multiplier_3.io_in_1 <= io_in_b_0 @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 73:26]

  module TGate : @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 55:7]
    input clock : Clock @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 55:7]
    input reset : UInt<1> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 55:7]
    input io_in_QSV_0_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 57:14]
    input io_in_QSV_0_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 57:14]
    input io_in_QSV_1_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 57:14]
    input io_in_QSV_1_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 57:14]
    input io_in_valid : UInt<1> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 57:14]
    output io_out_QSV_0_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 57:14]
    output io_out_QSV_0_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 57:14]
    output io_out_QSV_1_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 57:14]
    output io_out_QSV_1_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 57:14]
    output io_out_valid : UInt<1> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 57:14]

    inst multiplier of FixedComplexMultiplier @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 59:27]
    node sqrtOneHalf = asSInt(UInt<16>("h2d41")) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 67:66]
    reg regout_0 : SInt<16>, clock with :
      reset => (UInt<1>("h0"), regout_0) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 74:24]
    reg regout_1 : SInt<16>, clock with :
      reset => (UInt<1>("h0"), regout_1) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 74:24]
    reg delayed_r : UInt<1>, clock with :
      reset => (UInt<1>("h0"), delayed_r) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 80:30]
    node _GEN_0 = mux(UInt<1>("h1"), io_in_valid, delayed_r) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 80:{30,30,30}]
    reg delayed : UInt<1>, clock with :
      reset => (UInt<1>("h0"), delayed) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 80:30]
    node _GEN_1 = mux(UInt<1>("h1"), delayed_r, delayed) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 80:{30,30,30}]
    node _WIRE__0 = sqrtOneHalf @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 71:{32,32}]
    node _WIRE__1 = sqrtOneHalf @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 71:{32,32}]
    node _WIRE_1_0_0 = regout_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 76:{24,24}]
    node _WIRE_1_0_1 = regout_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 76:{24,24}]
    node _WIRE_1_1_0 = multiplier.io_out_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 76:{24,24}]
    node _WIRE_1_1_1 = multiplier.io_out_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 76:{24,24}]
    io_out_QSV_0_0 <= _WIRE_1_0_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 76:14]
    io_out_QSV_0_1 <= _WIRE_1_0_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 76:14]
    io_out_QSV_1_0 <= _WIRE_1_1_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 76:14]
    io_out_QSV_1_1 <= _WIRE_1_1_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 76:14]
    io_out_valid <= delayed @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 81:16]
    multiplier.clock <= clock
    multiplier.reset <= reset
    multiplier.io_in_a_0 <= io_in_QSV_1_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 70:22]
    multiplier.io_in_a_1 <= io_in_QSV_1_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 70:22]
    multiplier.io_in_b_0 <= _WIRE__0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 71:22]
    multiplier.io_in_b_1 <= _WIRE__1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 71:22]
    regout_0 <= mux(reset, io_in_QSV_0_0, io_in_QSV_0_0) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 74:{24,24} 75:15]
    regout_1 <= mux(reset, io_in_QSV_0_1, io_in_QSV_0_1) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 74:{24,24} 75:15]
    delayed_r <= _GEN_0
    delayed <= _GEN_1

  module ChooseGate : @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 17:7]
    input clock : Clock @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 17:7]
    input reset : UInt<1> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 17:7]
    input io_in_QSV_0_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 18:14]
    input io_in_QSV_0_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 18:14]
    input io_in_QSV_1_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 18:14]
    input io_in_QSV_1_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 18:14]
    input io_in_valid : UInt<1> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 18:14]
    output io_out_QSV_0_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 18:14]
    output io_out_QSV_0_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 18:14]
    output io_out_QSV_1_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 18:14]
    output io_out_QSV_1_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 18:14]
    output io_out_valid : UInt<1> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 18:14]

    inst gate of TGate @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 22:21]
    io_out_QSV_0_0 <= gate.io_out_QSV_0_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 32:21]
    io_out_QSV_0_1 <= gate.io_out_QSV_0_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 32:21]
    io_out_QSV_1_0 <= gate.io_out_QSV_1_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 32:21]
    io_out_QSV_1_1 <= gate.io_out_QSV_1_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 32:21]
    io_out_valid <= gate.io_out_valid @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 31:21]
    gate.clock <= clock
    gate.reset <= reset
    gate.io_in_QSV_0_0 <= io_in_QSV_0_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 29:21]
    gate.io_in_QSV_0_1 <= io_in_QSV_0_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 29:21]
    gate.io_in_QSV_1_0 <= io_in_QSV_1_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 29:21]
    gate.io_in_QSV_1_1 <= io_in_QSV_1_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 29:21]
    gate.io_in_valid <= io_in_valid @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 30:21]

  module TGate2QubitVector16bit : @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 35:7]
    input clock : Clock @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 35:7]
    input reset : UInt<1> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 35:7]
    input io_in_QSV_0_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    input io_in_QSV_0_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    input io_in_QSV_1_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    input io_in_QSV_1_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    input io_in_QSV_2_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    input io_in_QSV_2_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    input io_in_QSV_3_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    input io_in_QSV_3_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    input io_in_valid : UInt<1> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_0_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_0_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_1_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_1_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_2_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_2_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_3_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_3_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_valid : UInt<1> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]

    inst gate_0 of ChooseGate @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 41:53]
    inst gate_1 of ChooseGate @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 41:53]
    node _io_out_valid_T = andr(gate_0.io_out_valid) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 46:43]
    node _io_out_valid_T_1 = andr(gate_1.io_out_valid) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 46:43]
    io_out_QSV_0_0 <= gate_0.io_out_QSV_0_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_QSV_0_1 <= gate_0.io_out_QSV_0_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_QSV_1_0 <= gate_0.io_out_QSV_1_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_QSV_1_1 <= gate_0.io_out_QSV_1_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_QSV_2_0 <= gate_1.io_out_QSV_0_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_QSV_2_1 <= gate_1.io_out_QSV_0_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_QSV_3_0 <= gate_1.io_out_QSV_1_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_QSV_3_1 <= gate_1.io_out_QSV_1_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_valid <= _io_out_valid_T_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 46:19]
    gate_0.clock <= clock
    gate_0.reset <= reset
    gate_0.io_in_QSV_0_0 <= io_in_QSV_0_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 53:35]
    gate_0.io_in_QSV_0_1 <= io_in_QSV_0_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 53:35]
    gate_0.io_in_QSV_1_0 <= io_in_QSV_1_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 53:35]
    gate_0.io_in_QSV_1_1 <= io_in_QSV_1_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 53:35]
    gate_0.io_in_valid <= io_in_valid @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 45:25]
    gate_1.clock <= clock
    gate_1.reset <= reset
    gate_1.io_in_QSV_0_0 <= io_in_QSV_2_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 53:35]
    gate_1.io_in_QSV_0_1 <= io_in_QSV_2_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 53:35]
    gate_1.io_in_QSV_1_0 <= io_in_QSV_3_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 53:35]
    gate_1.io_in_QSV_1_1 <= io_in_QSV_3_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 53:35]
    gate_1.io_in_valid <= io_in_valid @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 45:25]
