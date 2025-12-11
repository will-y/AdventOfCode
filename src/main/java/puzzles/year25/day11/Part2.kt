package puzzles.year25.day11

import util.Puzzle
import java.util.LinkedList
import kotlin.math.min

class Part2 : Puzzle<Long?> {
    override fun getAnswer(inputString: String): Long {
        val input = inputString.trim().lines().associate { parse(it) }
        val parents = parents(input)

        val queue = LinkedList<Circuit>()
        val seen = HashMap<String, Node>()

        queue.add(Circuit("svr", ""))

        test@ while (queue.isNotEmpty()) {
            val circuit = queue.removeFirst()
            val nextPaths = input[circuit.next] ?: listOf()

            for (p in parents[circuit.next] ?: emptyList()) {
                if (!seen.containsKey(p)) {
                    queue.add(circuit)
                    continue@test
                }
            }
            var add = true
            if (seen.containsKey(circuit.next)) {
                seen.put(circuit.next, combineNodes(seen[circuit.next]!!, seen[circuit.previousNode] ?: Node(1, 0, 0, 0)))
                add = false
            } else {
                seen.put(circuit.next, clone(seen[circuit.previousNode] ?: Node(1, 0, 0, 0)))
            }

            if (circuit.next == "fft") {
                seen[circuit.next]!!.fPaths = seen[circuit.next]!!.paths
                seen[circuit.next]!!.bothPaths = min(seen[circuit.next]!!.dPaths, seen[circuit.next]!!.fPaths)
            }

            if (circuit.next == "dac") {
                seen[circuit.next]!!.dPaths = seen[circuit.next]!!.paths
                seen[circuit.next]!!.bothPaths = min(seen[circuit.next]!!.dPaths, seen[circuit.next]!!.fPaths)

            }

            if (circuit.next == "out") {
                continue
            }


            if (add) {
                for (path in nextPaths) {
                    queue.add(Circuit(path, circuit.next))
                }
            }
        }

        return seen["out"]!!.bothPaths
    }

    fun with(input: Set<String>, s: String): Set<String> {
        val result = mutableSetOf<String>()
        result.addAll(input)
        result.add(s)
        return result
    }

    fun parse(input: String): Pair<String, List<String>> {
        val split = input.trim().split(":")
        return Pair(split[0], split[1].trim().split(" "))
    }

    fun clone(prev: Node): Node {
        return Node(prev.paths, prev.fPaths, prev.dPaths, prev.bothPaths)
    }

    fun combineNodes(prev: Node, next: Node): Node {
        return Node(prev.paths + next.paths, prev.fPaths + next.fPaths, prev.dPaths + next.dPaths, prev.bothPaths + next.bothPaths)
    }

    fun parents(input: Map<String, List<String>>): Map<String, Set<String>> {
        val result = HashMap<String, MutableSet<String>>()
        for (entry in input) {
            for (e in entry.value) {
                result.compute(e) {key, value -> with(value ?: HashSet(), entry.key).toMutableSet() }
            }
        }

        return result
    }

    data class Circuit(val next: String, val previousNode: String)

    data class Node(var paths: Long, var fPaths: Long, var dPaths: Long, var bothPaths: Long)
}

// 37317593856 LOW
