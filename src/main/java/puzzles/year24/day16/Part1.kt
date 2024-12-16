package puzzles.year24.day16

import util.Puzzle
import java.io.Serializable
import java.util.PriorityQueue

class Part1 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        val map = inputString.lines().map {it.toCharArray()}
        val startingPoint = getPoint(map, 'S')
        val endingPoint = getPoint(map, 'E')

        return getMinScoreNonRecursive(map, startingPoint, endingPoint)
    }

    fun getMinScoreNonRecursive(map: List<CharArray>, startingPoint: Pair<Int, Int>, endPoint: Pair<Int, Int>): Int {
        val queue = PriorityQueue<Pair<Node, Int>>(Comparator.comparingInt { it.second })
        val seen = mutableMapOf<Node, Int>()

        queue.add(Pair(Node(startingPoint, Direction.EAST), 0))

        while (queue.isNotEmpty()) {
            val node = queue.poll()

            val currentValue = map[node.first.pos.second][node.first.pos.first]

            if (currentValue == '#') continue

            if (node.first.pos == endPoint) return node.second

            if (seen.containsKey(node.first)) {
                if (seen[node.first]!! > node.second) {
                    seen[node.first] = node.second
                } else {
                    continue
                }
            } else {
                seen[node.first] = node.second
            }

            queue.add(Pair(Node(node.first.direction.nextPos(node.first.pos), node.first.direction), node.second + 1))
            val clockwiseDirection = node.first.direction.clockwise()
            queue.add(Pair(Node(clockwiseDirection.nextPos(node.first.pos), clockwiseDirection), node.second + 1001))
            val counterClockwiseDirection = node.first.direction.counterClockwise()
            queue.add(Pair(Node(counterClockwiseDirection.nextPos(node.first.pos), counterClockwiseDirection), node.second + 1001))
        }

        return -1
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
}

data class Node(val pos: Pair<Int, Int>, val direction: Direction): Serializable
