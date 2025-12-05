package puzzles.year25.day4

import util.Puzzle

class Part2 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        val map = inputString.trim().lines().map{ it.toCharArray()}.map { a -> Array(a.size) { i -> a[i] }}

        var count = 0

        var lastCount = -1
        while (lastCount != 0) {
            lastCount = getRemovedAndRemove(map)
            count += lastCount
        }

        return count
    }

    fun getRemovedAndRemove(map: List<Array<Char>>): Int {
        var count = 0

        for (y in map.indices) {
            for (x in map[0].indices) {
                if (map[y][x] == '@' && canPickup(map, x, y)) {
                    count++
                    map[y][x] = '.'
                }
            }
        }

        return count
    }

    fun canPickup(map: List<Array<Char>>, x: Int, y: Int): Boolean {
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

    fun getValue(map: List<Array<Char>>, x: Int, y: Int): Int {
        if (x < 0 || x >= map[0].size || y < 0 || y >= map.size) {
            return 0
        } else if (map[y][x] == '@') {
            return 1
        }

        return 0
    }
}
