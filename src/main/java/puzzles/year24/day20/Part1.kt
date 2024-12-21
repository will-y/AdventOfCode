package puzzles.year24.day20

import util.Puzzle
import java.util.*

class Part1 : Puzzle<Int?> {
    val cache = mutableMapOf<Pair<Int, Int>, Int>()
    var width: Int = 0
    var height: Int = 0

    override fun getAnswer(inputString: String): Int {
        val map = inputString.lines().map {it.toCharArray()}
        val walls = mutableSetOf<Pair<Int, Int>>()
        val open = mutableSetOf<Pair<Int, Int>>()
        var startingPoint = Pair(0, 0)
        var endingPoint = Pair(0, 0)
        width = map[0].size
        height = map.size

        for (y in map.indices) {
            for (x in map[0].indices) {
                if (map[y][x] == '#') {
                    walls.add(Pair(x, y))
                } else if (map[y][x] == '.') {
                    open.add(Pair(x, y))
                } else if (map[y][x] == 'S') {
                    open.add(Pair(x, y))
                    startingPoint = Pair(x, y)
                } else if (map[y][x] == 'E') {
                    open.add(Pair(x, y))
                    endingPoint = Pair(x, y)
                }
            }
        }

        val result = calculatePath(Node(startingPoint, 0, null, null), endingPoint, walls)

        var answer = 0
        for (i in result.first.keys) {
            if (result.second - i >= 100) {
                answer += result.first[i]!!
            }
        }

        return answer
    }

    fun calculatePath(startingNode: Node, endingPoint: Pair<Int, Int>, walls: Set<Pair<Int, Int>>): Pair<Map<Int, Int>, Int> {
        val cheats = mutableMapOf<Int, Int>()
        val queue = PriorityQueue(Comparator.comparingInt<Node> { it.pathLength })
        val seen = mutableSetOf<Node>()
        queue.add(startingNode)

        while (queue.isNotEmpty()) {
            val currentNode = queue.poll()

            if (seen.contains(currentNode) || outOfBounds(currentNode.pos)) {
                seen.add(currentNode)
                continue
            }

            if (currentNode.pos == endingPoint) {
                if (currentNode.cheated != null) {
                    cheats.compute(currentNode.pathLength) { _, v -> (v ?: 0) + 1 }
                    continue
                } else {
                    return Pair(cheats, currentNode.pathLength)
                }
            }

            seen.add(currentNode)

            for (nextPosition in nextPositions(currentNode.pos)) {
                if (walls.contains(nextPosition)) {
                    if (currentNode.cheated == null) {
                        for (nextNextPosition in nextPositions(nextPosition)) {
                            if (nextNextPosition != currentNode.pos && !walls.contains(nextNextPosition) && !outOfBounds(nextNextPosition)) {
                                queue.add(Node(nextNextPosition, currentNode.pathLength + 2, Pair(currentNode.pos, nextNextPosition), currentNode))
                            }
                        }
                    }
                } else {
                    if (currentNode.prev == null || currentNode.prev.pos != nextPosition)
                        queue.add(Node(nextPosition, currentNode.pathLength + 1, currentNode.cheated, currentNode))
                }
            }
        }

        throw RuntimeException("Cannot calculate path")
    }

    fun outOfBounds(pos: Pair<Int, Int>): Boolean {
        return pos.first >= width || pos.first < 0 || pos.second >= height || pos.second < 0
    }

    fun nextPositions(pos: Pair<Int, Int>): List<Pair<Int, Int>> {
        val list = mutableListOf<Pair<Int, Int>>()

        list.add(Pair(pos.first + 1, pos.second))
        list.add(Pair(pos.first - 1, pos.second))
        list.add(Pair(pos.first, pos.second + 1))
        list.add(Pair(pos.first, pos.second - 1))

        return list
    }
}

data class Node(val pos: Pair<Int, Int>, val pathLength: Int, val cheated: Pair<Pair<Int, Int>, Pair<Int, Int>>?, val prev: Node?) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Node

        if (pos != other.pos) return false
        if (cheated != other.cheated) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pos.hashCode()
        result = 31 * result + (cheated?.hashCode() ?: 0)
        return result
    }

}

// 422 low
// 2156 High