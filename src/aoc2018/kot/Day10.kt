package aoc2018.kot

import getNumbers
import java.io.File

object Day10 {
  data class Particle(var x: Int, var y: Int, val xSpeed: Int, val ySpeed: Int) {
    fun tick() {
      x += xSpeed
      y += ySpeed
    }
  }

  fun solve(input: List<String>): Pair<String, Int> {
    val particles = parse(input)
    var s = 0
    while (true) {
      val (lminX, lminY) = Pair(particles.minBy { it.x }!!.x, particles.minBy { it.y }!!.y)
      val (lmaxX, lmaxY) = Pair(particles.maxBy { it.x }!!.x, particles.maxBy { it.y }!!.y)
      particles.forEach(Particle::tick)
      val (minX, minY) = Pair(particles.minBy { it.x }!!.x, particles.minBy { it.y }!!.y)
      val (maxX, maxY) = Pair(particles.maxBy { it.x }!!.x, particles.maxBy { it.y }!!.y)
      s++
      if (lmaxY - lminY < maxY - minY && lmaxX - lminX < maxX - minX) {
        val sb = StringBuilder()
        (lminY..lmaxY).forEach { y ->
          val row = particles.filter { it.y - it.ySpeed == y }.map { it.x - it.xSpeed }
          (lminX..lmaxX).forEach { if (it in row) sb.append('#') else sb.append(' ') }
          sb.append('\n')
        }
        return Pair(sb.toString(), (s - 1))
//        val image = BufferedImage(lmaxX - lminX + 1, lmaxY - lminY + 1, TYPE_INT_RGB)
//        for (star in particles) {
//          image.setRGB(star.x - star.xSpeed - lminX, star.y - star.ySpeed - lminY, Color.WHITE.rgb)
//        }
//        ImageIO.write(image, "png", File("Day10.png"))
      }
    }
  }

  private fun parse(input: List<String>): List<Particle> = input.map {
    val (x, y, xSpeed, ySpeed) = it.getNumbers()
    Particle(x, y, xSpeed, ySpeed)
  }
}

fun main(args: Array<String>) {
  val input = File("./input/2018/Day10_input.txt").readLines()
  val (partOne, partTwo) = Day10.solve(input)
  println("Part One = $partOne")
  println("Part Two = $partTwo")
}
