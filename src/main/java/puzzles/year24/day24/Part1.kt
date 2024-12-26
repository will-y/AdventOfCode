package puzzles.year24.day24

import util.Puzzle
import kotlin.math.pow

class Part1 : Puzzle<Long?> {
    override fun getAnswer(inputString: String): Long {
        val wires = inputString.split(System.lineSeparator() + System.lineSeparator())[0].lines().map { it.split(": ") }
                .associate { Pair(it[0], it[1].toInt()) }.toMutableMap()
        val gates = inputString.split(System.lineSeparator() + System.lineSeparator())[1].lines().map {stringToGate(it)}

        val gateQueue = ArrayDeque(gates)

        while (gateQueue.isNotEmpty()) {
            val gate = gateQueue.removeFirst()

            if (wires.containsKey(gate.g1) && wires.containsKey(gate.g2)) {
                wires.put(gate.g3, gate.getOutput(wires[gate.g1]!!, wires[gate.g2]!!))
            } else {
                gateQueue.add(gate)
            }
        }

        val zWires = wires.filter {it.key.startsWith("z") }.toSortedMap()
        var result = 0L
        var i = 0
        for (wire in zWires.values) {
            result += 2.0.pow(i++).toLong() * wire.toLong()
        }

        return result
    }

    private fun stringToGate(s: String): Gate {
        val split = s.split(" ")
        return Gate(split[0], split[2], split[4], split[1])
    }
}

data class Gate(val g1: String, val g2: String, var g3: String, val opp: String) {
    fun getOutput(v1: Int, v2: Int): Int {
        return when (opp) {
            "AND" -> v1 and v2
            "OR" -> v1 or v2
            "XOR" -> v1 xor v2
            else -> 0
        }
    }
}
