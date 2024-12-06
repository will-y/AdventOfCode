package puzzles.year24.day6

import util.Puzzle
import java.util.HashSet

class Part2 : Puzzle<Int?> {
    val part1 = Part1();

    override fun getAnswer(inputString: String): Int {
        val map = inputString.lines().map(String::toCharArray);
        val positionsToCheck = part1.getPositionSet(map);
        val startingPosition = part1.getStartingPosition(map);
        var result = 0;

        for (position in positionsToCheck) {
            val x = position.first;
            val y = position.second;
            if (x == startingPosition.first && y == startingPosition.second) {
                continue;
            }
            if (map[y][x] != '#') {
                map[y][x] = '#';
                if (isLoop(map, startingPosition)) {
                    result++;
                }
                map[y][x] = '.';
            }
        }

        return result;
    }

    private fun isLoop(map: List<CharArray>, startingPosition: Pair<Int, Int>): Boolean {
        var position = startingPosition;
        var direction = Direction.NORTH;
        val visited = HashSet<Pair<Direction, Pair<Int, Int>>>();

        while (true) {
            if (visited.contains(Pair(direction, position))) {
                return true;
            }
            visited.add(Pair(direction, position));
            var nextPosition = Direction.nextPos(direction, position);

            if (part1.outsideMap(map, nextPosition)) {
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
}
