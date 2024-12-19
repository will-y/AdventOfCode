package puzzles.year24.day19

import util.Puzzle

class Part1 : Puzzle<Int?> {
    val cache = mutableMapOf<String, Boolean>()

    override fun getAnswer(inputString: String): Int {
        val towels = inputString.split(System.lineSeparator() + System.lineSeparator())[0].split(", ").sortedBy { it.length }.reversed()
        val patterns = inputString.split(System.lineSeparator() + System.lineSeparator())[1].lines()

        var result = 0;
        for (pattern in patterns) {
            if (isPossible(towels, pattern)) {
                result++
            }
        }
        return result
    }

    private fun isPossible(towels: List<String>, pattern: String): Boolean {
        if (pattern.isEmpty()) return true;

        if (cache.containsKey(pattern)) {
            return cache[pattern]!!
        }

        for (towel in towels) {
            if (pattern.startsWith(towel)) {
                if (isPossible(towels, pattern.substring(towel.length))) {
                    cache[pattern] = true
                    return true;
                }
            }
        }

        cache[pattern] = false
        return false;
    }
}
