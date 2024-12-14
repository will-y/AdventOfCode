package puzzles.year24.day14

import puzzles.year24.day9.MutablePair
import util.Puzzle

class Part2 : Puzzle<Int?> {
    val width = 101
    val height = 103

    override fun getAnswer(inputString: String): Int {
        val robots = inputString.split(System.lineSeparator()).map {it.split(" ")}.map {i -> MutablePair(getPair(i[0]), getPair(i[1]))}

        var time = 0;
        var positions: List<MutablePair<Int, Int>>
        do  {
            positions = step(robots)
            time++
        } while ((positions.size != positions.toSet().size))

        val set = positions.toSet()
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (set.contains(MutablePair(x, y))) {
                    print('#')
                } else {
                    print('.')
                }
            }
            print(System.lineSeparator())
        }

        return time;
    }

    private fun step(robots: List<MutablePair<MutablePair<Int, Int>, MutablePair<Int, Int>>>): List<MutablePair<Int, Int>> {
        return robots.map {finalPosition(it)}
    }

    private fun finalPosition(robot: MutablePair<MutablePair<Int, Int>, MutablePair<Int, Int>>): MutablePair<Int, Int> {
        var x = (robot.first.first + robot.second.first) % width
        var y = (robot.first.second + robot.second.second) % height

        if (x < 0) {
            x += width
        }

        if (y < 0) {
            y += height
        }

        robot.first.first = x
        robot.first.second = y

        return MutablePair(x, y)
    }

    private fun getPair(sIn: String): MutablePair<Int, Int> {
        val split = sIn.substring(2).split(",")
        return MutablePair(split[0].toInt(), split[1].toInt())
    }
}
