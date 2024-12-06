package puzzles.year24.day6

import util.Puzzle

class Part1 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        val map = inputString.lines().map(String::toCharArray);

        var position = getStartingPosition(map);
        var direction = Direction.NORTH;

        while (true) {
            var nextPosition = Direction.nextPos(direction, position);

            if (outsideMap(map, nextPosition)) {
                break;
            }

            if (map[nextPosition.second][nextPosition.first] == '#') {
                direction = Direction.nextDirection(direction);
                nextPosition = Direction.nextPos(direction, position);
            }

            position = nextPosition;
            map[nextPosition.second][nextPosition.first] = '+';
        }

        var count = 0;
        for (y in map.indices) {
            for (x in map[y].indices) {
                if (map[y][x] == '+') {
                    count++;
                }
            }
        }

        return count;
    }

    fun outsideMap(map: List<CharArray>, position: Pair<Int, Int>): Boolean {
        return position.second >= map.size || position.second < 0 || position.first >= map[0].size || position.first < 0;
    }

    fun getStartingPosition(map: List<CharArray>): Pair<Int, Int> {
        for (y in map.indices) {
            for (x in map[y].indices) {
                if (map[y][x] == '^') {
                    map[y][x] = '+';
                    return Pair(x, y);
                }
            }
        }

        throw RuntimeException("No dude found");
    }
}
