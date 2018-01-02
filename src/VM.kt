class VM(val input: List<String>, regs: List<Pair<String, Long>> = listOf()) {
  private val ram = input.map { ("$it .").split(" ").toMutableList() }
  private val regs = regs.associate { it.first to it.second }.toMutableMap()

  private var pc = 0
  val inputQueue = mutableListOf<Long>()
  val outQueue = mutableListOf<Long>()

  fun isReg(s: String): Boolean = regs.containsKey(s) or (s[0] in 'a'..'z')

  fun getValue(s: String): Long = when (s.toLongOrNull()) {
    null -> regs.getOrPut(s, { 0L })
    else -> s.toLong()
  }

  fun run(): Long {
    while (pc < ram.size) {
      val (inst, op1, op2) = ram[pc]
      when (inst) {
        "snd" -> {
          outQueue.add(getValue(op1))
          regs["SendCount"] = getValue("SendCount") + 1
        }
        "rcv" -> when {
          regs.containsKey("partOne") -> return outQueue.last()
          inputQueue.size == 0 -> return -1
          else -> regs[op1] = inputQueue.removeAt(0)
        }
        "set" -> if (isReg(op1)) regs[op1] = getValue(op2)
        "cpy" -> if (isReg(op2)) regs[op2] = getValue(op1)
        "add" -> if (isReg(op1)) regs[op1] = getValue(op1) + getValue(op2)
        "inc" -> if (isReg(op1)) regs[op1] = getValue(op1) + 1
        "sub" -> if (isReg(op1)) regs[op1] = getValue(op1) - getValue(op2)
        "dec" -> if (isReg(op1)) regs[op1] = getValue(op1) - 1
        "mul" -> if (isReg(op1)) {
          regs[op1] = getValue(op1) * getValue(op2)
          regs["MultCount"] = getValue("MultCount") + 1
        }
        "mod" -> if (isReg(op1)) regs[op1] = getValue(op1) % getValue(op2)

        "jnz" -> if (getValue(op1) != 0L) pc += getValue(op2).toInt() - 1
        "jgz" -> if (getValue(op1) > 0L) pc += getValue(op2).toInt() - 1

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
          if (getValue(op1) == getValue("lastOut")) {
            return -1
          } else if (getValue("periodLength") > 500)
            return 0
          regs["periodLength"] = getValue("periodLength") + 1
          regs["lastOut"] = getValue(op1)
        }
      }
      pc++
    }
    return 0
  }
}