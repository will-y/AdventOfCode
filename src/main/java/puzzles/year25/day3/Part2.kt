package puzzles.year25.day3

import util.Puzzle

class Part2 : Puzzle<Long?> {
    override fun getAnswer(inputString: String): Long {
        return inputString.lines().sumOf { maxJoltage(it) }
    }

    fun maxJoltage(string: String): Long {
        val result: Array<Int> = Array(12) {0}
        val charArray = string.toCharArray().map(Char::digitToInt)

        for (i in charArray.indices) {
            val c = charArray[i]
            for (j in 0..11) {
                if (c > result[j] && string.length - i >= 12 - j) {
                    result[j] = c
                    result.fill(0, j + 1, 12)
                    break
                }
            }
        }

        return result.joinToString("") { it.toString() }.toLong()
    }
}
