package puzzles.year25.day9

import util.Puzzle
import kotlin.math.absoluteValue

class Part1 : Puzzle<Long?> {
    override fun getAnswer(inputString: String): Long {
        val input = inputString.trim().lines().map{ s -> s.split(",").map { it.toLong() }}

        var max = 0L
        for (i in input) {
            for (j in input) {
                val area = area(i, j)
                if (area > max) {
                    max = area
                }
            }
        }

        return max
    }

    fun area(x1: List<Long>, x2: List<Long>): Long {
        return (x1[0] - x2[0] + 1).absoluteValue * (x1[1] - x2[1] + 1).absoluteValue
    }
}

// 4757983825
