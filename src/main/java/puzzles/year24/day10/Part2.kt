package puzzles.year24.day10

import util.Puzzle

class Part2 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        val map = inputString.lines().map { line -> line.toCharArray().map { c -> c.digitToInt() } }
        val zeroPositions = getZeroPositions(map);

        var result = 0;

        for (position in zeroPositions) {
            result += getScore(map, position);
        }

        return result;
    }

    private fun getZeroPositions(map: List<List<Int>>): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>();
        for (y in map.indices) {
            for (x in map[0].indices) {
                if (map[y][x] == 0) {
                    result.add(Pair(x, y));
                }
            }
        }

        return result;
    }

    private fun getScore(map: List<List<Int>>, startPos: Pair<Int, Int>): Int {
        return getScoreRecursive(map, startPos, -1);
    }

    private fun getScoreRecursive(map: List<List<Int>>, pos: Pair<Int, Int>, lastNum: Int): Int {
        if (isOutOfBounds(map, pos)) return 0;

        val value = map[pos.second][pos.first];
        if (value != lastNum + 1) return 0;
        if (value == 9) return 1;

        return getScoreRecursive(map, Pair(pos.first, pos.second - 1), value) +
                getScoreRecursive(map, Pair(pos.first, pos.second + 1), value) +
                getScoreRecursive(map, Pair(pos.first - 1, pos.second), value) +
                getScoreRecursive(map, Pair(pos.first + 1, pos.second), value);
    }

    private fun isOutOfBounds(map: List<List<Int>>, point: Pair<Int, Int>): Boolean {
        return point.first < 0 || point.first >= map[0].size || point.second < 0 || point.second >= map.size;
    }
}
