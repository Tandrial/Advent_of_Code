package aoc2015.kot

import java.security.MessageDigest

object Day04 {
    fun solve(salt: String, prefix : String): Int {
        var num = 0
        while (!md5Hash(salt + num).startsWith(prefix)) num++
        return num
    }
    val digest = MessageDigest.getInstance("MD5")!!
    fun md5Hash(input: String): String {
        return digest.digest(input.toByteArray()).toHex()
    }

    fun ByteArray.toHex(): String {
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
    System.out.println("Part One = ${Day04.solve("yzbqklnj", "00000")}")
    System.out.println("Part Two = ${Day04.solve("yzbqklnj", "000000")}")
}
