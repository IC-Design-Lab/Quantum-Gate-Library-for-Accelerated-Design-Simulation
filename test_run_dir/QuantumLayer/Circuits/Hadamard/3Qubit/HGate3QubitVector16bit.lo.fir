FIRRTL version 1.2.0
circuit HGate3QubitVector16bit :
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

  module FixedComplexAdder : @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 7:7]
    input clock : Clock @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 7:7]
    input reset : UInt<1> @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 7:7]
    input io_in_a_0 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 8:14]
    input io_in_a_1 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 8:14]
    input io_in_b_0 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 8:14]
    input io_in_b_1 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 8:14]
    output io_out_0 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 8:14]
    output io_out_1 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 8:14]

    inst Adder_0 of Fixed16BitAdder_1 @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 13:33]
    inst Adder_1 of Fixed16BitAdder_1 @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 13:33]
    io_out_0 <= Adder_0.io_out @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 22:13]
    io_out_1 <= Adder_1.io_out @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 23:13]
    Adder_0.clock <= clock
    Adder_0.reset <= reset
    Adder_0.io_in_0 <= io_in_a_0 @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 16:21]
    Adder_0.io_in_1 <= io_in_b_0 @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 17:21]
    Adder_1.clock <= clock
    Adder_1.reset <= reset
    Adder_1.io_in_0 <= io_in_a_1 @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 19:21]
    Adder_1.io_in_1 <= io_in_b_1 @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 20:21]

  module FixedComplexSubber : @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 26:7]
    input clock : Clock @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 26:7]
    input reset : UInt<1> @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 26:7]
    input io_in_a_0 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 27:14]
    input io_in_a_1 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 27:14]
    input io_in_b_0 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 27:14]
    input io_in_b_1 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 27:14]
    output io_out_0 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 27:14]
    output io_out_1 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 27:14]

    inst Subber_0 of Fixed16BitAdder @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 32:34]
    inst Subber_1 of Fixed16BitAdder @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 32:34]
    io_out_0 <= Subber_0.io_out @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 41:13]
    io_out_1 <= Subber_1.io_out @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 42:13]
    Subber_0.clock <= clock
    Subber_0.reset <= reset
    Subber_0.io_in_0 <= io_in_a_0 @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 35:22]
    Subber_0.io_in_1 <= io_in_b_0 @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 36:22]
    Subber_1.clock <= clock
    Subber_1.reset <= reset
    Subber_1.io_in_0 <= io_in_a_1 @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 38:22]
    Subber_1.io_in_1 <= io_in_b_1 @[\\src\\main\\scala\\FixedPointUnit\\ComplexFixedPoint\\FixedComplexUnit.scala 39:22]

  module HadamardGate : @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 13:7]
    input clock : Clock @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 13:7]
    input reset : UInt<1> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 13:7]
    input io_in_QSV_0_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 15:14]
    input io_in_QSV_0_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 15:14]
    input io_in_QSV_1_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 15:14]
    input io_in_QSV_1_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 15:14]
    input io_in_valid : UInt<1> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 15:14]
    output io_out_QSV_0_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 15:14]
    output io_out_QSV_0_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 15:14]
    output io_out_QSV_1_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 15:14]
    output io_out_QSV_1_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 15:14]
    output io_out_valid : UInt<1> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 15:14]

    inst multiplier_0 of FixedComplexMultiplier @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 17:39]
    inst multiplier_1 of FixedComplexMultiplier @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 17:39]
    inst adder of FixedComplexAdder @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 18:27]
    inst subber of FixedComplexSubber @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 19:27]
    node sqrtOneHalf = asSInt(UInt<16>("h2d41")) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 27:66]
    node zero = asSInt(UInt<16>("h0")) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 28:48]
    reg delayed_r : UInt<1>, clock with :
      reset => (UInt<1>("h0"), delayed_r) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 48:30]
    node _GEN_0 = mux(UInt<1>("h1"), io_in_valid, delayed_r) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 48:{30,30,30}]
    reg delayed_r_1 : UInt<1>, clock with :
      reset => (UInt<1>("h0"), delayed_r_1) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 48:30]
    node _GEN_1 = mux(UInt<1>("h1"), delayed_r, delayed_r_1) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 48:{30,30,30}]
    reg delayed : UInt<1>, clock with :
      reset => (UInt<1>("h0"), delayed) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 48:30]
    node _GEN_2 = mux(UInt<1>("h1"), delayed_r_1, delayed) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 48:{30,30,30}]
    node _WIRE__0 = sqrtOneHalf @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 32:{35,35}]
    node _WIRE__1 = zero @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 32:{35,35}]
    node _WIRE_1_0 = sqrtOneHalf @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 34:{35,35}]
    node _WIRE_1_1 = zero @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 34:{35,35}]
    io_out_QSV_0_0 <= adder.io_out_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 39:17]
    io_out_QSV_0_1 <= adder.io_out_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 39:17]
    io_out_QSV_1_0 <= adder.io_out_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 44:17]
    io_out_QSV_1_1 <= adder.io_out_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 44:17]
    io_out_valid <= delayed @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 49:16]
    multiplier_0.clock <= clock
    multiplier_0.reset <= reset
    multiplier_0.io_in_a_0 <= io_in_QSV_0_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 31:25]
    multiplier_0.io_in_a_1 <= io_in_QSV_0_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 31:25]
    multiplier_0.io_in_b_0 <= _WIRE__0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 32:25]
    multiplier_0.io_in_b_1 <= _WIRE__1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 32:25]
    multiplier_1.clock <= clock
    multiplier_1.reset <= reset
    multiplier_1.io_in_a_0 <= io_in_QSV_1_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 33:25]
    multiplier_1.io_in_a_1 <= io_in_QSV_1_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 33:25]
    multiplier_1.io_in_b_0 <= _WIRE_1_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 34:25]
    multiplier_1.io_in_b_1 <= _WIRE_1_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 34:25]
    adder.clock <= clock
    adder.reset <= reset
    adder.io_in_a_0 <= multiplier_0.io_out_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 37:17]
    adder.io_in_a_1 <= multiplier_0.io_out_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 37:17]
    adder.io_in_b_0 <= multiplier_1.io_out_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 38:17]
    adder.io_in_b_1 <= multiplier_1.io_out_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 38:17]
    subber.clock <= clock
    subber.reset <= reset
    subber.io_in_a_0 <= multiplier_0.io_out_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 42:18]
    subber.io_in_a_1 <= multiplier_0.io_out_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 42:18]
    subber.io_in_b_0 <= multiplier_1.io_out_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 43:18]
    subber.io_in_b_1 <= multiplier_1.io_out_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SingleGate.scala 43:18]
    delayed_r <= _GEN_0
    delayed_r_1 <= _GEN_1
    delayed <= _GEN_2

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

    inst gate of HadamardGate @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 21:21]
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

  module HGate3QubitVector16bit : @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 35:7]
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
    input io_in_QSV_4_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    input io_in_QSV_4_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    input io_in_QSV_5_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    input io_in_QSV_5_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    input io_in_QSV_6_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    input io_in_QSV_6_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    input io_in_QSV_7_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    input io_in_QSV_7_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    input io_in_valid : UInt<1> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_0_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_0_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_1_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_1_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_2_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_2_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_3_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_3_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_4_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_4_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_5_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_5_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_6_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_6_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_7_0 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_QSV_7_1 : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]
    output io_out_valid : UInt<1> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 37:14]

    inst gate_0 of ChooseGate @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 41:53]
    inst gate_1 of ChooseGate @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 41:53]
    inst gate_2 of ChooseGate @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 41:53]
    inst gate_3 of ChooseGate @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 41:53]
    node _io_out_valid_T = andr(gate_0.io_out_valid) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 46:43]
    node _io_out_valid_T_1 = andr(gate_1.io_out_valid) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 46:43]
    node _io_out_valid_T_2 = andr(gate_2.io_out_valid) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 46:43]
    node _io_out_valid_T_3 = andr(gate_3.io_out_valid) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 46:43]
    io_out_QSV_0_0 <= gate_0.io_out_QSV_0_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_QSV_0_1 <= gate_0.io_out_QSV_0_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_QSV_1_0 <= gate_0.io_out_QSV_1_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_QSV_1_1 <= gate_0.io_out_QSV_1_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_QSV_2_0 <= gate_1.io_out_QSV_0_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_QSV_2_1 <= gate_1.io_out_QSV_0_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_QSV_3_0 <= gate_1.io_out_QSV_1_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_QSV_3_1 <= gate_1.io_out_QSV_1_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_QSV_4_0 <= gate_2.io_out_QSV_0_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_QSV_4_1 <= gate_2.io_out_QSV_0_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_QSV_5_0 <= gate_2.io_out_QSV_1_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_QSV_5_1 <= gate_2.io_out_QSV_1_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_QSV_6_0 <= gate_3.io_out_QSV_0_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_QSV_6_1 <= gate_3.io_out_QSV_0_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_QSV_7_0 <= gate_3.io_out_QSV_1_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_QSV_7_1 <= gate_3.io_out_QSV_1_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 54:55]
    io_out_valid <= _io_out_valid_T_3 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 46:19]
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
    gate_2.clock <= clock
    gate_2.reset <= reset
    gate_2.io_in_QSV_0_0 <= io_in_QSV_4_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 53:35]
    gate_2.io_in_QSV_0_1 <= io_in_QSV_4_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 53:35]
    gate_2.io_in_QSV_1_0 <= io_in_QSV_5_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 53:35]
    gate_2.io_in_QSV_1_1 <= io_in_QSV_5_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 53:35]
    gate_2.io_in_valid <= io_in_valid @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 45:25]
    gate_3.clock <= clock
    gate_3.reset <= reset
    gate_3.io_in_QSV_0_0 <= io_in_QSV_6_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 53:35]
    gate_3.io_in_QSV_0_1 <= io_in_QSV_6_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 53:35]
    gate_3.io_in_QSV_1_0 <= io_in_QSV_7_0 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 53:35]
    gate_3.io_in_QSV_1_1 <= io_in_QSV_7_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 53:35]
    gate_3.io_in_valid <= io_in_valid @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\SpanVector.scala 45:25]
