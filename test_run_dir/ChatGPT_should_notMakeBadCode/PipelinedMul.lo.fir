FIRRTL version 1.2.0
circuit PipelinedMul :
  module PipelinedMul : @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\GeneratePipline.scala 57:7]
    input clock : Clock @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\GeneratePipline.scala 57:7]
    input reset : UInt<1> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\GeneratePipline.scala 57:7]
    input io_a : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\GeneratePipline.scala 58:14]
    input io_b : SInt<16> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\GeneratePipline.scala 58:14]
    output io_y : SInt<32> @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\GeneratePipline.scala 58:14]

    node raw = mul(io_a, io_b) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\GeneratePipline.scala 65:18]
    reg piped_REG : SInt<32>, clock with :
      reset => (UInt<1>("h0"), piped_REG) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\GeneratePipline.scala 68:71]
    reg piped_REG_1 : SInt<32>, clock with :
      reset => (UInt<1>("h0"), piped_REG_1) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\GeneratePipline.scala 68:71]
    reg piped_REG_2 : SInt<32>, clock with :
      reset => (UInt<1>("h0"), piped_REG_2) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\GeneratePipline.scala 68:71]
    reg piped_REG_3 : SInt<32>, clock with :
      reset => (UInt<1>("h0"), piped_REG_3) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\GeneratePipline.scala 68:71]
    reg piped : SInt<32>, clock with :
      reset => (UInt<1>("h0"), piped) @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\GeneratePipline.scala 68:71]
    io_y <= piped @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\GeneratePipline.scala 70:8]
    piped_REG <= raw @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\GeneratePipline.scala 68:71]
    piped_REG_1 <= piped_REG @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\GeneratePipline.scala 68:71]
    piped_REG_2 <= piped_REG_1 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\GeneratePipline.scala 68:71]
    piped_REG_3 <= piped_REG_2 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\GeneratePipline.scala 68:71]
    piped <= piped_REG_3 @[\\src\\main\\scala\\QunatumLayers\\ArithmiticGates\\GeneratePipline.scala 68:71]
