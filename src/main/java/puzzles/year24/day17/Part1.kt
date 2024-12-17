package puzzles.year24.day17

import util.Puzzle
import kotlin.math.pow

class Part1 : Puzzle<String?> {
    var regA = 0L
    var regB = 0L
    var regC = 0L

    override fun getAnswer(inputString: String): String {
        val lines = inputString.lines()
//        regA = 164279024971453L
        regA = lines[0].split(": ")[1].toLong()
        regB = lines[1].split(": ")[1].toLong()
        regC = lines[2].split(": ")[1].toLong()
        val program = lines[4].split(": ")[1].split(",").map { it.toInt() }

        val output = mutableListOf<Int>()
        var index = 0;

        while (index < program.size) {
            when (program[index]) {
                0 -> regA /= 2.0.pow(getComboValue(program[index + 1]).toDouble()).toInt()
                1 -> regB = regB xor program[index + 1].toLong()
                2 -> regB = getComboValue(program[index + 1]) % 8
                3 -> {
                    if (regA != 0L) {
                        index = program[index + 1]
                        continue
                    }
                }
                4 -> regB = regB xor regC
                5 -> output.add((getComboValue(program[index + 1]) % 8).toInt())
                6 -> regB = regA / 2.0.pow(getComboValue(program[index + 1]).toDouble()).toInt()
                7 -> regC = regA / 2.0.pow(getComboValue(program[index + 1]).toDouble()).toInt()
            }

            index += 2
        }

        return output.joinToString(",")
    }

    private fun getComboValue(i: Int): Long {
        if (i <= 3) return i.toLong()
        if (i == 4) return regA
        if (i == 5) return regB
        if (i == 6) return regC

        return -1
    }
}
