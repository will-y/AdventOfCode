package puzzles.year24.day20

import util.Puzzle
import java.io.Serializable
import java.util.*

class Part1 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        val map = inputString.lines().map {it.toCharArray()}
        val walls = mutableSetOf<Pair<Int, Int>>()
        var startingPoint = Pair(0, 0)
        var endingPoint = Pair(0, 0)
        val width = map[0].size
        val height = map.size

        for (y in map.indices) {
            for (x in map[0].indices) {
                if (map[y][x] == '#') {
                    walls.add(Pair(x, y))
                } else if (map[y][x] == 'S') {
                    startingPoint = Pair(x, y)
                } else if (map[y][x] == 'E') {
                    endingPoint = Pair(x, y)
                }
            }
        }
        val distanceMap = computeDistanceMap(Node(endingPoint, 0, null), startingPoint, walls, width, height)

        var currentNode: Node? = distanceMap.second
        var cheat = 0
        while (currentNode != null) {
            for (nextPosition in nextPositions(currentNode.pos)) {
                for (nextNextPosition in nextPositions(nextPosition)) {
                    if (nextNextPosition == currentNode.pos) continue

                    if (distanceMap.first.containsKey(nextNextPosition)) {
                        val improvement = - distanceMap.first[nextNextPosition]!! - 2 + currentNode.pathLength

                        if (improvement >= 100) {
                            cheat++
                        }
                    }
                }
            }
            currentNode = currentNode.prev
        }
        return cheat
    }

    fun computeDistanceMap(startingNode: Node, endingPoint: Pair<Int, Int>, walls: Set<Pair<Int, Int>>, width: Int, height: Int): Pair<Map<Pair<Int, Int>, Int>, Node> {
        val result = mutableMapOf<Pair<Int, Int>, Int>()
        val queue = PriorityQueue(Comparator.comparingInt<Node> { it.pathLength })
        val seen = mutableSetOf<Pair<Int, Int>>()
        queue.add(startingNode)

        while (queue.isNotEmpty()) {
            val currentNode = queue.poll()

            if (seen.contains(currentNode.pos) || walls.contains(currentNode.pos) || outOfBounds(currentNode.pos, width, height)) {
                seen.add(currentNode.pos)
                continue
            }

            result[currentNode.pos] = currentNode.pathLength

            if (currentNode.pos == endingPoint) {
                return Pair(result, currentNode)
            }

            seen.add(currentNode.pos)

            for (nextPosition in nextPositions(currentNode.pos)) {
                if (!seen.contains(nextPosition)) {
                    queue.add(Node(nextPosition, currentNode.pathLength + 1, currentNode))
                }
            }
        }

        throw RuntimeException("error")
    }

    fun outOfBounds(pos: Pair<Int, Int>, width: Int, height: Int): Boolean {
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

data class Node(val pos: Pair<Int, Int>, val pathLength: Int, val prev: Node?): Serializable
