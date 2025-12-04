package puzzles.year25.day3

import util.Puzzle

class Part1 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        return inputString.lines().sumOf { maxJoltage(it) }
    }

    fun maxJoltage(string: String): Int {
        var max = 0

        for (i in string.toCharArray().indices) {
            for (j in string.substring(i + 1)) {
                val next = string.toCharArray()[i].digitToInt() * 10 + j.digitToInt()
                if (next > max) {
                    max = next
                }
            }
        }

        println(max)

        return max
    }
}
