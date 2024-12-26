package puzzles.year24.day25

import util.Puzzle

class Part1 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        val both = inputString.split(System.lineSeparator() + System.lineSeparator())
        val locks = mutableListOf<List<Int>>()
        val keys = mutableListOf<List<Int>>()

        for (x in both) {
            val result = parseSegment(x.lines().toMutableList())
            if (result.second) {
                locks.add(result.first)
            } else {
                keys.add(result.first)
            }
        }

        var result = 0

        for (lock in locks) {
            for (key in keys) {
                if (fits(lock, key)) {
                    result++
                }
            }
        }
        return result
    }

    fun fits(lock: List<Int>, key: List<Int>): Boolean {
        for (i in lock.indices) {
            if (lock[i] + key[i] > 5) {
                return false
            }
        }

        return true
    }

    fun parseSegment(lock: MutableList<String>): Pair<List<Int>, Boolean> {
        val isLock = lock[0] == "#####"
        val result = mutableListOf(0, 0, 0, 0, 0)

        if (isLock) {
            lock.removeAt(0)
        } else {
            lock.removeLast()
        }

        for (line in lock) {
            for (i in line.indices) {
                if (line[i] == '#') {
                    result[i]++
                }
            }
        }

        return Pair(result, isLock)
    }
}
