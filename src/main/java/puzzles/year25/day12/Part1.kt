package puzzles.year25.day12

import util.Puzzle

class Part1 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        val count = arrayOf(7, 5, 7, 7, 7, 6)
        val input = inputString.trim().split(System.lineSeparator() + System.lineSeparator())
        var result = 0
        for (i in input.last().lines()) {
            val split = i.trim().split(":")
            val split2 = split[0].split("x").map { it.toInt() }
            val sum = split[1].trim().split(" ").mapIndexed { index, it -> it.toInt() * count[index] }.sum()

            if (split2[0] * split2[1] > sum) {
                result++
            }
        }
        return result
    }
}

// 405 low