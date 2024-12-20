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

        val originalPath = calculatePath(Node(endingPoint, 0, null), startingPoint, walls)

        var currentNode: Node? = originalPath

        while (currentNode != null) {
            cache[currentNode.pos] = currentNode.pathLength
            currentNode = currentNode.prev;
        }

        val cheats = mutableMapOf<Int, Int>()
        currentNode = originalPath

        while (currentNode != null) {
            for (pos in nextPositions(currentNode.pos)) {
                if (walls.contains(pos)) {
                    walls.remove(pos)
                    val timeSave = originalPath.pathLength - calculatePath(Node(currentNode.pos, originalPath.pathLength - currentNode.pathLength, null), endingPoint, walls).pathLength
                    if (timeSave >= 100) {
                        cheats.compute(timeSave) { _, v -> (v ?: 0) + 1 }
                    }
                    walls.add(pos)
                }
            }

            currentNode = currentNode.prev
        }

        return cheats.values.sum()
    }

    fun calculatePath(startingNode: Node, endingPoint: Pair<Int, Int>, walls: Set<Pair<Int, Int>>): Node {
        val queue = PriorityQueue(Comparator.comparingInt<Node> { it.pathLength })
        val seen = mutableSetOf<Pair<Int, Int>>()
        queue.add(startingNode)

        while (queue.isNotEmpty()) {
            val currentNode = queue.poll()

            if (currentNode.pos == endingPoint) {
                return currentNode
            }

            if (seen.contains(currentNode.pos) || walls.contains(currentNode.pos) || outOfBounds(currentNode.pos)) {
                seen.add(currentNode.pos)
                continue
            } else {
                seen.add(currentNode.pos)
                for (nextPosition in nextPositions(currentNode.pos)) {
                    if (cache.containsKey(nextPosition)) {
                        queue.add(Node(endingPoint, cache[nextPosition]!! + currentNode.pathLength + 1, currentNode))
                    } else {
                        queue.add(Node(nextPosition, currentNode.pathLength + 1, currentNode))
                    }
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

data class Node(val pos: Pair<Int, Int>, val pathLength: Int, val prev: Node?) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Node

        if (pos != other.pos) return false
        if (pathLength != other.pathLength) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pos.hashCode()
        result = 31 * result + pathLength
        return result
    }
}

// 422 low
// 2156 High