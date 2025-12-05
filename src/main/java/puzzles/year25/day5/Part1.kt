package puzzles.year25.day5

import util.Puzzle

class Part1 : Puzzle<Long?> {
    override fun getAnswer(inputString: String): Long {
        val split = inputString.split(System.lineSeparator() + System.lineSeparator())
        val ranges = split[0].lines().map { Pair(it.split("-")[0].toLong(), it.split("-")[1].toLong()) }
        val toCheck = split[1].trim().lines().map {it.toLong()}

        var count = 0L
        for (i in toCheck) {
            for (j in ranges) {
                if (i >= j.first && i <= j.second) {
                    count++
                    break
                }
            }
        }
        return count
    }
}
