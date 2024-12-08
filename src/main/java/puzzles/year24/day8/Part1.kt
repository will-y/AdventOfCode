package puzzles.year24.day8

import util.Puzzle

class Part1 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        val map = inputString.lines().map { line -> line.toCharArray() }
        val pointMap = HashMap<Char, HashSet<Pair<Int, Int>>>();

        for (y in map.indices) {
            for (x in map[0].indices) {
                if (map[y][x] != '.') {
                    pointMap.compute(map[y][x]) {_, set ->
                        if (set == null) {
                            val newSet = HashSet<Pair<Int, Int>>();
                            newSet.add(Pair(x, y));
                            return@compute newSet;
                        }

                        set.add(Pair(x, y));
                        return@compute set;
                    }
                }
            }
        }

        val antiSet = HashSet<Pair<Int, Int>>();

        for (char in pointMap.keys) {
            val set = pointMap[char];
            for (pair1 in set!!) {
                for (pair2 in set) {
                    if (pair1 == pair2) continue;

                    getAntiNodes(pair1, pair2).filter { p -> !isOutOfBounds(map, p)}.forEach(antiSet::add);
                }
            }
        }

        return antiSet.size;
    }

    private fun getAntiNodes(p1: Pair<Int, Int>, p2: Pair<Int, Int>): List<Pair<Int, Int>> {
        val dx = p1.second - p2.second;
        val dy = p1.first - p2.first;
        return listOf(Pair(p1.first + dy, p1.second + dx), Pair(p2.first - dy, p2.second - dx));
    }

    private fun isOutOfBounds(map: List<CharArray>, point: Pair<Int, Int>): Boolean {
        return point.first < 0 || point.first >= map.size || point.second < 0 || point.second >= map[0].size;
    }
}
