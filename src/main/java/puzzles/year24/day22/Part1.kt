package puzzles.year24.day22

import util.Puzzle

class Part1 : Puzzle<Long?> {
    override fun getAnswer(inputString: String): Long {
        val inputs = inputString.lines().map { it.toLong()}
        var result = 0L
        for (input in inputs) {
            var secretNumber = input
            for (i in 0..<2000) {
                secretNumber = nextNumber(secretNumber)
            }
            result += secretNumber
        }

        return result
    }

    private fun nextNumber(num: Long) : Long {
        val step1 = ((num * 64L) xor num) % 16777216L
        val step2 = ((step1 / 32L) xor step1) % 16777216L
        return ((step2 * 2048L) xor step2) % 16777216L
    }
}
