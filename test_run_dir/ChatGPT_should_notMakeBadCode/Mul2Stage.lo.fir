FIRRTL version 1.2.0
circuit Mul2Stage :
  module Mul2Stage : @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 72:7]
    input clock : Clock @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 72:7]
    input reset : UInt<1> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 72:7]
    input io_a : UInt<16> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 75:14]
    input io_b : UInt<16> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 75:14]
    output io_y : UInt<32> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 75:14]

    node aLo = bits(io_a, 7, 0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 83:17]
    node aHi = bits(io_a, 15, 8) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 84:17]
    node bLo = bits(io_b, 7, 0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 85:17]
    node bHi = bits(io_b, 15, 8) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 86:17]
    node pLL = mul(aLo, bLo) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 89:17]
    node pLH = mul(aLo, bHi) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 90:17]
    node pHL = mul(aHi, bLo) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 91:17]
    node pHH = mul(aHi, bHi) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 92:17]
    reg rLL : UInt<16>, clock with :
      reset => (UInt<1>("h0"), rLL) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 95:20]
    reg rLH : UInt<16>, clock with :
      reset => (UInt<1>("h0"), rLH) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 96:20]
    reg rHL : UInt<16>, clock with :
      reset => (UInt<1>("h0"), rHL) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 97:20]
    reg rHH : UInt<16>, clock with :
      reset => (UInt<1>("h0"), rHH) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 98:20]
    node _prod_T = shl(rLH, 8) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 103:12]
    node _prod_T_1 = or(rLL, _prod_T) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 102:11]
    node _prod_T_2 = shl(rHL, 8) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 104:12]
    node _prod_T_3 = or(_prod_T_1, _prod_T_2) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 103:28]
    node _prod_T_4 = shl(rHH, 16) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 105:12]
    node prod = or(_prod_T_3, _prod_T_4) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 104:28]
    io_y <= prod @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 107:8]
    rLL <= pLL @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 95:20]
    rLH <= pLH @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 96:20]
    rHL <= pHL @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 97:20]
    rHH <= pHH @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 98:20]
