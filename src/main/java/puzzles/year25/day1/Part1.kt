package puzzles.year25.day1

import util.Puzzle

class Part1 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        var result = 0
        var value = 50;

        for (s in inputString.lines()) {
            if (s.isEmpty()) continue
            val dir = if (s.get(0) == 'R')  1 else -1
            val dist = s.substring(1).toInt()

            value = (value + (dist * dir)).mod(100)
            println(value)

            if (value == 0) {
                result ++
            }
        }
        return result
    }
}
