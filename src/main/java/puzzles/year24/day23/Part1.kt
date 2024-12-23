package puzzles.year24.day23

import util.Puzzle

class Part1 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        val lines = inputString.lines()
        val graph = mutableMapOf<String, MutableSet<String>>()

        for (line in lines) {
            val parts = line.split("-")
            graph.compute(parts[0]) {_, v -> add(v ?: mutableSetOf(), parts[1])}
            graph.compute(parts[1]) {_, v -> add(v ?: mutableSetOf(), parts[0])}
        }

        val groups = mutableSetOf<Set<String>>()

        for (computer in graph.keys) {
            for (computer2 in graph[computer]!!) {
                for (computer3 in graph[computer2].orEmpty()) {
                    if (graph[computer3].orEmpty().contains(computer)) {
                        groups.add(setOf(computer, computer2, computer3))
                    }
                }
            }
        }

        return groups.filter{it.any{it.startsWith("t")}}.size
    }

    fun <T>add(set: MutableSet<T>, value: T): MutableSet<T> {
        set.add(value)
        return set
    }
}
