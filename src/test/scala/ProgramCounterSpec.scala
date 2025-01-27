import chisel3._
import chiseltest._
import org.scalatest._

import flatspec._
import matchers._

class ProgramCounterSpec extends AnyFlatSpec with ChiselScalatestTester with should.Matchers {
  "ProgramCounter" should "initialize to 0" in {
    test(new ProgramCounter()) { c =>
      c.io.pcPort.dataOut.expect(0.U)
    }
  }
  it should "walk 1 word" in {
    test(new ProgramCounter()) { c =>
      c.io.pcPort.countEnable.poke(true.B)
      c.io.pcPort.dataOut.peek().litValue() should be(0)
      c.clock.step()
      c.io.pcPort.dataOut.peek().litValue() should be(1)
    }
  }
  it should "jump to 0xbaddcafe (write)" in {
    test(new ProgramCounter()) { c =>
      c.io.pcPort.writeEnable.poke(true.B)
      c.io.pcPort.dataIn.poke("h_baddcafe".U)
      c.clock.step()
      c.io.pcPort.dataOut.peek().litValue() should be(BigInt("baddcafe", 16))
    }
  }
  it should "add 32 to PC" in {
    test(new ProgramCounter()) { c =>
      c.io.pcPort.writeEnable.poke(true.B)
      c.io.pcPort.writeAdd.poke(true.B)
      c.io.pcPort.dataIn.poke(32.U)
      c.clock.step()
      c.io.pcPort.dataOut.peek().litValue() should be(32)
    }
  }
}
