package puzzles.year24.day13

import util.Puzzle

class Part2 : Puzzle<Long?> {
    override fun getAnswer(inputString: String): Long {
        val machines = inputString.split(System.lineSeparator() + System.lineSeparator())
        var result = 0L
        for (machine in machines) {
            val machineLines = machine.lines()
            val aIn = parseInputLine(machineLines[0], '+')
            val bIn = parseInputLine(machineLines[1], '+')
            var out = parseInputLine(machineLines[2], '=')
            out = Pair(out.first + 10000000000000L, out.second + 10000000000000L)

            val bOut = ((out.second * aIn.first - aIn.second * out.first) / (bIn.second * aIn.first - aIn.second * bIn.first))
            val aOut = ((out.first - bIn.first * bOut) / aIn.first)

            if (isCorrect(aOut, bOut, aIn, bIn, out)) {
                result += bOut + aOut * 3
            }
        }
        return result;
    }

    private fun parseInputLine(line: String, char: Char): Pair<Long, Long> {
        val array = line.split(": ")[1].split(", ").map {it.split(char.toString())[1].toLong()}

        return Pair(array[0], array[1])
    }

    private fun isCorrect(a: Long, b: Long, aIn: Pair<Long, Long>, bIn: Pair<Long, Long>, out: Pair<Long, Long>): Boolean {
        return a * aIn.first + b * bIn.first == out.first && a * aIn.second + b * bIn.second == out.second
    }
}

// 21818 low
