package puzzles.year24.day18

import util.Puzzle
import java.util.*
import kotlin.Comparator

class Part2 : Puzzle<String?> {
    val size = 70
    val numToSim = 1024

    override fun getAnswer(inputString: String): String {
        val pairs = inputString.lines().map {it.split(",")}.map {Pair(it[0].toInt(), it[1].toInt())}


        var simNumber = numToSim;
        var path = getPath(pairs, simNumber)

        while (true) {
            val currentPoint = pairs[simNumber]

            if (path.contains(currentPoint)) {
                path = getPath(pairs, simNumber + 1)

                if (path.isEmpty()) {
                    return "${currentPoint.first},${currentPoint.second}"
                }
            }

            simNumber++;
        }
    }

    fun getPath(pairs: List<Pair<Int, Int>>, numToCalc: Int): Set<Pair<Int, Int>> {
        val points = pairs.subList(0, numToCalc).toSet()
        val queue = PriorityQueue(Comparator.comparingInt<Pair<Pair<Int, Int>, Set<Pair<Int, Int>>>?> { it.second.size })
        queue.add(Pair(Pair(0, 0), mutableSetOf()))

        val seen = mutableMapOf<Pair<Int, Int>, Int>()

        while (queue.isNotEmpty()) {
            val current = queue.poll()
            val point = current.first
            if (points.contains(point) || point.first < 0 || point.second < 0 || point.first > size || point.second > size) {
                continue
            }

            if (seen.containsKey(point)) {
                if (seen[point]!! > current.second.size) {
                    seen[point] = current.second.size
                } else {
                    continue
                }
            } else {
                seen[point] = current.second.size
            }

            if (point.first == size && point.second == size) {
                return current.second;
            }

            queue.add(Pair(Pair(point.first - 1, point.second), add(current.second, current.first)))
            queue.add(Pair(Pair(point.first + 1, point.second), add(current.second, current.first)))
            queue.add(Pair(Pair(point.first, point.second - 1), add(current.second, current.first)))
            queue.add(Pair(Pair(point.first, point.second + 1), add(current.second, current.first)))
        }

        return setOf()
    }

    private fun add(set: Set<Pair<Int, Int>>, point: Pair<Int, Int>): Set<Pair<Int, Int>> {
        val newSet = mutableSetOf<Pair<Int, Int>>()
        newSet.addAll(set)
        newSet.add(point)
        return newSet
    }
}
