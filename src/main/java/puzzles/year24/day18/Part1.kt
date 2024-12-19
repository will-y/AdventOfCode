package puzzles.year24.day18

import util.Puzzle
import java.util.*
import kotlin.Comparator

class Part1 : Puzzle<Int?> {
    val size = 70
    val numToSim = 1024

    override fun getAnswer(inputString: String): Int {
        val pairs = inputString.lines().map {it.split(",")}.map {Pair(it[0].toInt(), it[1].toInt())}.subList(0, numToSim).toSet()

        val queue = PriorityQueue(Comparator.comparingInt<Pair<Pair<Int, Int>, Int>?> { it.second })
        queue.add(Pair(Pair(0, 0), 0))

        val seen = mutableMapOf<Pair<Int, Int>, Int>()

        while (true) {
            val current = queue.poll()
            val point = current.first
            if (pairs.contains(point) || point.first < 0 || point.second < 0 || point.first > size || point.second > size) {
                continue
            }

            if (seen.containsKey(point)) {
                if (seen[point]!! > current.second) {
                    seen[point] = current.second
                } else {
                    continue
                }
            } else {
                seen[point] = current.second
            }

            if (point.first == size && point.second == size) {
                return current.second;
            }

            queue.add(Pair(Pair(point.first - 1, point.second), current.second + 1))
            queue.add(Pair(Pair(point.first + 1, point.second), current.second + 1))
            queue.add(Pair(Pair(point.first, point.second - 1), current.second + 1))
            queue.add(Pair(Pair(point.first, point.second + 1), current.second + 1))
        }
    }
}
