package aoc2015.kot

import java.security.MessageDigest

object Day04 {
  fun solve(salt: String, prefix: String): Int = generateSequence(0) { it + 1 }.map { md5Hash(salt + it) }.indexOfFirst { it.startsWith(prefix) }

  private fun md5Hash(input: String): String = MessageDigest.getInstance("MD5")!!.digest(input.toByteArray()).toHex()

  private fun ByteArray.toHex(): String {
    val result = StringBuffer()

    forEach {
      val octet = it.toInt()
      val firstIndex = (octet and 0xF0).ushr(4)
      val secondIndex = octet and 0x0F
      result.append("0123456789ABCDEF"[firstIndex])
      result.append("0123456789ABCDEF"[secondIndex])
    }
    return result.toString()
  }
}

fun main(args: Array<String>) {
  println("Part One = ${Day04.solve("yzbqklnj", "00000")}")
  println("Part Two = ${Day04.solve("yzbqklnj", "000000")}")
}
