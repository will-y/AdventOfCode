package puzzles.year24.day11

import util.Puzzle
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.roundToLong

class Part2 : Puzzle<Long?> {
    // Map of value => how many of that number of stones there are
    override fun getAnswer(inputString: String): Long {
        var stoneMap = inputString.split(" ").associate { i -> Pair(i.toLong(), 1L) }.toMutableMap()

        for (i in 0..<75) {
            stoneMap = step(stoneMap)
        }

        return stoneMap.map { it.value }.sum();
    }

    private fun step(stones: MutableMap<Long, Long>): MutableMap<Long, Long> {
        val newMap = HashMap<Long, Long>()

        for (element in stones) {
            val next = next(element.key)
            newMap.compute(next.first) { _, v -> (v ?: 0) + element.value }

            if (next.second != -1L) {
                newMap.compute(next.second) { _, v -> (v ?: 0) + element.value }
            }
        }

        return newMap;
    }

    private fun next(stone: Long): Pair<Long, Long> {
        if (stone == 0L) {
            return Pair(1L, -1);
        } else {
            val digits = ((floor(log10(stone.toDouble()))) + 1).toInt();
            if (digits % 2 == 0) {
                val power =  10.0.pow(digits / 2).toLong()
                val first = (stone / power)
                val second = (((stone / power.toDouble()) - first) * power).roundToLong()
                return Pair(first, second)
            } else {
                return Pair((stone * 2024), -1L)
            }
        }
    }
}