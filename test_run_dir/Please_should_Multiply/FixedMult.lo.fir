FIRRTL version 1.2.0
circuit FixedMult :
  module FixedMult : @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 7:7]
    input clock : Clock @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 7:7]
    input reset : UInt<1> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 7:7]
    input io_in_multiplicant_0 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 9:14]
    input io_in_multiplicant_1 : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 9:14]
    input io_in_valid : UInt<1> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 9:14]
    output io_out_data : SInt<16> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 9:14]
    output io_out_valid : UInt<1> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 9:14]

    reg inputs_0 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), inputs_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 17:20]
    reg inputs_1 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), inputs_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 17:20]
    reg inputs_2 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), inputs_2) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 17:20]
    reg inputs_3 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), inputs_3) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 17:20]
    reg inputs_4 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), inputs_4) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 17:20]
    reg inputs_5 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), inputs_5) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 17:20]
    reg inputs_6 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), inputs_6) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 17:20]
    reg inputs_7 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), inputs_7) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 17:20]
    reg inputs_8 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), inputs_8) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 17:20]
    reg inputs_9 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), inputs_9) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 17:20]
    reg inputs_10 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), inputs_10) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 17:20]
    reg inputs_11 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), inputs_11) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 17:20]
    reg inputs_12 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), inputs_12) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 17:20]
    reg inputs_13 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), inputs_13) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 17:20]
    reg inputs_14 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), inputs_14) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 17:20]
    reg inputs_15 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), inputs_15) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 17:20]
    node _inputs_0_T = asUInt(io_in_multiplicant_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:40]
    node _inputs_0_T_1 = bits(io_in_multiplicant_1, 0, 0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:70]
    node _inputs_0_T_2 = and(_inputs_0_T, _inputs_0_T_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:47]
    node _inputs_1_T = asUInt(io_in_multiplicant_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:40]
    node _inputs_1_T_1 = bits(io_in_multiplicant_1, 1, 1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:70]
    node _inputs_1_T_2 = and(_inputs_1_T, _inputs_1_T_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:47]
    node _inputs_2_T = asUInt(io_in_multiplicant_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:40]
    node _inputs_2_T_1 = bits(io_in_multiplicant_1, 2, 2) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:70]
    node _inputs_2_T_2 = and(_inputs_2_T, _inputs_2_T_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:47]
    node _inputs_3_T = asUInt(io_in_multiplicant_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:40]
    node _inputs_3_T_1 = bits(io_in_multiplicant_1, 3, 3) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:70]
    node _inputs_3_T_2 = and(_inputs_3_T, _inputs_3_T_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:47]
    node _inputs_4_T = asUInt(io_in_multiplicant_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:40]
    node _inputs_4_T_1 = bits(io_in_multiplicant_1, 4, 4) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:70]
    node _inputs_4_T_2 = and(_inputs_4_T, _inputs_4_T_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:47]
    node _inputs_5_T = asUInt(io_in_multiplicant_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:40]
    node _inputs_5_T_1 = bits(io_in_multiplicant_1, 5, 5) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:70]
    node _inputs_5_T_2 = and(_inputs_5_T, _inputs_5_T_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:47]
    node _inputs_6_T = asUInt(io_in_multiplicant_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:40]
    node _inputs_6_T_1 = bits(io_in_multiplicant_1, 6, 6) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:70]
    node _inputs_6_T_2 = and(_inputs_6_T, _inputs_6_T_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:47]
    node _inputs_7_T = asUInt(io_in_multiplicant_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:40]
    node _inputs_7_T_1 = bits(io_in_multiplicant_1, 7, 7) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:70]
    node _inputs_7_T_2 = and(_inputs_7_T, _inputs_7_T_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:47]
    node _inputs_8_T = asUInt(io_in_multiplicant_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:40]
    node _inputs_8_T_1 = bits(io_in_multiplicant_1, 8, 8) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:70]
    node _inputs_8_T_2 = and(_inputs_8_T, _inputs_8_T_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:47]
    node _inputs_9_T = asUInt(io_in_multiplicant_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:40]
    node _inputs_9_T_1 = bits(io_in_multiplicant_1, 9, 9) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:70]
    node _inputs_9_T_2 = and(_inputs_9_T, _inputs_9_T_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:47]
    node _inputs_10_T = asUInt(io_in_multiplicant_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:40]
    node _inputs_10_T_1 = bits(io_in_multiplicant_1, 10, 10) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:70]
    node _inputs_10_T_2 = and(_inputs_10_T, _inputs_10_T_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:47]
    node _inputs_11_T = asUInt(io_in_multiplicant_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:40]
    node _inputs_11_T_1 = bits(io_in_multiplicant_1, 11, 11) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:70]
    node _inputs_11_T_2 = and(_inputs_11_T, _inputs_11_T_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:47]
    node _inputs_12_T = asUInt(io_in_multiplicant_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:40]
    node _inputs_12_T_1 = bits(io_in_multiplicant_1, 12, 12) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:70]
    node _inputs_12_T_2 = and(_inputs_12_T, _inputs_12_T_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:47]
    node _inputs_13_T = asUInt(io_in_multiplicant_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:40]
    node _inputs_13_T_1 = bits(io_in_multiplicant_1, 13, 13) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:70]
    node _inputs_13_T_2 = and(_inputs_13_T, _inputs_13_T_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:47]
    node _inputs_14_T = asUInt(io_in_multiplicant_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:40]
    node _inputs_14_T_1 = bits(io_in_multiplicant_1, 14, 14) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:70]
    node _inputs_14_T_2 = and(_inputs_14_T, _inputs_14_T_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:47]
    node _inputs_15_T = asUInt(io_in_multiplicant_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:40]
    node _inputs_15_T_1 = bits(io_in_multiplicant_1, 15, 15) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:70]
    node _inputs_15_T_2 = and(_inputs_15_T, _inputs_15_T_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:47]
    reg next__0 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), next__0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    reg next__1 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), next__1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    reg next__2 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), next__2) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    reg next__3 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), next__3) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    reg next__4 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), next__4) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    reg next__5 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), next__5) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    reg next__6 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), next__6) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    reg next__7 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), next__7) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    node _shift_T = bits(inputs_1, 14, 0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 37:37]
    node _shift_T_1 = cat(_shift_T, UInt<1>("h0")) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 37:55]
    node shift = _shift_T_1 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 36:27 37:13]
    node _next_0_T = add(inputs_0, shift) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 38:49]
    node _next_0_T_1 = tail(_next_0_T, 1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 38:49]
    node _shift_T_2 = bits(inputs_3, 14, 0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 37:37]
    node _shift_T_3 = cat(_shift_T_2, UInt<1>("h0")) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 37:55]
    node shift_1 = _shift_T_3 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 36:27 37:13]
    node _next_1_T = add(inputs_2, shift_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 38:49]
    node _next_1_T_1 = tail(_next_1_T, 1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 38:49]
    node _shift_T_4 = bits(inputs_5, 14, 0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 37:37]
    node _shift_T_5 = cat(_shift_T_4, UInt<1>("h0")) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 37:55]
    node shift_2 = _shift_T_5 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 36:27 37:13]
    node _next_2_T = add(inputs_4, shift_2) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 38:49]
    node _next_2_T_1 = tail(_next_2_T, 1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 38:49]
    node _shift_T_6 = bits(inputs_7, 14, 0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 37:37]
    node _shift_T_7 = cat(_shift_T_6, UInt<1>("h0")) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 37:55]
    node shift_3 = _shift_T_7 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 36:27 37:13]
    node _next_3_T = add(inputs_6, shift_3) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 38:49]
    node _next_3_T_1 = tail(_next_3_T, 1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 38:49]
    reg next_1_0 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), next_1_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    reg next_1_1 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), next_1_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    reg next_1_2 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), next_1_2) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    reg next_1_3 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), next_1_3) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    node _shift_T_8 = bits(next__1, 14, 0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 37:37]
    node _shift_T_9 = cat(_shift_T_8, UInt<1>("h0")) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 37:55]
    node shift_4 = _shift_T_9 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 36:27 37:13]
    node _next_0_T_2 = add(next__0, shift_4) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 38:49]
    node _next_0_T_3 = tail(_next_0_T_2, 1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 38:49]
    node _shift_T_10 = bits(next__3, 14, 0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 37:37]
    node _shift_T_11 = cat(_shift_T_10, UInt<1>("h0")) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 37:55]
    node shift_5 = _shift_T_11 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 36:27 37:13]
    node _next_1_T_2 = add(next__2, shift_5) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 38:49]
    node _next_1_T_3 = tail(_next_1_T_2, 1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 38:49]
    reg next_2_0 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), next_2_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    reg next_2_1 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), next_2_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    node _shift_T_12 = bits(next_1_1, 14, 0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 37:37]
    node _shift_T_13 = cat(_shift_T_12, UInt<1>("h0")) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 37:55]
    node shift_6 = _shift_T_13 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 36:27 37:13]
    node _next_0_T_4 = add(next_1_0, shift_6) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 38:49]
    node _next_0_T_5 = tail(_next_0_T_4, 1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 38:49]
    reg next_3_0 : UInt<16>, clock with :
      reset => (UInt<1>("h0"), next_3_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    reg productReg : SInt<16>, clock with :
      reset => (UInt<1>("h0"), productReg) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 55:27]
    node product = UInt<32>("h0") @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 29:{25,25}]
    node _productReg_T = bits(product, 29, 14) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 56:24]
    node _productReg_T_1 = asSInt(_productReg_T) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 56:60]
    reg io_out_valid_r : UInt<1>, clock with :
      reset => (UInt<1>("h0"), io_out_valid_r) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 66:33]
    node _GEN_0 = mux(UInt<1>("h1"), io_in_valid, io_out_valid_r) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 66:{33,33,33}]
    reg io_out_valid_r_1 : UInt<1>, clock with :
      reset => (UInt<1>("h0"), io_out_valid_r_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 66:33]
    node _GEN_1 = mux(UInt<1>("h1"), io_out_valid_r, io_out_valid_r_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 66:{33,33,33}]
    reg io_out_valid_r_2 : UInt<1>, clock with :
      reset => (UInt<1>("h0"), io_out_valid_r_2) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 66:33]
    node _GEN_2 = mux(UInt<1>("h1"), io_out_valid_r_1, io_out_valid_r_2) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 66:{33,33,33}]
    reg io_out_valid_r_3 : UInt<1>, clock with :
      reset => (UInt<1>("h0"), io_out_valid_r_3) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 66:33]
    node _GEN_3 = mux(UInt<1>("h1"), io_out_valid_r_2, io_out_valid_r_3) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 66:{33,33,33}]
    reg io_out_valid_r_4 : UInt<1>, clock with :
      reset => (UInt<1>("h0"), io_out_valid_r_4) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 66:33]
    node _GEN_4 = mux(UInt<1>("h1"), io_out_valid_r_3, io_out_valid_r_4) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 66:{33,33,33}]
    io_out_data <= productReg @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 57:15]
    io_out_valid <= io_out_valid_r_4 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 66:17]
    inputs_0 <= _inputs_0_T_2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:15]
    inputs_1 <= _inputs_1_T_2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:15]
    inputs_2 <= _inputs_2_T_2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:15]
    inputs_3 <= _inputs_3_T_2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:15]
    inputs_4 <= _inputs_4_T_2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:15]
    inputs_5 <= _inputs_5_T_2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:15]
    inputs_6 <= _inputs_6_T_2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:15]
    inputs_7 <= _inputs_7_T_2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:15]
    inputs_8 <= _inputs_8_T_2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:15]
    inputs_9 <= _inputs_9_T_2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:15]
    inputs_10 <= _inputs_10_T_2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:15]
    inputs_11 <= _inputs_11_T_2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:15]
    inputs_12 <= _inputs_12_T_2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:15]
    inputs_13 <= _inputs_13_T_2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:15]
    inputs_14 <= _inputs_14_T_2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:15]
    inputs_15 <= _inputs_15_T_2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 19:15]
    next__0 <= _next_0_T_1 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 38:22]
    next__1 <= _next_1_T_1 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 38:22]
    next__2 <= _next_2_T_1 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 38:22]
    next__3 <= _next_3_T_1 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 38:22]
    next__4 <= next__4 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    next__5 <= next__5 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    next__6 <= next__6 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    next__7 <= next__7 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    next_1_0 <= _next_0_T_3 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 38:22]
    next_1_1 <= _next_1_T_3 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 38:22]
    next_1_2 <= next_1_2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    next_1_3 <= next_1_3 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    next_2_0 <= _next_0_T_5 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 38:22]
    next_2_1 <= next_2_1 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    next_3_0 <= next_3_0 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 32:19]
    productReg <= mux(reset, asSInt(UInt<16>("h0")), _productReg_T_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 55:{27,27} 56:14]
    io_out_valid_r <= _GEN_0
    io_out_valid_r_1 <= _GEN_1
    io_out_valid_r_2 <= _GEN_2
    io_out_valid_r_3 <= _GEN_3
    io_out_valid_r_4 <= _GEN_4
