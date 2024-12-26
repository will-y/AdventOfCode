package puzzles.year24.day24

import util.Puzzle
import kotlin.math.pow

class Part2 : Puzzle<String?> {
    override fun getAnswer(inputString: String): String {
        val originalWires = inputString.split(System.lineSeparator() + System.lineSeparator())[0].lines().map { it.split(": ") }
            .associate { Pair(it[0], it[1].toInt()) }.toMutableMap()
        val wires = HashMap(originalWires)
        val gates = inputString.split(System.lineSeparator() + System.lineSeparator())[1].lines().map {stringToGate(it)}

        val x = getNumber(wires, 'x')
        val y = getNumber(wires, 'y')

        val gateQueue = ArrayDeque(gates)
        val orderedGates = mutableListOf<Gate>()
        val gateMap = mutableMapOf<String, Gate>()

        while (gateQueue.isNotEmpty()) {
            val gate = gateQueue.removeFirst()

            if (wires.containsKey(gate.g1) && wires.containsKey(gate.g2)) {
                wires[gate.g3] = gate.getOutput(wires[gate.g1]!!, wires[gate.g2]!!)
                orderedGates.add(gate)
                gateMap[gate.g3] = gate
            } else {
                gateQueue.add(gate)
            }
        }

//        wires = inputString.split(System.lineSeparator() + System.lineSeparator())[0].lines().map { it.split(": ") }
//            .associate { Pair(it[0], it[1].toInt()) }.toMutableMap()
//
//        for (gate in orderedGates) {
//            wires[gate.g3] = gate.getOutput(wires[gate.g1]!!, wires[gate.g2]!!)
//        }

        val wrongBitsNumber: Long = (x + y) xor getNumber(wires, 'z')

        val wrongBits = getWrongBits(wrongBitsNumber)

//        val wrongGates = getWrongGates(gateMap, wrongBits)

        val gatePairs = getGatePairs(orderedGates)

        val orderedPairs: MutableList<Pair<Pair<String, String>, Int>> = mutableListOf()

        // TODO: iterate through gate pairs, with the gates swapped and the order by the number of wrong gates. The iterate through all combinations of 4 gate pairs swap them, and run again
        for (gatePair in gatePairs) {
            gateMap[gatePair.first]!!.g3 = gatePair.second
            gateMap[gatePair.second]!!.g3 = gatePair.first
            orderedPairs.add(Pair(gatePair, findWrongBits(originalWires, orderedGates).size))
            gateMap[gatePair.first]!!.g3 = gatePair.first
            gateMap[gatePair.second]!!.g3 = gatePair.second
        }

        orderedPairs.sortBy {it.second}

        for (i in orderedPairs.indices) {
            val firstPair = orderedPairs[i]
            gateMap[firstPair.first.first]!!.g3 = firstPair.first.second
            gateMap[firstPair.first.second]!!.g3 = firstPair.first.first
            for (j in i + 1 ..< orderedPairs.size) {
                val secondPair = orderedPairs[j]
                gateMap[secondPair.first.first]!!.g3 = secondPair.first.second
                gateMap[secondPair.first.second]!!.g3 = secondPair.first.first
                val wrongBits = findWrongBits(originalWires, orderedGates)
                if (wrongBits.isEmpty()) {
                    println("${firstPair.first}  ${secondPair.first}")
                }
                gateMap[secondPair.first.first]!!.g3 = secondPair.first.first
                gateMap[secondPair.first.second]!!.g3 = secondPair.first.second
            }
            gateMap[firstPair.first.first]!!.g3 = firstPair.first.first
            gateMap[firstPair.first.second]!!.g3 = firstPair.first.second
        }

        return getNumber(wires, 'z').toString()
    }

    private fun findWrongBits(originalWires: Map<String, Int>, orderedGates: List<Gate>): List<Int> {
        val gateQueue = ArrayDeque<Gate>()
        val wires = HashMap(originalWires)
        for (gate in orderedGates) {
            if (wires.containsKey(gate.g1) && wires.containsKey(gate.g2)) {
                wires[gate.g3] = gate.getOutput(wires[gate.g1]!!, wires[gate.g2]!!)
            } else {
                gateQueue.add(gate)
            }
        }

        val seen = mutableSetOf<Gate>()
        while (gateQueue.isNotEmpty()) {
            val gate = gateQueue.removeFirst()
            if (seen.contains(gate)) continue
            if (wires.containsKey(gate.g1) && wires.containsKey(gate.g2)) {
                wires[gate.g3] = gate.getOutput(wires[gate.g1]!!, wires[gate.g2]!!)
            } else {
                gateQueue.add(gate)
                seen.add(gate)
            }
        }

        return getWrongBits((getNumber(wires, 'x') + getNumber(wires, 'y')) xor getNumber(wires, 'z'))

    }

    private fun getWrongGates(gateMap: MutableMap<String, Gate>, wrongBits: List<Int>): Set<String> {
        val wrongGates = mutableSetOf<String>()

        for (wrongBit in wrongBits) {
            addWrong(wrongGates, gateMap, wireFromNumber(wrongBit))
        }

        return wrongGates
    }

    private fun addWrong(wrongGates: MutableSet<String>, gateMap: MutableMap<String, Gate>, s: String) {
        val toAdd = ArrayDeque<String>()
        toAdd.add(s)

        while (toAdd.isNotEmpty()) {
            val next = toAdd.removeFirst()
            wrongGates.add(next)

            if (!gateMap.containsKey(next)) {
                continue
            }

            if (gateMap.containsKey(gateMap[next]!!.g1)) {
                toAdd.add(gateMap[next]!!.g1)
            }

            if (gateMap.containsKey(gateMap[next]!!.g2)) {
                toAdd.add(gateMap[next]!!.g2)
            }
        }
    }

    private fun getGatePairs(orderedGates: List<Gate>): Set<Pair<String, String>> {
        val result = mutableSetOf<Pair<String, String>>()
        for (i in orderedGates.indices) {
            for (j in i + 1..<orderedGates.size) {
                result.add(Pair(orderedGates[i].g3, orderedGates[j].g3))
            }
        }

        return result
    }

    private fun wireFromNumber(num: Int): String {
        return if (num < 10) {
            "z0$num"
        } else {
            "z$num"
        }
    }

    private fun getWrongBits(long: Long): List<Int> {
        val result = mutableListOf<Int>()
        for (i in 0..<64) {
            if ((long shr i) % 2 != 0L) {
                result.add(i)
            }
        }

        return result
    }

    private fun getNumber(wires: Map<String, Int>, c: Char): Long {
        val zWires = wires.filter {it.key.startsWith(c) }.toSortedMap()
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
