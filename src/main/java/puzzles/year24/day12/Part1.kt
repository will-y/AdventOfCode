package puzzles.year24.day12

import util.Puzzle

class Part1 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        val map = inputString.lines().map(String::toCharArray);

        val areaSet = HashMap<Char, MutableSet<Pair<Pair<Int, Int>, Int>>>();

        for (y in map.indices) {
            for (x in map[0].indices) {
                val pointsToCheck = pointsToCheck(Pair(x, y));
                var perimeterToAdd = 0;
                for (point in pointsToCheck) {
                    if (isOutOfBounds(map, point) || map[y][x] != map[point.second][point.first]) {
                        perimeterToAdd++;
                    }
                }
                val toAdd = Pair(Pair(x, y), perimeterToAdd);
                areaSet.compute(map[y][x]) {_, set ->
                    if (set == null) {
                        return@compute mutableSetOf(toAdd);
                    }

                    set.add(toAdd);
                    return@compute set;
                }
            }
        }

        return computeAnswer(areaSet);
    }

    private fun computeAnswer(areaSet: HashMap<Char, MutableSet<Pair<Pair<Int, Int>, Int>>>): Int {
        var result = 0;
        for (entry in areaSet.entries) {
            for (areaMap in separateAreas(entry.value)) {
                val area = areaMap.size;
                val perimeter = areaMap.map { i -> i.value }.sum()
                result += perimeter * area;
            }
        }

        return result;
    }

    private fun separateAreas(set: MutableSet<Pair<Pair<Int, Int>, Int>>): List<MutableMap<Pair<Int, Int>, Int>> {
        val results = mutableListOf<MutableMap<Pair<Int, Int>, Int>>();

        for (entry in set) {
            val groupsFound: MutableSet<MutableMap<Pair<Int, Int>, Int>> = mutableSetOf();
            for (result in results) {
                for (point in pointsToCheck(entry.first)) {
                    if (result.keys.contains(point)) {
                        result[entry.first] = entry.second
                        groupsFound.add(result)
                        break;
                    }
                }
            }

            if (groupsFound.isEmpty()) {
                results.add(mutableMapOf(entry))
            } else {
                results.removeAll(groupsFound);
                val newGroup = mutableMapOf<Pair<Int, Int>, Int>();
                groupsFound.forEach{g -> newGroup.putAll(g)}
                results.add(newGroup)
            }

        }

        return results;
    }

    private fun pointsToCheck(point: Pair<Int, Int>): Set<Pair<Int, Int>> {
        val result = mutableSetOf<Pair<Int, Int>>();
        result.add(Pair(point.first - 1, point.second));
        result.add(Pair(point.first + 1, point.second));
        result.add(Pair(point.first, point.second - 1));
        result.add(Pair(point.first, point.second + 1));

        return result;
    }

    private fun isOutOfBounds(map: List<CharArray>, point: Pair<Int, Int>): Boolean {
        return point.first < 0 || point.first >= map[0].size || point.second < 0 || point.second >= map.size;
    }
}
