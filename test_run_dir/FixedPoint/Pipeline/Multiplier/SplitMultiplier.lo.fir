FIRRTL version 1.2.0
circuit SplitMultiplier :
  module SplitMultiplier : @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 97:7]
    input clock : Clock @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 97:7]
    input reset : UInt<1> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 97:7]
    input io_a : UInt<16> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 100:14]
    input io_b : UInt<16> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 100:14]
    output io_out : UInt<32> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 100:14]
    output io_out_fixed : UInt<16> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 100:14]
    input io_valid_in : UInt<1> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 100:14]
    output io_valid_out : UInt<1> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 100:14]

    node bChunk = bits(io_b, 3, 0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 119:22]
    node _initialPartials_0_T = mul(io_a, bChunk) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 120:34]
    node _initialPartials_0_T_1 = shl(_initialPartials_0_T, 0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 120:44]
    node bChunk_1 = bits(io_b, 7, 4) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 119:22]
    node _initialPartials_1_T = mul(io_a, bChunk_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 120:34]
    node _initialPartials_1_T_1 = shl(_initialPartials_1_T, 4) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 120:44]
    node bChunk_2 = bits(io_b, 11, 8) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 119:22]
    node _initialPartials_2_T = mul(io_a, bChunk_2) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 120:34]
    node _initialPartials_2_T_1 = shl(_initialPartials_2_T, 8) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 120:44]
    node bChunk_3 = bits(io_b, 15, 12) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 119:22]
    node _initialPartials_3_T = mul(io_a, bChunk_3) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 120:34]
    node _initialPartials_3_T_1 = shl(_initialPartials_3_T, 12) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 120:44]
    reg partials_0 : UInt<32>, clock with :
      reset => (UInt<1>("h0"), partials_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 126:25]
    reg partials_1 : UInt<32>, clock with :
      reset => (UInt<1>("h0"), partials_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 126:25]
    reg partials_2 : UInt<32>, clock with :
      reset => (UInt<1>("h0"), partials_2) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 126:25]
    reg partials_3 : UInt<32>, clock with :
      reset => (UInt<1>("h0"), partials_3) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 126:25]
    reg acc : UInt<32>, clock with :
      reset => (UInt<1>("h0"), acc) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 127:25]
    reg valid : UInt<1>, clock with :
      reset => (UInt<1>("h0"), valid) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 128:25]
    reg REG__0 : UInt<32>, clock with :
      reset => (UInt<1>("h0"), REG__0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 144:23]
    reg REG__1 : UInt<32>, clock with :
      reset => (UInt<1>("h0"), REG__1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 144:23]
    reg REG__2 : UInt<32>, clock with :
      reset => (UInt<1>("h0"), REG__2) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 144:23]
    node _T = add(acc, REG__0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 147:23]
    node _T_1 = tail(_T, 1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 147:23]
    reg REG_1 : UInt<32>, clock with :
      reset => (UInt<1>("h0"), REG_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 147:18]
    reg REG_2 : UInt<1>, clock with :
      reset => (UInt<1>("h0"), REG_2) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 150:20]
    reg REG_3_0 : UInt<32>, clock with :
      reset => (UInt<1>("h0"), REG_3_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 144:23]
    reg REG_3_1 : UInt<32>, clock with :
      reset => (UInt<1>("h0"), REG_3_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 144:23]
    node _T_2 = add(REG_1, REG_3_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 147:23]
    node _T_3 = tail(_T_2, 1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 147:23]
    reg REG_4 : UInt<32>, clock with :
      reset => (UInt<1>("h0"), REG_4) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 147:18]
    reg REG_5 : UInt<1>, clock with :
      reset => (UInt<1>("h0"), REG_5) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 150:20]
    reg REG_6_0 : UInt<32>, clock with :
      reset => (UInt<1>("h0"), REG_6_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 144:23]
    node _T_4 = add(REG_4, REG_6_0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 147:23]
    node _T_5 = tail(_T_4, 1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 147:23]
    reg REG_7 : UInt<32>, clock with :
      reset => (UInt<1>("h0"), REG_7) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 147:18]
    reg REG_8 : UInt<1>, clock with :
      reset => (UInt<1>("h0"), REG_8) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 150:20]
    node _io_out_fixed_T = bits(REG_7, 29, 14) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 157:22]
    node initialPartials_0 = pad(_initialPartials_0_T_1, 32) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 114:29 120:24]
    node initialPartials_1 = pad(_initialPartials_1_T_1, 32) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 114:29 120:24]
    node initialPartials_2 = pad(_initialPartials_2_T_1, 32) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 114:29 120:24]
    node initialPartials_3 = _initialPartials_3_T_1 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 114:29 120:24]
    node nextPartials__0 = partials_1 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 136:28 140:23]
    node nextPartials__1 = partials_2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 136:28 140:23]
    node nextPartials__2 = partials_3 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 136:28 140:23]
    node nextPartials_1_0 = REG__1 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 136:28 140:23]
    node nextPartials_1_1 = REG__2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 136:28 140:23]
    node nextPartials_2_0 = REG_3_1 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 136:28 140:23]
    io_out <= REG_7 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 156:10]
    io_out_fixed <= _io_out_fixed_T @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 157:16]
    io_valid_out <= REG_8 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 158:16]
    partials_0 <= initialPartials_0 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 126:25]
    partials_1 <= initialPartials_1 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 126:25]
    partials_2 <= initialPartials_2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 126:25]
    partials_3 <= initialPartials_3 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 126:25]
    acc <= partials_0 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 127:25]
    valid <= io_valid_in @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 128:25]
    REG__0 <= nextPartials__0 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 144:23]
    REG__1 <= nextPartials__1 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 144:23]
    REG__2 <= nextPartials__2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 144:23]
    REG_1 <= _T_1 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 147:18]
    REG_2 <= valid @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 150:20]
    REG_3_0 <= nextPartials_1_0 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 144:23]
    REG_3_1 <= nextPartials_1_1 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 144:23]
    REG_4 <= _T_3 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 147:18]
    REG_5 <= REG_2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 150:20]
    REG_6_0 <= nextPartials_2_0 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 144:23]
    REG_7 <= _T_5 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 147:18]
    REG_8 <= REG_5 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 150:20]
