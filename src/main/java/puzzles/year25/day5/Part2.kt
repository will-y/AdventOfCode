package puzzles.year25.day5

import puzzles.year24.day9.MutablePair
import util.Puzzle

class Part2 : Puzzle<Long?> {
    override fun getAnswer(inputString: String): Long {
        val split = inputString.split(System.lineSeparator() + System.lineSeparator())
        val ranges = split[0].lines().map { Pair(it.split("-")[0].toLong(), it.split("-")[1].toLong()) }

        val compressedRanges = ArrayList<Pair<Long, Long>>()

        for (range in ranges) {
            val res = subtractAll(compressedRanges, range)

            if (res.first != -1L) {
                compressedRanges.add(res)
            }
        }

        return compressedRanges.sumOf { it.second - it.first + 1 }
    }

    fun subtractAll(compressedRanges: MutableList<Pair<Long, Long>>, range: Pair<Long, Long>): Pair<Long, Long> {
        val result = MutablePair(range.first, range.second)

        val iter = compressedRanges.iterator()

        while (iter.hasNext()) {
            val r = iter.next()
            // old one is contained
            if (r.first >= result.first && r.second <= result.second) {
                iter.remove()
                continue
            }

            if (result.first >= r.first && result.first <= r.second) {
                // First one is in range
                if (result.second >= r.first && result.second <= r.second) {
                    // Second is also in
                    return Pair(-1, -1)
                } else {
                    // Second is not in
                    result.first = r.second + 1
                }
            }

            if (result.second >= r.first && result.second <= r.second) {
                // Second one is in range, because of return up there first one can't also be in the range
                result.second = r.first - 1
            }
        }

        return Pair(result.first, result.second)
    }
}