package FPU_Test

import FixedPointUnit._
import FixedPointUnit.ComplexFixedPoint._
import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class TestFixedPointAdder extends AnyFlatSpec with ChiselScalatestTester {
  "FixedPointAdder" should "Add" in
    test(new FixedAdder(16, false)) { dut =>
      dut.io.in(0).poke(32.S)
      dut.io.in(1).poke(-32.S)

      dut.clock.step()

      val Output = dut.io.out.peek().litValue
      println(s"The output is: ${Output}")
    }
}

class TestFixedPointMultiplier extends AnyFlatSpec with ChiselScalatestTester {
  "FixedPointAdder" should "Add" in
    test(new FixedMultiplier(16,14)) { dut =>
      dut.io.in(0).poke(BigInt("4000",16).S(16.W)) // 1
      dut.io.in(1).poke(BigInt("4000",16).S(16.W)) // 1
      dut.clock.step()
      var Output = dut.io.out.peek().litValue
      println(s"The output ${Output.toString(16)}") // = 1 = h 4000

      dut.clock.step()

      dut.io.in(0).poke(BigInt("2000",16).S(16.W)) // 1/2
      dut.io.in(1).poke(BigInt("2000",16).S(16.W)) // 1/2
      dut.clock.step()
      Output = dut.io.out.peek().litValue
      println(s"The output ${Output.toString(16)}") // = 1/4 = h 1000
    }
}

class TestFixedPointDivider extends AnyFlatSpec with ChiselScalatestTester {
  "FixedPointDivider" should "Divide" in
    test(new FixedDivision(16, 14)) { dut =>
      dut.io.in_num.poke(BigInt("1000",16).S(16.W)) // 1/4
      dut.io.in_den.poke(BigInt("2000",16).S(16.W)) // 1/2
      dut.clock.step()
      var Output = dut.io.out.peek().litValue
      println(s"The output ${Output.toString(16)}") // = 1/2 = h 2000

      dut.clock.step()

      dut.io.in_num.poke(BigInt("2000",16).S(16.W)) // 1/2
      dut.io.in_den.poke(BigInt("2000",16).S(16.W)) // 1/2
      dut.clock.step()
      Output = dut.io.out.peek().litValue
      println(s"The output ${Output.toString(16)}") // = 1 = h 4000
    }
}

class TestFixedComplexAdd extends AnyFlatSpec with ChiselScalatestTester {
    "FixedComplexAdd" should "Add" in
      test(new FixedComplexAdder(16)) { dut =>
        // 0.25 + 0.25j
        dut.io.in_a(0).poke(BigInt("1000", 16).S(16.W)) // 1/4
        dut.io.in_a(1).poke(BigInt("1000", 16).S(16.W)) // 1/4
        // 0.75 + 0.50j
        dut.io.in_b(0).poke(BigInt("3000", 16).S(16.W)) // 3/4
        dut.io.in_b(1).poke(BigInt("2000", 16).S(16.W)) // 2/4
        //output
        dut.clock.step()
        var OutReal = dut.io.out(0).peek().litValue
        var OutImag = dut.io.out(1).peek().litValue
        println(s"The output ${OutReal.toString(16)} + ${OutImag.toString(16)} j") // = 1 + 3/4j = 4000 3000
      }
}

class TestFixedComplexMult extends AnyFlatSpec with ChiselScalatestTester {
    "FixedComplexAdd" should "Add" in
      test(new FixedComplexMultiplier(16,14)) { dut =>
          // 0.25 + 0.25j
          dut.io.in_a(0).poke(BigInt("1000", 16).S(16.W)) // 1/4
          dut.io.in_a(1).poke(BigInt("1000", 16).S(16.W)) // 1/4
          // 0.75 + 0.50j
          dut.io.in_b(0).poke(BigInt("3000", 16).S(16.W)) // 3/4
          dut.io.in_b(1).poke(BigInt("2000", 16).S(16.W)) // 2/4
          //output
          dut.clock.step()
          var OutReal = dut.io.out(0).peek().litValue
          var OutImag = dut.io.out(1).peek().litValue
          println(s"The output ${OutReal.toString(16)} + ${OutImag.toString(16)} j") // = 1/16 + 5/16j = 0400 1400
      }
}

class TestSimpleSqrt extends AnyFlatSpec with ChiselScalatestTester {
  "SqrtRoot" should "SqrtRoot" in
    test(new SimpleFixedSquareRoot(16,14)) { dut =>
      dut.io.in.poke(0x2000.S(16.W))
      dut.clock.step()
      dut.io.in_valid.poke(1.B)

      //check clock cycles until valid
      println(s"The input: Root: ${dut.io.in.peek().litValue.toString(16)} & Valid: ${dut.io.in_valid.peek().litValue}")
      for(i <- 0 until 21){
        dut.clock.step()
        println(s"The output: Root: ${dut.io.out.peek().litValue.toString(16)} & Valid: ${dut.io.out_valid.peek().litValue}")
      }
    }
}