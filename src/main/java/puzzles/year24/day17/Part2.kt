package puzzles.year24.day17

import util.Puzzle

class Part2 : Puzzle<Long?> {
    var regA = 0L
    var regB = 0L
    var regC = 0L

    override fun getAnswer(inputString: String): Long {
        val lines = inputString.lines()
        regA = lines[0].split(": ")[1].toLong()
        regB = lines[1].split(": ")[1].toLong()
        regC = lines[2].split(": ")[1].toLong()
        val program = lines[4].split(": ")[1].split(",").map { it.toInt() }.reversed()

        return getAnswerRecursive(program, 0, 0L)
    }

    private fun getAnswerRecursive(programs: List<Int>, index: Int, resultSoFar: Long): Long {
        if (index == programs.size) return resultSoFar

        val program = programs[index]
        for (nextLastBits in 0..7) {
            val test = (resultSoFar shl 3) + nextLastBits
            if ((nextLastBits.toLong() xor 4L xor (test shr (nextLastBits xor 1))) % 8 == program.toLong()) {
                val subAnswer = getAnswerRecursive(programs, index + 1, test)
                if (subAnswer != -1L) {
                    return subAnswer
                }
            }
        }

        return -1L
    }
}