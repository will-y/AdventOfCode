package puzzles.year24.day19

import util.Puzzle

class Part2 : Puzzle<Long?> {
    val cache = mutableMapOf<String, Long>()

    override fun getAnswer(inputString: String): Long {
        val towels = inputString.split(System.lineSeparator() + System.lineSeparator())[0].split(", ")
        val patterns = inputString.split(System.lineSeparator() + System.lineSeparator())[1].lines()

        var result = 0L
        var count = 0
        for (pattern in patterns) {
            cache.clear()
           val newCount = getCount(towels, pattern)
//            println("$pattern -> $newCount")
            result += newCount
            if (newCount > 0) {
                count++
            }
        }

        return result
    }

    private fun getCount(towels: List<String>, pattern: String): Long {
        if (pattern.isBlank()) return 1L

        if (cache.containsKey(pattern)) {
            return cache[pattern]!!
        }

        var totalCount = 0L
        for (towel in towels) {
            if (pattern.startsWith(towel)) {
                val newCount = getCount(towels, pattern.substring(towel.length))
                totalCount += newCount
            }
        }

        cache[pattern] = totalCount
        return totalCount
    }
}
