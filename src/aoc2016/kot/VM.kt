package aoc2016.kot

class VM(val input: List<String>) {
    val ram = input.map { it.split(" ").toTypedArray() }.toMutableList()
    private fun isReg(s: String): Boolean {
        return s[0] >= 'a' && s[0] <= 'd'
    }

    private fun getValue(s: String, regs: LongArray): Long {
        return when (isReg(s)) {
            true -> regs[s[0] - 'a']
            false -> s.toLong()
        }
    }

    fun run(regA: Long = 0, regB: Long = 0, regC: Long = 0, regD: Long = 0): Long {
        var periodLength = 0L
        var lastOut = 1L
        var pc = 0
        var reg: Int
        val regs = longArrayOf(regA, regB, regC, regD)
        while (pc < ram.size) {
            val inst = ram[pc]
            reg = inst[1][0] - 'a'
            when (inst[0]) {
                "cpy" -> {
                    if (isReg(inst[2]))
                        regs[inst[2][0] - 'a'] = getValue(inst[1], regs)
                }
                "inc" -> regs[reg]++
                "dec" -> regs[reg]--
                "jnz" -> {
                    if (getValue(inst[1], regs) != 0L)
                        pc += getValue(inst[2], regs).toInt() - 1
                }
                "tgl" -> {
                    val offset = pc + getValue(inst[1], regs).toInt()
                    if (offset < ram.size)
                        ram[offset][0] = when (ram[offset][0]) {
                            "inc" -> "dec"
                            "tgl" -> "inc"
                            "dec" -> "inc"
                            "jnz" -> "cpy"
                            "cpy" -> "jnz"
                            else -> ram[offset][0]
                        }
                }
                "out" -> {
                    if (getValue(inst[1], regs) == lastOut) {
                        return -1
                    } else if (periodLength > 500)
                        return 1
                    periodLength++
                    lastOut = getValue(inst[1], regs)
                }
            }
            pc++
        }
        return regs[0]
    }
}
