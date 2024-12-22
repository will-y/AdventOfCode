package puzzles.year24.day21

import util.Puzzle

class Part1 : Puzzle<Int?> {
    private val numPadPosMap = mapOf(
        Pair('A', Pair(2, 3)),
        Pair('3', Pair(2, 2)),
        Pair('6', Pair(2, 1)),
        Pair('9', Pair(2, 0)),
        Pair('0', Pair(1, 3)),
        Pair('2', Pair(1, 2)),
        Pair('5', Pair(1, 1)),
        Pair('8', Pair(1, 0)),
        Pair('1', Pair(0, 2)),
        Pair('4', Pair(0, 1)),
        Pair('7', Pair(0, 0)),
    )

    private val numPadPaths = populateNumPadMap()

    private val dirPadPosMap = mapOf(
        Pair('^', Pair(1, 0)),
        Pair('A', Pair(2, 0)),
        Pair('<', Pair(0, 1)),
        Pair('v', Pair(1, 1)),
        Pair('>', Pair(2, 1))
    )

    private val dirPadPaths = populateDirPadMap()

    override fun getAnswer(inputString: String): Int {
        val codes = inputString.lines().map {it.toCharArray()}
        var result = 0

        for (code in codes) {
            result+= getMinPathLength(code) * numFromString(code)
        }

        return result
    }

    fun numFromString(code: CharArray): Int {
        return code.joinToString("").substring(0, 3).toInt()
    }

    fun getMinPathLength(code: CharArray): Int {
        // Num pad min inputs
        val numPadPaths = typeNumbers(code)

        val firstIteration = iterate(numPadPaths)
        val secondIteration = iterate(firstIteration)

        return secondIteration[0].length
    }

    fun iterate(paths: List<String>): List<String> {
        val firstIteration: MutableList<String> = mutableListOf()

        for (path in paths) {
            var newPaths = dirPadPaths[Pair('A', path[0])]!!
            for (i in 0..<path.length - 1) {
                newPaths = newPaths * dirPadPaths[Pair(path[i], path[i + 1])]!!
            }
            firstIteration.addAll(newPaths)
        }

        val resultMap = firstIteration.groupBy { it.length }
        return resultMap[resultMap.keys.min()]!!
    }

    fun typeNumbers(numbers: CharArray): List<String> {
        var lastCharacter = 'A'
        var paths: List<String> = mutableListOf("")

        for (i in numbers) {
            paths = paths * numPadPaths[Pair(lastCharacter, i)]!!
            lastCharacter = i
        }

        return paths
    }

    fun populateDirPadMap(): Map<Pair<Char, Char>, List<String>> {
        val result = mutableMapOf<Pair<Char, Char>, List<String>>()
        for (c1 in dirPadPosMap.keys) {
            for (c2 in dirPadPosMap.keys) {
                if (c1 != c2) {
                    val paths = getPaths(c1, c2, dirPadPosMap).filter {s -> isValidPath(dirPadPosMap[c1]!!, s, Pair(0, 0))}.map { s -> s + 'A'}
                    result[Pair(c1, c2)] = paths
                } else {
                    result[Pair(c1, c2)] = listOf("A")
                }
            }
        }

        return result
    }

    fun populateNumPadMap(): Map<Pair<Char, Char>, List<String>> {
        val result = mutableMapOf<Pair<Char, Char>, List<String>>()
        for (c1 in numPadPosMap.keys) {
            for (c2 in numPadPosMap.keys) {
                if (c1 != c2) {
                    val paths = getPaths(c1, c2, numPadPosMap).filter {s -> isValidPath(numPadPosMap[c1]!!, s, Pair(0, 3))}.map { s -> s + 'A'}
                    result[Pair(c1, c2)] = paths
                }
            }
        }

        return result
    }

    fun isValidPath(p: Pair<Int, Int>, pathString: String, badPoint: Pair<Int, Int>): Boolean {
        val path = mutableSetOf<Pair<Int, Int>>()
        path.add(p)
        var lastPoint = p

        for (c in pathString.toCharArray()) {
            when (c) {
                '^' -> {
                    val newPoint = Pair(lastPoint.first, lastPoint.second - 1)
                    path.add(newPoint)
                    lastPoint = newPoint
                }
                'v' -> {
                    val newPoint = Pair(lastPoint.first, lastPoint.second + 1)
                    path.add(newPoint)
                    lastPoint = newPoint
                }
                '>' -> {
                    val newPoint = Pair(lastPoint.first + 1, lastPoint.second)
                    path.add(newPoint)
                    lastPoint = newPoint
                }
                '<' -> {
                    val newPoint = Pair(lastPoint.first - 1, lastPoint.second)
                    path.add(newPoint)
                    lastPoint = newPoint
                }
            }
            if (lastPoint == badPoint) return false
        }

        return true
    }

    fun getPaths(c1: Char, c2: Char, map: Map<Char, Pair<Int, Int>>): List<String> {
        return getPaths(map[c1]!!, map[c2]!!)
    }

    fun getPaths(p1: Pair<Int, Int>, p2: Pair<Int, Int>): List<String> {
        val x = p2.first - p1.first
        val y = p2.second - p1.second

        if (x == 0 && y == 0) {
            return listOf("")
        } else if (x == 0 && y > 0) {
            return listOf('v' * y)
        } else if (x == 0) {
            return listOf('^' * -y)
        } else if (x > 0 && y == 0) {
            return listOf('>' * x)
        } else if (x > 0 && y > 0) {
            return getPaths('>', x, 'v', y)
        } else if (x > 0) {
            return getPaths('>', x, '^', -y)
        } else if (y == 0) {
            return listOf('<' * -x)
        } else if ( y > 0) {
            return getPaths('<', -x, 'v', y)
        } else {
            return getPaths('<', -x, '^', -y)
        }
    }

    fun getPaths(c1: Char, x: Int, c2: Char, y: Int, s: String = ""): List<String> {
        // x c1s and y c2s, need every possible combination
        if (x == 0) {
            return listOf(s + c2 * y)
        }

        if (y == 0) {
            return listOf(s + c1 * x)
        }

        return getPaths(c1, x - 1, c2, y, s + c1) + getPaths(c1, x, c2, y - 1, s + c2)
    }
}

private operator fun List<String>.times(es: List<String>): List<String> {
    val result = mutableListOf<String>()

    for (s1 in this) {
        for (s2 in es) {
            result.add(s1 + s2)
        }
    }

    return result
}

private operator fun Char.times(i: Int): String {
    return this.toString().repeat(i)
}
