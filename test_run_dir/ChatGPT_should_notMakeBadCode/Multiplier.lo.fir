FIRRTL version 1.2.0
circuit Multiplier :
  module Multiplier : @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 94:7]
    input clock : Clock @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 94:7]
    input reset : UInt<1> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 94:7]
    input io_in_0 : UInt<32> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 98:14]
    input io_in_1 : UInt<32> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 98:14]
    input io_in_valid : UInt<1> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 98:14]
    output io_out : UInt<64> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 98:14]
    output io_out_valid : UInt<1> @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 98:14]

    node aChunks_0 = bits(io_in_0, 7, 0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 109:13]
    node aChunks_1 = bits(io_in_0, 15, 8) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 109:13]
    node aChunks_2 = bits(io_in_0, 23, 16) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 109:13]
    node aChunks_3 = bits(io_in_0, 31, 24) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 109:13]
    node _p0_T = mul(aChunks_0, io_in_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 116:24]
    node p0 = shl(_p0_T, 0) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 116:36]
    reg partialSums_0_REG : UInt<40>, clock with :
      reset => (UInt<1>("h0"), partialSums_0_REG) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 117:28]
    node _pi_T = mul(aChunks_1, io_in_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 121:26]
    node pi = shl(_pi_T, 8) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 121:38]
    node partialSums_0 = pad(partialSums_0_REG, 64) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 113:25 117:18]
    node _acc_T = add(partialSums_0, pi) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 122:32]
    node acc = tail(_acc_T, 1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 122:32]
    reg partialSums_1_REG : UInt<64>, clock with :
      reset => (UInt<1>("h0"), partialSums_1_REG) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 123:30]
    node _pi_T_1 = mul(aChunks_2, io_in_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 121:26]
    node pi_1 = shl(_pi_T_1, 16) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 121:38]
    node partialSums_1 = partialSums_1_REG @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 113:25 123:20]
    node _acc_T_1 = add(partialSums_1, pi_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 122:32]
    node acc_1 = tail(_acc_T_1, 1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 122:32]
    reg partialSums_2_REG : UInt<64>, clock with :
      reset => (UInt<1>("h0"), partialSums_2_REG) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 123:30]
    node _pi_T_2 = mul(aChunks_3, io_in_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 121:26]
    node pi_2 = shl(_pi_T_2, 24) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 121:38]
    node partialSums_2 = partialSums_2_REG @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 113:25 123:20]
    node _acc_T_2 = add(partialSums_2, pi_2) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 122:32]
    node acc_2 = tail(_acc_T_2, 1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 122:32]
    reg partialSums_3_REG : UInt<64>, clock with :
      reset => (UInt<1>("h0"), partialSums_3_REG) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 123:30]
    reg io_out_valid_r : UInt<1>, clock with :
      reset => (UInt<1>("h0"), io_out_valid_r) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 128:32]
    node _GEN_0 = mux(UInt<1>("h1"), io_in_valid, io_out_valid_r) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 128:{32,32,32}]
    reg io_out_valid_r_1 : UInt<1>, clock with :
      reset => (UInt<1>("h0"), io_out_valid_r_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 128:32]
    node _GEN_1 = mux(UInt<1>("h1"), io_out_valid_r, io_out_valid_r_1) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 128:{32,32,32}]
    reg io_out_valid_r_2 : UInt<1>, clock with :
      reset => (UInt<1>("h0"), io_out_valid_r_2) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 128:32]
    node _GEN_2 = mux(UInt<1>("h1"), io_out_valid_r_1, io_out_valid_r_2) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 128:{32,32,32}]
    reg io_out_valid_r_3 : UInt<1>, clock with :
      reset => (UInt<1>("h0"), io_out_valid_r_3) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 128:32]
    node _GEN_3 = mux(UInt<1>("h1"), io_out_valid_r_2, io_out_valid_r_3) @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 128:{32,32,32}]
    node partialSums_3 = partialSums_3_REG @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 113:25 123:20]
    io_out <= partialSums_3 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 126:10]
    io_out_valid <= io_out_valid_r_3 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 128:16]
    partialSums_0_REG <= p0 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 117:28]
    partialSums_1_REG <= acc @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 123:30]
    partialSums_2_REG <= acc_1 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 123:30]
    partialSums_3_REG <= acc_2 @[\\src\\main\\scala\\FixedPointUnit\\AdvancedFixedPoint.scala 123:30]
    io_out_valid_r <= _GEN_0
    io_out_valid_r_1 <= _GEN_1
    io_out_valid_r_2 <= _GEN_2
    io_out_valid_r_3 <= _GEN_3
