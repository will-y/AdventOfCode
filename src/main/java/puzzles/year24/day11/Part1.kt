package puzzles.year24.day11

import util.Puzzle

class Part1 : Puzzle<Int?> {
    override fun getAnswer(inputString: String): Int {
        var stones = inputString.split(" ")

        for (i in 0..<25) {
            stones = step(stones);
        }

        return stones.size;
    }

    private fun step(stones: List<String>): List<String> {
        val result = mutableListOf<String>();

        for (stone in stones) {
            if (stone == "0") {
                result.add("1");
            } else if (stone.length % 2 == 0) {
                result.add(stone.substring(0, stone.length / 2).toLong().toString());
                result.add(stone.substring(stone.length / 2).toLong().toString());
            } else {
                result.add((stone.toLong() * 2024).toString());
            }
        }

        return result;
    }
}
