package puzzles.year24.day20

import util.Puzzle
import kotlin.math.abs
import kotlin.math.max

class Part2 : Puzzle<Int?> {
    val p1 = Part1()

    override fun getAnswer(inputString: String): Int {
        val map = inputString.lines().map {it.toCharArray()}
        val walls = mutableSetOf<Pair<Int, Int>>()
        var startingPoint = Pair(0, 0)
        var endingPoint = Pair(0, 0)

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

        val distanceMap = p1.computeDistanceMap(Node(endingPoint, 0, null), startingPoint, walls, map[0].size, map.size)

        var currentNode: Node? = distanceMap.second
        var cheat = 0
        while (currentNode != null) {
            cheat += getCheatPositions(currentNode.pos, currentNode.pathLength, distanceMap.first, map[0].size, map.size)
            currentNode = currentNode.prev
        }
        return cheat
    }

    private fun getCheatPositions(pos: Pair<Int, Int>, currentPathLength: Int, distanceMap: Map<Pair<Int, Int>, Int>, width: Int, height: Int): Int {
        var x = max(0, pos.first - 20)
        var y: Int
        var cheat = 0
        while (x <= pos.first + 20 && x < width) {
            y = max(0, pos.second - 20)
            while (y <= pos.second + 20 && y < height) {
                val nextPosition = Pair(x, y)
                val manDist = manDist(nextPosition, pos)
                if (manDist > 20) {
                    y++
                    continue
                }
                if (distanceMap.containsKey(nextPosition)) {
                    val improvement = -distanceMap[nextPosition]!! - manDist + currentPathLength

                    if (improvement >= 100) {
                        cheat++
                    }
                }
                y++
            }
            x++
        }

        return cheat
    }

    private fun manDist(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Int {
        return abs(p1.first - p2.first) + abs(p1.second - p2.second)
    }
}
