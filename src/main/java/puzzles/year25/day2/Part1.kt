package puzzles.year25.day2

import util.Puzzle

class Part1 : Puzzle<Long?> {
    override fun getAnswer(inputString: String): Long {
        return inputString.split(",").map { s -> s.split("-") }.sumOf { a -> getInvalidSum(a) }
    }

    fun getInvalidSum(l: List<String>): Long {
        var result = 0L
        for (i in l.get(0).toLong()..l.get(1).toLong()) {
            val s = i.toString()
            if (s.length % 2 == 1) continue
            if (s.substring(0, s.length / 2) == s.substring(s.length / 2)) {
                result += i
            }
        }

        return result
    }
}
