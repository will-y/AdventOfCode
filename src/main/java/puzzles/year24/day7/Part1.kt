package puzzles.year24.day7

import util.Puzzle

class Part1 : Puzzle<Long?> {
    override fun getAnswer(inputString: String): Long {
        val lines = inputString.lines();
        var overallResult = 0L;
        for (line in lines) {
            val split = line.split(":");
            val operandList = split[1].trim().split(" ").filter { s -> s.isNotBlank() }.map(String::toLong);
            val result = split[0].toLong();

            if (canWork(operandList, result)) {
                overallResult += result;
            }
        }
        return overallResult;
    }

    private fun canWork(operandList: List<Long>, value: Long): Boolean {
        var list : List<Long> = ArrayList<Long>(listOf(operandList[0]));

        var first = true;
        for (i in operandList.indices) {
            if (first) {
                first = false;
                continue
            }

            list = recurse(operandList[i], list);
        }

        return list.contains(value);
    }

    private fun recurse(value: Long, prev: List<Long>): List<Long> {
        return prev.flatMap { prevVal -> listOf(prevVal * value, prevVal + value) };
    }
}
