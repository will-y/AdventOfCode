package puzzles.year24.day12

import util.Puzzle
import java.io.Serializable

class Part2 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        val map = inputString.lines().map(String::toCharArray);

        val areaMap = calculateAreaMap(map)
        var result = 0
        for (area in areaMap) {
            result += calculatePrice(area.value)
        }
        return result
    }

    private fun calculatePrice(nodes: Set<Node>): Int{
        val area = nodes.size
        var sides = 0

        val edges = nodes.flatMap {node -> node.edges}.toSet()

        for (edgeType in mutableListOf('X', 'x', 'Y', 'y')) {
            val isXEdge = edgeType.lowercase() == "x"
            val typedEdges = edges.filter { e -> e.first == edgeType }
            val equalTypedEdges = typedEdges.groupBy {edge -> if (isXEdge) edge.second.second else edge.second.first}

            for (edgeList in equalTypedEdges.values) {
                var xEdgeCount = 1
                val sortedEdgeList = edgeList.sortedBy { a -> if (isXEdge) a.second.first else a.second.second }
                for (i in 0 ..< sortedEdgeList.size - 1) {
                    val currentValue = if (isXEdge) sortedEdgeList[i].second.first else sortedEdgeList[i].second.second
                    val nextValue = if (isXEdge) sortedEdgeList[i + 1].second.first else sortedEdgeList[i + 1].second.second
                    if (currentValue + 1 != nextValue) {
                        xEdgeCount++
                    }
                }
                sides += xEdgeCount
            }
        }

        return sides * area
    }

    private fun calculateAreaMap(map: List<CharArray>): Map<Pair<Char, Pair<Int, Int>>, Set<Node>> {
        val result = mutableMapOf<Pair<Char, Pair<Int, Int>>, Set<Node>>()
        val isSorted = mutableSetOf<Pair<Int, Int>>()

        for (y in map.indices) {
            for (x in map[0].indices) {
                if (!isSorted.contains(Pair(x, y))) {
                    result[Pair(map[y][x], Pair(x, y))] = categorize(isSorted, map, map[y][x], Pair(x, y))
                }
            }
        }

        return result
    }

    private fun categorize(sorted: MutableSet<Pair<Int, Int>>, map: List<CharArray>, character: Char, point: Pair<Int, Int>): Set<Node> {
        val result = mutableSetOf<Node>()

        sorted.add(point)
        val edges = mutableSetOf<Pair<Char, Pair<Int, Int>>>()
        for (p in pointsToCheck(point)) {
            if (isOutOfBounds(map, p.second) || map[p.second.second][p.second.first] != character) {
                edges.add(p)
                continue
            }

            if (sorted.contains(p.second)) {
                continue
            }

            result.addAll(categorize(sorted, map, character, p.second))
        }

        result.add(Node(character, point, edges))

        return result
    }

    private fun pointsToCheck(point: Pair<Int, Int>): Set<Pair<Char, Pair<Int, Int>>> {
        val result = mutableSetOf<Pair<Char, Pair<Int, Int>>>()
        result.add(Pair('y', Pair(point.first - 1, point.second)))
        result.add(Pair('Y', Pair(point.first + 1, point.second)))
        result.add(Pair('x', Pair(point.first, point.second - 1)))
        result.add(Pair('X', Pair(point.first, point.second + 1)))

        return result;
    }

    private fun isOutOfBounds(map: List<CharArray>, point: Pair<Int, Int>): Boolean {
        return point.first < 0 || point.first >= map[0].size || point.second < 0 || point.second >= map.size
    }
}

data class Node(val character: Char, val pos: Pair<Int, Int>, val edges: Set<Pair<Char, Pair<Int, Int>>>) : Serializable {
    override fun toString() = "$character (${pos.first}, ${pos.second}) $edges"
}
