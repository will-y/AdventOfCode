package puzzles.year25.day10

import util.Puzzle
import java.util.LinkedList

class Part1 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        val input = inputString.trim().lines().map{ it.split(" ") }.map{ parse(it) }
        var result = 0
        for (i in input) {
            val queue = LinkedList<Circuit>()
            queue.addAll(i.second.map { Circuit(i.first.map { false }.toMutableList(), it, 0) })
            while (queue.isNotEmpty()) {
                val circuit = queue.removeFirst()
                applyButton(circuit.lights, circuit.nextButton)
                if (circuit.lights.filterIndexed { index, it -> it != i.first[index] }.isEmpty()) {
                    result+= circuit.count + 1
                    break
                }

                for (button in i.second) {
                    if (button != circuit.nextButton) {
                        queue.addLast(Circuit(clone(circuit.lights), button, circuit.count + 1))
                    }
                }
            }
        }

        return result
    }

    fun <T> clone(input: List<T>): MutableList<T> {
        val result = ArrayList<T>()
        result.addAll(input)
        return result
    }

    fun applyButton(lights: MutableList<Boolean>, buttons: List<Int>) {
        for (i in buttons) {
            lights[i] = !lights[i]
        }
    }

    fun parse(input: List<String>): Pair<List<Boolean>, List<List<Int>>> {
        var lights: List<Boolean> = ArrayList()
        val buttons: MutableList<List<Int>> = ArrayList()

        for (s in input) {
            if (s.startsWith("[")) {
                lights = s.substring(1, s.length - 1).map { c -> c != '.' }
            } else if (s.startsWith("(")) {
                buttons.add(s.substring(1, s.length - 1).split(",").map { it.toInt() })
            }
        }

        return Pair(lights, buttons)
    }

    data class Circuit(
        var lights: MutableList<Boolean>,
        var nextButton: List<Int>,
        var count: Int = 0
    )
}
