package puzzles.year24.day15

import util.Puzzle

class Part1 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        val split = inputString.split(System.lineSeparator() + System.lineSeparator())
        val map = split[0].lines().map { it.toCharArray() }
        val movements = split[1].replace(System.lineSeparator(), "").toCharArray()
        printMap(map)

        var currentPosition = findRobot(map)
        for (movement in movements) {
            val nextPosition = step(map, currentPosition, movement)

            map[currentPosition.second][currentPosition.first] = '.'
            map[nextPosition.second][nextPosition.first] = '@'
//            printMap(map)

            currentPosition = nextPosition
        }

        printMap(map)

        return calculateScore(map);
    }

    fun calculateScore(map: List<CharArray>): Int {
        var result = 0
        for (y in map.indices) {
            for (x in map[0].indices) {
                if (map[y][x] == 'O') {
                    result += (100 * y + x)
                }
            }
        }

        return result
    }

    fun printMap(map: List<CharArray>) {
        for (row in map) {
            println(row.joinToString(""))
        }
        println()
    }

    private fun step(map: List<CharArray>, pos: Pair<Int, Int>, dir: Char): Pair<Int, Int> {
        val nextPosition = nextPosition(pos, dir)
        val nextCharacter = map[nextPosition.second][nextPosition.first]
        if (nextCharacter == '#') {
            return pos
        } else if (nextCharacter == '.') {
            return nextPosition
        } else if (nextCharacter == 'O') {
            return if (canPush(map, pos, nextPosition)) {
                nextPosition
            } else {
                pos
            }
        } else {
            throw RuntimeException("Unexpected map character $nextCharacter")
        }

    }

    private fun canPush(map: List<CharArray>, pos: Pair<Int, Int>, nextPosition: Pair<Int, Int>): Boolean {
        if (pos.first == nextPosition.first) {
            // Moving along y-axis
            val delta = nextPosition.second - pos.second
            var nextChar = map[nextPosition.second][nextPosition.first]
            var i = 0
            while (nextChar == 'O') {
                i += delta
                nextChar = map[nextPosition.second + i][nextPosition.first]

            }

            if (nextChar == '.') {
                map[nextPosition.second + i][nextPosition.first] = 'O'
                return true
            } else {
                return false
            }
        } else {
            // Moving along x-axis
            val delta = nextPosition.first - pos.first
            var nextChar = map[nextPosition.second][nextPosition.first]
            var i = 0
            while (nextChar == 'O') {
                i += delta
                nextChar = map[nextPosition.second][nextPosition.first + i]
            }

            if (nextChar == '.') {
                map[nextPosition.second ][nextPosition.first + i] = 'O'
                return true
            } else {
                return false
            }
        }
    }

    fun findRobot(map: List<CharArray>): Pair<Int, Int> {
        for (y in map.indices) {
            for (x in map[0].indices) {
                if (map[y][x] == '@') {
                    return Pair(x, y)
                }
            }
        }

        throw RuntimeException("No robot found")
    }

    fun nextPosition(pos: Pair<Int, Int>, dir: Char): Pair<Int, Int> {
        return when (dir) {
            '^' -> Pair(pos.first, pos.second - 1)
            '>' -> Pair(pos.first + 1, pos.second)
            'v' -> Pair(pos.first, pos.second + 1)
            '<' -> Pair(pos.first - 1, pos.second)
            else -> pos
        }
    }
}
