package puzzles.year25.day8

import util.Puzzle
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

class Part2 : Puzzle<Long?> {
    override fun getAnswer(inputString: String): Long {
        val input = inputString.trim().lines().map{ str -> str.split(",").map{ it.toInt() } }
        val distances = calcDistances(input)
        val connections = Array<MutableSet<Int>> (input.size) {i -> set(i)}

        while (true) {
            val result = step(connections, distances)
            if (result.second) {
                return input[result.first.first][0].toLong() * input[result.first.second][0]
            }
        }
    }

    fun step(connections: Array<MutableSet<Int>>, distances: Array<Array<Int>>): Pair<Pair<Int, Int>, Boolean> {
        val min = min(distances, connections)
        val toAdd = HashSet<Int>()
        toAdd.addAll(connections[min.first])
        toAdd.addAll(connections[min.second])
        for (c in connections[min.first]) {
            if (c == min.first) continue
            connections[c].addAll(toAdd)
        }
        for (c in connections[min.second]) {
            if (c == min.second) continue
            connections[c].addAll(toAdd)
        }
        connections[min.first].addAll(toAdd)
        connections[min.second].addAll(toAdd)
        distances[min.first][min.second] = Int.MAX_VALUE
        distances[min.second][min.first] = Int.MAX_VALUE
//        println("Connecting $min")

        return Pair(min, connections.all { it.size == connections[0].size })
    }

    fun min(distances: Array<Array<Int>>, connections: Array<MutableSet<Int>>): Pair<Int, Int> {
        var min = distances[0][0]
        var minIndex = Pair(0, 0)

        for (i in distances.indices) {
            for (j in distances[0].indices) {
                if (distances[i][j] < min && !connections[i].contains(j)) {
                    min = distances[i][j]
                    minIndex = Pair(i, j)
                }
            }
        }

        return minIndex
    }

    // TODO: Doing double the work here
    fun calcDistances(input: List<List<Int>>): Array<Array<Int>> {
        val distances = Array<Array<Int>>(input.size) { Array(input.size) { 0 } }

        for (i in input.indices) {
            for (j in input.indices) {
                if (i == j) {
                    distances[i][j] = Int.MAX_VALUE
                } else {
                    distances[i][j] = dist(input[i], input[j])
                }
            }
        }

        return distances
    }

    fun dist(one: List<Int>, two: List<Int>): Int {
        return sqrt((one[0] - two[0]).toFloat().pow(2) + (one[1] - two[1]).toFloat().pow(2) + (one[2] - two[2]).toFloat().pow(2)).roundToInt()
    }

    fun set(i: Int): MutableSet<Int> {
        val set = HashSet<Int>(1)
        set.add(i)
        return set
    }
}
