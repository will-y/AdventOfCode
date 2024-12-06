package puzzles.year24.day6

import util.Puzzle

class Part1 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        val map = inputString.lines().map(String::toCharArray);

        return getPositionSet(map).size;
    }

    fun getPositionSet(map: List<CharArray>): Set<Pair<Int, Int>> {
        var position = getStartingPosition(map);
        var direction = Direction.NORTH;
        val result = HashSet<Pair<Int, Int>>();

        while (true) {
            result.add(position);
            var nextPosition = Direction.nextPos(direction, position);

            if (outsideMap(map, nextPosition)) {
                break;
            }

            if (map[nextPosition.second][nextPosition.first] == '#') {
                direction = Direction.nextDirection(direction);
                nextPosition = Direction.nextPos(direction, position);
            }

            position = nextPosition;
        }

        return result;
    }

    fun outsideMap(map: List<CharArray>, position: Pair<Int, Int>): Boolean {
        return position.second >= map.size || position.second < 0 || position.first >= map[0].size || position.first < 0;
    }

    fun getStartingPosition(map: List<CharArray>): Pair<Int, Int> {
        for (y in map.indices) {
            for (x in map[y].indices) {
                if (map[y][x] == '^') {
                    return Pair(x, y);
                }
            }
        }

        throw RuntimeException("No dude found");
    }
}
