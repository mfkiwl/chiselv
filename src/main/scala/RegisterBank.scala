import chisel3._
import chisel3.util._

class RegisterBank(numRegs: Int = 32, regWidth: Int = 32) extends Module {
  val io = IO(new Bundle {
    val dataIn      = Input(UInt(regWidth.W))
    val dataOut     = Output(UInt(regWidth.W))
    val address     = Input(UInt(log2Ceil(regWidth + 1).W))
    val writeEnable = Input(Bool())
  })

  val regs = Reg(Vec(numRegs, UInt(regWidth.W)))
  regs(0) := 0.U // Register x0 is always 0

  io.dataOut := regs(io.address)
  when(io.writeEnable && io.address =/= 0.U) {
    regs(io.address) := io.dataIn
  }
}