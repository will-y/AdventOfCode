package puzzles.year25.day2

import util.Puzzle

class Part2 : Puzzle<Long?> {
    val pattern = "^(\\d+)\\1+\$"
    val regex = Regex(pattern)

    override fun getAnswer(inputString: String): Long {
        return inputString.split(",").map { s -> s.split("-") }.sumOf { a -> getInvalidSum(a) }
    }

    fun getInvalidSum(l: List<String>): Long {
        var result = 0L
        for (i in l[0].toLong()..l[1].toLong()) {
            val s = i.toString()
            if (regex.containsMatchIn(s)) {
                result += i
            }
        }

        return result
    }
}
