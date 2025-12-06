package puzzles.year25.day6

import util.Puzzle
import kotlin.text.toLong

class Part2 : Puzzle<Long?> {
    override fun getAnswer(inputString: String): Long {
        val problems  = inputString.trim().lines().map{it.toCharArray()}
        val transposed = transpose(problems)

        var result = 0L

        var first = true
        var tempCounter = 0L
        var operation = ' '

        for (i in transposed.indices) {
            val row = transposed[i]

            if (first) {
                result += tempCounter
                tempCounter = 0
                first = false
                operation = row.last()
            }

            if (row.all { it == ' ' }) {
                first = true
                continue
            }

            if (operation == '+') {
                tempCounter += toNumber(row)
            } else if (operation == '*') {
                if (tempCounter == 0L) {
                    tempCounter = 1
                }
                tempCounter *= toNumber(row)
            }
        }

        result += tempCounter

        return result
    }

    fun transpose(input: List<CharArray>): Array<Array<Char>> {
        val result = Array(input.maxOf{it.size}) {
            Array(input.size) { ' ' }
        }

        for (i in input.indices) {
            for (j in input[i].indices) {
                result[j][i] = input[i][j]
            }
        }

        return result
    }

    fun toNumber(input: Array<Char>): Long {
        return input.take(input.size - 1).joinToString("").trim().toLong()
    }
}
