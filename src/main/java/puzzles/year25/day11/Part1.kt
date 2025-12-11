package puzzles.year25.day11

import util.Puzzle
import java.util.LinkedList

class Part1 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        val input = inputString.trim().lines().associate { parse(it) }

        val queue = LinkedList<Circuit>()
        queue.add(Circuit("you", listOf()))
        var result = 0
        while (queue.isNotEmpty()) {
            val circuit = queue.removeFirst()
            if (circuit.next == "out") {
                result++
                continue
            }

            val nextPaths = input[circuit.next] ?: listOf()
            for (path in nextPaths) {
                queue.add(Circuit(path, with(nextPaths, path)))
            }
        }
        return result
    }

    fun with(input: List<String>, s: String): List<String> {
        val result = mutableListOf<String>()
        result.addAll(input)
        result.add(s)
        return result
    }

    fun parse(input: String): Pair<String, List<String>> {
        val split = input.trim().split(":")
        return Pair(split[0], split[1].trim().split(" "))
    }

    data class Circuit(val next: String, val path: List<String>)
}
