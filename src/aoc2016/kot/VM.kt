package aoc2016.kot

class VM(val input: List<String>, regA: Long = 0, regB: Long = 0, regC: Long = 0, regD: Long = 0) {
  private val ram = input.map { (it + " .").split(" ").toTypedArray() }.toMutableList()
  private val regs = longArrayOf(regA, regB, regC, regD)
  private var pc = 0

  private fun isReg(s: String): Boolean = s[0] in 'a'..'d'

  private fun getValue(s: String): Long = when (isReg(s)) {
    true -> regs[s[0] - 'a']
    false -> s.toLong()
  }

  fun run(): Long {
    var periodLength = 0L
    var lastOut = 1L

    while (pc < ram.size) {
      val (inst, op1, op2) = ram[pc]
      when (inst) {
        "cpy" -> if (isReg(op2)) regs[op2[0] - 'a'] = getValue(op1)
        "inc" -> if (isReg(op1)) regs[op1[0] - 'a']++
        "dec" -> if (isReg(op1)) regs[op1[0] - 'a']--
        "add" -> if (isReg(op1)) regs[op1[0] - 'a'] += getValue(op2)
        "jnz" -> if (getValue(op1) != 0L) pc += getValue(op2).toInt() - 1
        "tgl" -> {
          val offset = pc + getValue(op1).toInt()
          if (offset < ram.size) ram[offset][0] = when (ram[offset][0]) {
            "inc" -> "dec"
            "tgl" -> "inc"
            "dec" -> "inc"
            "jnz" -> "cpy"
            "cpy" -> "jnz"
            else -> ram[offset][0]
          }
        }
        "out" -> {
          if (getValue(op1) == lastOut) {
            return -1
          } else if (periodLength > 500) return 1
          periodLength++
          lastOut = getValue(op1)
        }
      }
      pc++
    }
    return regs[0]
  }
}
