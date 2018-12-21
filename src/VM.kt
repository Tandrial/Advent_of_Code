class VM(val input: List<String>, regs: List<Pair<String, Long>> = listOf()) {
  private val ram = input.map { ("$it .").split(" ").toMutableList() }
  private val regs = regs.associate { it.first to it.second }.toMutableMap()

  private var pc = 0
  val inputQueue = mutableListOf<Long>()
  val outQueue = mutableListOf<Long>()

  private fun isReg(s: String): Boolean = regs.containsKey(s) or (s[0] in 'a'..'z')

  fun getValue(s: String): Long = when (s.toLongOrNull()) {
    null -> regs.getOrPut(s) { 0L }
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

class VM18(val input: List<String>, regs: List<Pair<String, Long>> = listOf(), private val ip: String) {
  private val ram = input.map { ("$it .").split(" ").toMutableList() }
  private val regs = regs.associate { it.first to it.second }.toMutableMap()

  private var pc = 0L
  fun run(day19: Boolean = false, day21: Boolean = false): Long {
    while (pc < ram.size) {
      val (inst, in1, in2, out1) = ram[pc.toInt()]
      if (day19 && pc == 2L) return regs["5"]!!
      if (day21 && pc == 28L) return regs["4"]!!
      regs[ip] = pc
      when (inst) {
        "addr" -> regs[out1] = regs[in1]!! + regs[in2]!!
        "addi" -> regs[out1] = regs[in1]!! + in2.toInt()

        "mulr" -> regs[out1] = regs[in1]!! * regs[in2]!!
        "muli" -> regs[out1] = regs[in1]!! * in2.toInt()

        "banr" -> regs[out1] = regs[in1]!! and regs[in2]!!
        "bani" -> regs[out1] = regs[in1]!! and in2.toLong()

        "borr" -> regs[out1] = regs[in1]!! or regs[in2]!!
        "bori" -> regs[out1] = regs[in1]!! or in2.toLong()

        "setr" -> regs[out1] = regs[in1]!!
        "seti" -> regs[out1] = in1.toLong()

        "gtir" -> regs[out1] = if (in1.toLong() > regs[in2]!!) 1L else 0
        "gtri" -> regs[out1] = if (regs[in1]!! > in2.toLong()) 1L else 0
        "gtrr" -> regs[out1] = if (regs[in1]!! > regs[in2]!!) 1L else 0

        "eqir" -> regs[out1] = if (in1.toLong() == regs[in2]!!) 1L else 0
        "eqri" -> regs[out1] = if (regs[in1]!! == in2.toLong()) 1L else 0
        "eqrr" -> regs[out1] = if (regs[in1]!! == regs[in2]!!) 1L else 0
      }
      pc = regs[ip]!! + 1
    }
    return 0
  }
}