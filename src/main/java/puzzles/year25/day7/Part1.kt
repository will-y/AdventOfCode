package puzzles.year25.day7

import util.Puzzle

class Part1 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        val lines = inputString.trim().lines()
        var beams: MutableSet<Int> = HashSet()
        beams.add(lines[0].indexOf('S'))

        var count = 0

        var first = true;
        for (line in lines) {
            if (first) {
                first = false
                continue
            }

            val result = step(line, beams)
            beams = result.first
            count += result.second
//            println(lines.indexOf(line).toString() + ", " + beams.size)

        }
        return count
    }

    fun step(line: String, beams: MutableSet<Int>): Pair<MutableSet<Int>, Int> {
        val iterator = beams.iterator()
        val toAdd = HashSet<Int>()
        var count = 0;
        while (iterator.hasNext()) {
            val i = iterator.next()
            val c = line[i]
            if (c == '^') {
                iterator.remove()
                toAdd.add(i + 1)
                toAdd.add(i - 1)
                count++
            }
        }

        beams.addAll(toAdd)
        return Pair(beams, count)
    }
}
