package puzzles.year25.day4

import util.Puzzle
import kotlin.CharArray
import kotlin.Int
import kotlin.collections.List

class Part1 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        val map = inputString.trim().lines().map{ it.toCharArray()}
        var count = 0

        for (y in map.indices) {
            for (x in map[0].indices) {
                if (map[y][x] == '@' && canPickup(map, x, y)) {
                    count++
                }
            }
        }

        return count
    }

    fun canPickup(map: List<CharArray>, x: Int, y: Int): Boolean {
        var count = 0
        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) continue

                if (getValue(map, x + i, y + j) == 1) {
                    if (++count >= 4) return false
                }
            }
        }

        return true
    }

    fun getValue(map: List<CharArray>, x: Int, y: Int): Int {
        if (x < 0 || x >= map[0].size || y < 0 || y >= map.size) {
            return 0
        } else if (map[y][x] == '@') {
            return 1
        }

        return 0
    }
}
