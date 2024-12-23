package puzzles.year24.day23

import util.Puzzle

class Part2 : Puzzle<String?> {
    override fun getAnswer(inputString: String): String {
        val lines = inputString.lines()
        val graph = mutableMapOf<String, MutableSet<String>>()

        for (line in lines) {
            val parts = line.split("-")
            graph.compute(parts[0]) {_, v -> add(v ?: mutableSetOf(), parts[1])}
            graph.compute(parts[1]) {_, v -> add(v ?: mutableSetOf(), parts[0])}
        }

        for (computer in graph.keys) {
            val connections = graph[computer]!!
            val subsets = subsets(connections)

            for (subset in subsets) {
                subset.add(computer)

                if (isAllConnected(graph, subset)) {
                    return subset.sorted().joinToString(",")
                }
            }
        }


        return ""
    }

    fun isAllConnected(graph: MutableMap<String, MutableSet<String>>, set: Set<String>): Boolean {
        for (computer in set) {
            for (computer2 in set) {
                if (computer != computer2 && !graph[computer].orEmpty().contains(computer2)) {
                    return false
                }
            }
        }
        return true
    }

    fun subsets(set: Set<String>): MutableSet<MutableSet<String>> {
        val result = mutableSetOf<MutableSet<String>>()
        for (x in set) {
            val newSet = HashSet(set)
            newSet.remove(x)
            result.add(newSet)
        }

        return result
    }

    fun <T>add(set: MutableSet<T>, value: T): MutableSet<T> {
        set.add(value)
        return set
    }
}
