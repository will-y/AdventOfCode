package puzzles.year24.day15

import util.Puzzle

class Part2 : Puzzle<Int?> {
    val part1 = Part1()
    override fun getAnswer(inputString: String): Int {
        val split = inputString.split(System.lineSeparator() + System.lineSeparator())
        val map = updateMap(split[0].lines().map { it.toCharArray() })
        val movements = split[1].replace(System.lineSeparator(), "").toCharArray()
//        println("Initial State:")
//        part1.printMap(map)

        var currentPosition = part1.findRobot(map)
//        var step = 0
        for (movement in movements) {
            val nextPosition = step(map, currentPosition, movement)

            map[currentPosition.second][currentPosition.first] = '.'
            map[nextPosition.second][nextPosition.first] = '@'
//            println("[${step++}] Move $movement:")
//            part1.printMap(map)

            currentPosition = nextPosition
        }

//        println("Final State:")
//        part1.printMap(map)

        return part1.calculateScore(map)
    }

    private fun step(map: List<CharArray>, pos: Pair<Int, Int>, dir: Char): Pair<Int, Int> {
        val nextPosition = part1.nextPosition(pos, dir)
        val nextCharacter = map[nextPosition.second][nextPosition.first]
        if (nextCharacter == '#') {
            return pos
        } else if (nextCharacter == '.') {
            return nextPosition
        } else if (nextCharacter == '[') {
            if (canPush(map, pos, nextPosition)) {
               doPush(map, nextPosition, dir)
               return nextPosition
            } else {
                return pos
            }
        } else if (nextCharacter == ']') {
            if (canPush(map, Pair(pos.first - 1, pos.second), Pair(nextPosition.first - 1, nextPosition.second))) {
                doPush(map, Pair(nextPosition.first - 1, nextPosition.second), dir)
                return nextPosition
            } else {
                return pos
            }
        } else {
            throw RuntimeException("Unexpected map character $nextCharacter")
        }
    }

    private fun doPush(map: List<CharArray>, pos: Pair<Int, Int>, dir: Char) {
        when (dir) {
            '>' -> {
                if (map[pos.second][pos.first + 2] == '[') {
                    doPush(map, Pair(pos.first + 2, pos.second), dir)
                } else if (map[pos.second][pos.first + 2] != '.') {
                    part1.printMap(map)
                    throw IllegalStateException("Map in unexpected state")
                }
                map[pos.second][pos.first] = '.'
                map[pos.second][pos.first + 1] = '['
                map[pos.second][pos.first + 2] = ']'
            }
            '<' -> {
                if (map[pos.second][pos.first - 1] == ']') {
                    doPush(map, Pair(pos.first - 2, pos.second), dir)
                } else if (map[pos.second][pos.first - 1] != '.') {
                    part1.printMap(map)
                    throw IllegalStateException("Map in unexpected state")
                }
                map[pos.second][pos.first - 1] = '['
                map[pos.second][pos.first] = ']'
                map[pos.second][pos.first + 1] = '.'
            }
            'v' -> {
                val bottomLeft = map[pos.second + 1][pos.first]
                val bottomRight = map[pos.second + 1][pos.first + 1]

                if (bottomLeft == '[') {
                    doPush(map, Pair(pos.first, pos.second + 1), dir)
                } else if (bottomLeft == ']') {
                    doPush(map, Pair(pos.first - 1, pos.second + 1), dir)
                }

                if (bottomRight == '[') {
                    doPush(map, Pair(pos.first + 1, pos.second + 1), dir)
                }

                map[pos.second][pos.first] = '.'
                map[pos.second][pos.first + 1] = '.'
                map[pos.second + 1][pos.first] = '['
                map[pos.second + 1][pos.first + 1] = ']'
            }
            '^' -> {
                val topLeft = map[pos.second - 1][pos.first]
                val topRight = map[pos.second - 1][pos.first + 1]

                if (topLeft == '[') {
                    doPush(map, Pair(pos.first, pos.second - 1), dir)
                } else if (topLeft == ']') {
                    doPush(map, Pair(pos.first - 1, pos.second - 1), dir)
                }

                if (topRight == '[') {
                    doPush(map, Pair(pos.first + 1, pos.second - 1), dir)
                }

                map[pos.second][pos.first] = '.'
                map[pos.second][pos.first + 1] = '.'
                map[pos.second - 1][pos.first] = '['
                map[pos.second - 1][pos.first + 1] = ']'
            }
        }
    }

    private fun canPush(map: List<CharArray>, pos: Pair<Int, Int>, nextPosition: Pair<Int, Int>): Boolean {
        if (pos.first == nextPosition.first) {
            // Moving along y-axis
            val delta = nextPosition.second - pos.second
            return canPushBox(map, nextPosition, Pair(0, delta))
        } else {
            // Moving along x-axis
            val delta = nextPosition.first - pos.first
            return canPushBox(map, nextPosition, Pair(delta, 0))
        }
    }

    private fun canPushBox(map: List<CharArray>, boxPos: Pair<Int, Int>, delta: Pair<Int, Int>): Boolean {
        // box pos is always the position of the '['
        if (delta.second == 0) {
            // Horizontal push
            val right = delta.first > 0
            val positionToCheck = if (right) Pair(boxPos.first + 2, boxPos.second) else Pair(boxPos.first - 1, boxPos.second)
            return map[positionToCheck.second][positionToCheck.first] == '.'
                    || (map[positionToCheck.second][positionToCheck.first] == '[' && canPushBox(map, Pair(positionToCheck.first, positionToCheck.second), delta))
                    || (map[positionToCheck.second][positionToCheck.first] == ']' && canPushBox(map, Pair(positionToCheck.first - 1, positionToCheck.second), delta))
        } else {
            // Vertical push
            val topLeft = map[boxPos.second + delta.second][boxPos.first]
            val topRight = map[boxPos.second + delta.second][boxPos.first + 1]

            if (topLeft == '#' || topRight == '#') {
                return false
            }

            val topLeftGood = topLeft == '.'
                    || (topLeft == '[' && canPushBox(map, Pair(boxPos.first, boxPos.second + delta.second), delta))
                    || (topLeft == ']' && canPushBox(map, Pair(boxPos.first - 1, boxPos.second + delta.second), delta))
            val topRightGood = topRight == '.'
                    || (topRight == '[' && canPushBox(map, Pair(boxPos.first + 1, boxPos.second + delta.second), delta))
                    || (topRight == ']' && topLeftGood)
            return topLeftGood && topRightGood
        }
    }

    private fun updateMap(map: List<CharArray>): List<CharArray> {
        val newMap = mutableListOf<CharArray>()
        for (row in map) {
            val newRow = StringBuilder()
            for (c in row) {
                when (c) {
                    '#' -> newRow.append("##")
                    'O' -> newRow.append("[]")
                    '.' -> newRow.append("..")
                    '@' -> newRow.append("@.")
                }
            }
            newMap.add(newRow.toString().toCharArray())
        }

        return newMap
    }
}
