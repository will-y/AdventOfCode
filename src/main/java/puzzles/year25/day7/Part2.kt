package puzzles.year25.day7

import util.Puzzle

class Part2 : Puzzle<Long?> {
    override fun getAnswer(inputString: String): Long {
        val lines = inputString.trim().lines()
        val waysToGetHere: Array<Array<Long>> = Array(lines.size + 1) {
            Array(lines[0].length) { 0 };
        }

        waysToGetHere[0][lines[0].indexOf('S')] = 1

        for (i in lines.indices) {
            for (j in lines[i].indices) {
                val c = lines[i][j]
                val waysToGetC = waysToGetHere[i][j]
                if (c == '^') {
                    waysToGetHere[i + 1][j - 1] += waysToGetC
                    waysToGetHere[i + 1][j + 1] += waysToGetC
                } else {
                    waysToGetHere[i + 1][j] += waysToGetC
                }
            }
        }

        return waysToGetHere.last().sumOf { it }
    }
}
