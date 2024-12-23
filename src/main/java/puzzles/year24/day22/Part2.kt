package puzzles.year24.day22

import util.Puzzle

class Part2 : Puzzle<Long?> {
    override fun getAnswer(inputString: String): Long {
        val inputs = inputString.lines().map { it.toLong()}
        val sequenceMap = mutableMapOf<List<Int>, Long>()

        for (input in inputs) {
            var secretNumber = Pair(input, listOf<Int>())
            val foundThisRun = mutableSetOf<List<Int>>()
            for (i in 0..<2000) {
                val next = nextNumber(secretNumber.first, secretNumber.second)
                if (next.second.size == 4 && !foundThisRun.contains(next.second)) {
                    sequenceMap.compute(next.second) { _, v -> (v ?: 0) + (next.first % 10) }
                    foundThisRun.add(next.second)
                }
                secretNumber = next
            }
        }

        return sequenceMap.values.max()
    }

    private fun nextNumber(num: Long, seq: List<Int>) : Pair<Long, List<Int>> {
        val step1 = ((num * 64L) xor num) % 16777216L
        val step2 = ((step1 / 32L) xor step1) % 16777216L
        val result = ((step2 * 2048L) xor step2) % 16777216L
        return Pair(result, nextSeq(num, result, seq))
    }

    private fun nextSeq(lastNum: Long, thisNum: Long, seq: List<Int>) : List<Int> {
        val newSeq = ArrayList(seq)
        newSeq.add(((thisNum % 10) - (lastNum % 10)).toInt())
        if (newSeq.size == 5) {
            newSeq.removeFirst()
        }

        return newSeq
    }
}
