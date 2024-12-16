package puzzles.year24.day16

import util.Puzzle
import java.util.*
import kotlin.Comparator

class Part2 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        val map = inputString.lines().map {it.toCharArray()}
        val startingPoint = getPoint(map, 'S')
        val endingPoint = getPoint(map, 'E')

        return getMinScoreNonRecursive(map, startingPoint, endingPoint)
    }

    fun getMinScoreNonRecursive(map: List<CharArray>, startingPoint: Pair<Int, Int>, endPoint: Pair<Int, Int>): Int {
        val queue = PriorityQueue<Pair<Node, Pair<Int, MutableList<Node>>>>(Comparator.comparingInt { it.second.first })
        val seen = mutableMapOf<Node, Int>()
        var bestScore = -1
        val solutionSet: MutableSet<Pair<Int, Int>> = mutableSetOf()

        queue.add(Pair(Node(startingPoint, Direction.EAST), Pair(0, mutableListOf())))

        while (queue.isNotEmpty()) {
            val node = queue.poll()

            val currentValue = map[node.first.pos.second][node.first.pos.first]

            if (currentValue == '#') continue

            if (node.first.pos == endPoint) {
                // Add to solution list
                if (bestScore == -1) {
                    bestScore = node.second.first
                    solutionSet.addAll(node.second.second.map {it.pos})
                } else if (node.second.first == bestScore) {
                    solutionSet.addAll(node.second.second.map {it.pos})
                } else {
                    break
                }
                continue
            }

            if (seen.containsKey(node.first)) {
                if (seen[node.first]!! >= node.second.first) {
                    seen[node.first] = node.second.first
                } else {
                    continue
                }
            } else {
                seen[node.first] = node.second.first
            }

            queue.add(Pair(Node(node.first.direction.nextPos(node.first.pos), node.first.direction), Pair(node.second.first + 1, add(node.second.second, node.first))))
            val clockwiseDirection = node.first.direction.clockwise()
            queue.add(Pair(Node(clockwiseDirection.nextPos(node.first.pos), clockwiseDirection), Pair(node.second.first + 1001, add(node.second.second, node.first))))
            val counterClockwiseDirection = node.first.direction.counterClockwise()
            queue.add(Pair(Node(counterClockwiseDirection.nextPos(node.first.pos), counterClockwiseDirection), Pair(node.second.first + 1001, add(node.second.second, node.first))))
        }

        return solutionSet.size + 1
    }

    fun getPoint(map: List<CharArray>, value: Char): Pair<Int, Int> {
        for (y in map.indices) {
            for (x in map[0].indices) {
                if (map[y][x] == value) {
                    return Pair(x, y)
                }
            }
        }

        throw RuntimeException("$value not found in map")
    }

    fun <T> add(list: MutableList<T>, value: T): MutableList<T> {
        val newList = ArrayList(list)
        newList.add(value)
        return newList
    }
}