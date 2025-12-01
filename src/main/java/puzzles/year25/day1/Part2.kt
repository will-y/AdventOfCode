package puzzles.year25.day1

import util.Puzzle
import kotlin.math.absoluteValue

class Part2 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        var result = 0
        var value = 50

        for (s in inputString.lines()) {
//            println("Value $value")
            if (s.isEmpty()) continue
            val dir = if (s.get(0) == 'R') 1 else -1
            val dist = s.substring(1).toInt()

            var delta = (dist * dir)

            if (value == 0 && delta < 0) {
                result --
//                println("Subtracting 1")
            }
            value = value + delta

//            println("Direction: $s, Raw value: $value, delta: $delta")
            if (value >= 100) {
                result += value.absoluteValue / 100
//                println("Adding " + value.absoluteValue / 100)
            } else if (value <= 0) {
                result += 1 + value.absoluteValue / 100
//                println("Adding " + (value.absoluteValue / 100 + 1))
            }
            value = value.mod(100)
        }
        return result
    }

    // 6542 HIGH
    // 6414 HIGH
}
