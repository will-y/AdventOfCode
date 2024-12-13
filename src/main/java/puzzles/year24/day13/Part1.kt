package puzzles.year24.day13

import util.Puzzle
import kotlin.math.roundToInt

class Part1 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        val machines = inputString.split(System.lineSeparator() + System.lineSeparator())
        var result = 0
        for (machine in machines) {
            val machineLines = machine.lines()
            val aIn = parseInputLine(machineLines[0], '+')
            val bIn = parseInputLine(machineLines[1], '+')
            val out = parseInputLine(machineLines[2], '=')

            val bOut = (out.second - aIn.second * out.first / aIn.first) / (bIn.second - aIn.second * bIn.first / aIn.first)
            val aOut = (out.first - bIn.first * bOut) / aIn.first

            if (isCorrect(aOut.roundToInt(), bOut.roundToInt(), aIn, bIn, out)) {
                result += bOut.roundToInt() + aOut.roundToInt() * 3
            }
        }
        return result;
    }

    private fun parseInputLine(line: String, char: Char): Pair<Double, Double> {
        val array = line.split(": ")[1].split(", ").map {it.split(char.toString())[1].toDouble()}

        return Pair(array[0], array[1])
    }

    private fun isCorrect(a: Int, b: Int, aIn: Pair<Double, Double>, bIn: Pair<Double, Double>, out: Pair<Double, Double>): Boolean {
        return a * aIn.first.roundToInt() + b * bIn.first.roundToInt() == out.first.roundToInt() && a * aIn.second.roundToInt() + b * bIn.second.roundToInt() == out.second.roundToInt()
    }
}

// 21818 low
