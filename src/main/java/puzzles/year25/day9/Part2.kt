package puzzles.year25.day9

import util.Puzzle
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

class Part2 : Puzzle<Long?> {
    override fun getAnswer(inputString: String): Long {
        val input = inputString.trim().lines().map{ s -> s.split(",").map { it.toLong() }}.toMutableList()
        input.add(input[0])
        val lineInput = doubleInputs(input)
        lineInput.add(lineInput[0])

        val lines = genLines(lineInput)
        val hLines = lines.first
        val vLines = lines.second

        var allPairs: MutableList<Pair<List<Long>, List<Long>>> = ArrayList()

        for (i in input) {
            for (j in input) {
                if (i != j) {
                    allPairs.add(i to j)
                }
            }
        }

        allPairs = allPairs.sortedBy { area(it.first, it.second) }.reversed().toMutableList()

        for (p in allPairs) {
            if (isValid(p.first, p.second, hLines, vLines)) {
                println("Valid Area Found: $p")
                return area(p.first, p.second)
            }
        }

        return 0
    }

    fun area(x1: List<Long>, x2: List<Long>): Long {
        return ((x1[0] - x2[0]).absoluteValue + 1) * ((x1[1] - x2[1]).absoluteValue + 1)
    }

    fun isValid(x1: List<Long>, x2: List<Long>, hLines: List<Line>, vLines: List<Line>): Boolean {
        val yMin = min(x1[1], x2[1]) * 2
        val yMax = max(x1[1], x2[1]) * 2
        val xMin = min(x1[0], x2[0]) * 2
        val xMax = max(x1[0], x2[0]) * 2

        return isHorizontalLineValid(hLineOf(xMin, xMax, yMin), hLines, vLines)
                && isHorizontalLineValid(hLineOf(xMin, xMax, yMax), hLines, vLines)
                && isVerticalLineValid(vLineOf(xMin, yMin, yMax), hLines, vLines)
                && isVerticalLineValid(vLineOf(xMax, yMin, yMax), hLines, vLines)
    }

    fun hLineOf(x1: Long, x2: Long, y: Long): Line {
        val p0 = ArrayList<Long>()
        val p1 = ArrayList<Long>()
        p0.add(x1)
        p1.add(x2)
        p0.add(y)
        p1.add(y)

        return Line(p0, p1)
    }

    fun vLineOf(x: Long, y1: Long, y2: Long): Line {
        val p0 = ArrayList<Long>()
        val p1 = ArrayList<Long>()
        p0.add(x)
        p1.add(x)
        p0.add(y1)
        p1.add(y2)

        return Line(p0, p1)
    }

    fun isHorizontalLineValid(inputHLine: Line, hLines: List<Line>, vLines: List<Line>): Boolean {
        val yIn = inputHLine.p1[1]
        for (l in vLines) {
            val x = l.p1[0]
            if (l.p1[1] < yIn && yIn < l.p2[1] && inputHLine.p1[0] < x && x < inputHLine.p2[0]) {
                return false
            }
        }

        return true
    }

    fun isVerticalLineValid(inputVLine: Line, hLines: List<Line>, vLines: List<Line>): Boolean {
        val xIn = inputVLine.p1[0]
        for (l in hLines) {
            val y = l.p1[1]
            if (l.p1[0] < xIn && xIn < l.p2[0] && inputVLine.p1[1] < y && y < inputVLine.p2[1]) {
                return false
            }
        }

        return true
    }

    fun doubleInputs(input: List<List<Long>>): MutableList<List<Long>> {
        var lastDir = 'L' // Test should be U
        var currentDir: Char

        val result = ArrayList<List<Long>>()

        for (i in 0..input.lastIndex - 1) {
            val p1 = input[i]
            val p2 = input[i + 1]

            if (p1[0] == p2[0]) {
                // Vertical Line
                if (p1[1] > p2[1]) {
                    currentDir = 'U'
                } else {
                    currentDir = 'D'
                }

            } else {
                // Horizontal Line
                if (p1[0] > p2[0]) {
                    currentDir = 'L'
                } else {
                    currentDir = 'R'
                }
            }

            result.add(dub(p1, lastDir, currentDir))
            lastDir = currentDir
        }

        return result
    }

    fun dub(p: List<Long>, prevDir: Char, nextDir: Char): List<Long> {
        when(nextDir) {
            'U' -> {
                return if (prevDir == 'L') {
                    listOf(p[0] * 2 - 1, p[1] * 2 + 1)
                } else {
                    listOf(p[0] * 2 - 1, p[1] * 2 - 1)
                }
            }
            'D' -> {
                return if (prevDir == 'L') {
                    listOf(p[0] * 2 + 1, p[1] * 2 + 1)
                } else {
                    listOf(p[0] * 2 + 1, p[1] * 2 - 1)
                }
            }
            'L' -> {
                return if (prevDir == 'U') {
                    listOf(p[0] * 2 - 1, p[1] * 2 + 1)
                } else {
                    listOf(p[0] * 2 + 1, p[1] * 2 + 1)
                }
            }
            'R' -> {
                return if (prevDir == 'U') {
                    listOf(p[0] * 2 - 1, p[1] * 2 - 1)
                } else {
                    listOf(p[0] * 2 + 1, p[1] * 2 - 1)
                }
            }
        }

        println("shouldn't get here")
        return listOf()
    }

    fun genLines(input: List<List<Long>>): Pair<List<Line>, List<Line>> {
        val hLines = ArrayList<Line>()
        val vLines = ArrayList<Line>()

        for (i in 0..input.lastIndex - 1) {
            val p1 = input[i]
            val p2 = input[i + 1]

            if (p1[0] == p2[0]) {
                // Vertical Line
                if (p1[1] > p2[1]) {
                    vLines.add(Line(p2, p1))
                } else {
                    vLines.add(Line(p1, p2))
                }
            } else {
                // Horizontal Line
                if (p1[0] > p2[0]) {
                    hLines.add(Line(p2, p1))
                } else {
                    hLines.add(Line(p1, p2))
                }
            }
        }

        return Pair(hLines, vLines)
    }

    data class Line (
        var p1: List<Long>,
        var p2: List<Long>
    )

}
// 21908224 LOW
// 122842425 LOW
// 134866860 LOW
// 1570926588 ?
// 1577920934 ?
// 1783055799 ?
// 1876404754 HIGH
