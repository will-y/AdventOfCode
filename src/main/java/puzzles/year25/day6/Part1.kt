package puzzles.year25.day6

import util.Puzzle

class Part1 : Puzzle<Long?> {
    override fun getAnswer(inputString: String): Long {
        val problems = inputString.trim().lines().map{it.trim().split("\\s+".toRegex()).filter{it.isNotEmpty()}}

        var result = 0L

        for (i in 0..problems[0].size - 1) {
            val operation = problems[problems.size - 1][i]

            if (operation == "+") {
                result+= problems.take(problems.size - 1).sumOf { it[i].toLong() }
            } else if (operation == "*") {
                result+= problems.take(problems.size - 1).fold(1L) {acc, e -> acc * e[i].toLong() }
            }
        }

        return result
    }
}
