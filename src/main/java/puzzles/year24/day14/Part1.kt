package puzzles.year24.day14

import util.Puzzle
import kotlin.math.abs

class Part1 : Puzzle<Int?> {
    val width = 101
    val height = 103
    val time = 100

    override fun getAnswer(inputString: String): Int {
        val robots = inputString.split(System.lineSeparator()).map {it.split(" ")}.map {i -> Pair(getPair(i[0]), getPair(i[1]))}
        val positions = getPositions(robots)

        var q1 = 0
        var q2 = 0
        var q3 = 0
        var q4 = 0
        val xSplit = width / 2
        val ySplit = height / 2
        for (position in positions) {
            if (position.first < xSplit && position.second < ySplit) {
                q1++
            } else if (position.first < xSplit && position.second > ySplit) {
                q2++
            } else if (position.first > xSplit && position.second > ySplit) {
                q3++
            } else if (position.first > xSplit && position.second < ySplit) {
                q4++
            }
        }
        return q1 * q2 * q3 * q4;
    }

    private fun getPositions(robots: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): List<Pair<Int, Int>> {
        return robots.map {finalPosition(it)}
    }

    private fun finalPosition(robot: Pair<Pair<Int, Int>, Pair<Int, Int>>): Pair<Int, Int> {
        var x = (robot.first.first + robot.second.first * time) % width
        var y = (robot.first.second + robot.second.second * time) % height

        if (x < 0) {
            x += width
        }

        if (y < 0) {
            y += height
        }

        return Pair(x, y)
    }

    private fun getPair(sIn: String): Pair<Int, Int> {
        val split = sIn.substring(2).split(",")
        return Pair(split[0].toInt(), split[1].toInt())
    }
}
