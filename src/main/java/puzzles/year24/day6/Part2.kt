package puzzles.year24.day6

import util.Puzzle
import java.util.HashSet

class Part2 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        val map = inputString.lines().map(String::toCharArray);
        val startingPosition = getStartingPosition(map);
        var result = 0;

        for (y in map.indices) {
            for (x in map[y].indices) {
                if (x == startingPosition.first && y == startingPosition.second) {
                    continue;
                }
                if (map[y][x] != '#') {
                    map[y][x] = '#';
                    if (isLoop(map, startingPosition)) {
                        result++;
//                        println("($x,$y)");
                    }
                    map[y][x] = '.';
                }
            }
        }

        return result;
    }

    fun isLoop(map: List<CharArray>, startingPosition: Pair<Int, Int>): Boolean {
        var position = startingPosition;
        var direction = Direction.NORTH;
        val visited = HashSet<Pair<Direction, Pair<Int, Int>>>();

        while (true) {
            if (visited.contains(Pair(direction, position))) {
                return true;
            }
            visited.add(Pair(direction, position));
            var nextPosition = Direction.nextPos(direction, position);

            if (outsideMap(map, nextPosition)) {
                return false;
            }

            if (map[nextPosition.second][nextPosition.first] == '#') {
                direction = Direction.nextDirection(direction);
                nextPosition = Direction.nextPos(direction, position);

                if (map[nextPosition.second][nextPosition.first] == '#') {
                    direction = Direction.nextDirection(direction);
                    nextPosition = Direction.nextPos(direction, position);
                }
            }

            position = nextPosition;
        }
    }

    fun outsideMap(map: List<CharArray>, position: Pair<Int, Int>): Boolean {
        return position.second >= map.size || position.second < 0 || position.first >= map[0].size || position.first < 0;
    }

    fun getStartingPosition(map: List<CharArray>): Pair<Int, Int> {
        for (y in map.indices) {
            for (x in map[y].indices) {
                if (map[y][x] == '^') {
                    map[y][x] = '.';
                    return Pair(x, y);
                }
            }
        }

        throw RuntimeException("No dude found");
    }
}

// 1845
