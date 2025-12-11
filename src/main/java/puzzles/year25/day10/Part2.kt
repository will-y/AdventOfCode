package puzzles.year25.day10

import util.Puzzle
import java.util.LinkedList

// Get all ways to get each joltage
// Check all combos?
class Part2 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        val input = inputString.trim().lines().map{ it.split(" ") }.map{ parse(it) }
        var result = 0
        for (i in input) {
            val queue = LinkedList<Circuit>()
            val seenPresses = HashSet<ArrayWrapper<Int>>()
            val startingSeen = Array(i.second.size) {0}
            queue.addAll(i.second.mapIndexed { index, it -> Circuit(Array(i.first.size) {0}, it, 0, with(clone(startingSeen), index, 1)) })
            while (queue.isNotEmpty()) {
                val circuit = queue.removeFirst()
                applyButton(circuit.jolts, circuit.nextButton)
                var skip = false

                if (circuit.jolts.contentEquals(i.first)) {
                    result += circuit.count + 1
                    println("Found ${circuit.count + 1}")
                    break
                }
                for (j in circuit.jolts.indices) {
                    if (circuit.jolts[j] > i.first[j]) {
                        skip = true
                        break
                    }
                }

                if (skip) continue

                for (index in i.second.indices) {
                    val presses = clone(circuit.seen)
                    presses[index]++
                    if (seenPresses.add(ArrayWrapper(clone(presses)))) {
                        queue.addLast(Circuit(clone(circuit.jolts), i.second[index], circuit.count + 1, presses))
                    }
                }
            }
        }

        return result
    }

    fun clone(input: Array<Int>): Array<Int> {
        return Array(input.size)  {i -> input[i]}
    }

    fun with(input: Array<Int>, index: Int, value: Int): Array<Int> {
        input[index] = value
        return input
    }

    fun <T> clone(input: List<T>): MutableList<T> {
        val result = ArrayList<T>()
        result.addAll(input)
        return result
    }

    fun <T> clone(input: Map<T, T>): MutableMap<T, T> {
        val result = HashMap<T, T>()
        result.putAll(input)
        return result
    }

    fun applyButton(lights: Array<Int>, buttons: List<Int>) {
        for (i in buttons) {
            lights[i]++
        }
    }

    fun parse(input: List<String>): Pair<Array<Int>, List<List<Int>>> {
        var jolts: Array<Int> = Array(0) {0}
        var buttons: MutableList<List<Int>> = ArrayList()

        for (s in input) {
            if (s.startsWith("{")) {
                jolts = s.substring(1, s.length - 1).split(",").map { it.toInt() }.toIntArray().toTypedArray()
            } else if (s.startsWith("(")) {
                buttons.add(s.substring(1, s.length - 1).split(",").map { it.toInt() })
            }
        }
        buttons = buttons.sortedBy { it.size }.reversed().toMutableList()
        return Pair(jolts, buttons)
    }

    data class Circuit(
        var jolts: Array<Int>,
        var nextButton: List<Int>,
        var count: Int = 0,
        var seen: Array<Int>
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Circuit

            if (count != other.count) return false
            if (jolts.contentEquals(other.jolts)) return false
            if (nextButton != other.nextButton) return false
            if (!seen.contentEquals(other.seen)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = count
            result = 31 * result + jolts.hashCode()
            result = 31 * result + nextButton.hashCode()
            result = 31 * result + seen.contentHashCode()
            return result
        }
    }

    class ArrayWrapper<T>(private val data: Array<T>) {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is ArrayWrapper<*>) return false
            return data.contentEquals(other.data)
        }

        override fun hashCode(): Int {
            return data.contentHashCode()
        }
    }
}