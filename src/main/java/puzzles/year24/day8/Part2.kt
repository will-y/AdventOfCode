package puzzles.year24.day8

import util.Puzzle

class Part2 : Puzzle<Int?> {
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

                    getAntiNodes(map, pair1, pair2).forEach(antiSet::add);
                }
            }
        }

        return antiSet.size;
    }

    private fun getAntiNodes(map: List<CharArray>, p1: Pair<Int, Int>, p2: Pair<Int, Int>): Set<Pair<Int, Int>> {
        var dx = p1.second - p2.second;
        var dy = p1.first - p2.first;

        val gcd = gcd(Math.abs(dx), Math.abs(dy));
        dx /= gcd;
        dy /= gcd;

        val result = HashSet<Pair<Int, Int>>();

        var newPoint = Pair(p1.first, p1.second);
        while(!isOutOfBounds(map, newPoint)) {
            result.add(newPoint);
            newPoint = Pair(newPoint.first + dy, newPoint.second + dx)
        }

        newPoint = Pair(p1.first, p1.second);
        while(!isOutOfBounds(map, newPoint)) {
            result.add(newPoint);
            newPoint = Pair(newPoint.first - dy, newPoint.second - dx)
        }

        return result;
    }

    private fun isOutOfBounds(map: List<CharArray>, point: Pair<Int, Int>): Boolean {
        return point.first < 0 || point.first >= map.size || point.second < 0 || point.second >= map[0].size;
    }

    private fun gcd(i1: Int, i2: Int): Int {
        if (i1 == i2) return i2;
        if (i2 == 0) return i1;
        if (i1 == 0) return i2;

        return gcd(i2, i1 % i2);
    }
}
