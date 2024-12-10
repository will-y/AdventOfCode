package puzzles.year24.day9

import util.Puzzle
import java.util.ArrayList

// The tuple is (fileSystemIndex, numberOfSpace, globalIndex)
class Part2 : Puzzle<Long?> {
    override fun getAnswer(inputString: String): Long {
        var overallIndex = 0;
        val fileSystem =
            inputString.toCharArray().foldIndexed(ArrayList<MutableTriple<Int, Int, Int>>()) { index, acc, char ->
                acc.add(MutableTriple(if (index % 2 == 0) (index / 2) else 0, char.digitToInt(), overallIndex))
                overallIndex += char.digitToInt();
                return@foldIndexed acc;
            }
        for (i in fileSystem.indices.reversed()) {
            if (i % 2 == 0) {
                // File space
                findFirstFreeSpace(fileSystem, fileSystem[i], i);
            }
        }

        return calculateResult(fileSystem);
    }

    private fun findFirstFreeSpace(fileSystem: ArrayList<MutableTriple<Int, Int, Int>>, element: MutableTriple<Int, Int, Int>, index: Int) {
        for (i in fileSystem.indices) {
            if (i >= index) return;
            if (i % 2 == 0) continue;

            if (fileSystem[i].second >= element.second) {
                fileSystem[i].second -= element.second;
                val originalFreeIndex = fileSystem[i].third;
                fileSystem[i].third += element.second;
                element.third = originalFreeIndex;
                return;
            }
        }
    }

    private fun calculateResult(fileSystem: ArrayList<MutableTriple<Int, Int, Int>>): Long {
        var result = 0L;
        for (i in fileSystem.indices) {
            val element = fileSystem[i];
            if (element.first == 0) continue;
            for (x in element.third..<element.third + element.second) {
                result += x.toLong() * element.first;
            }
        }

        return result;
    }

    private fun convertToString(fileSystem: ArrayList<MutableTriple<Int, Int, Int>>) {
        val result = Array(100000) { _ -> "" };
        for (i in fileSystem.indices) {
            val element = fileSystem[i];
            if (i != 0 && element.first == 0) {
                for (x in element.third..<element.third + element.second) {
                    result[x] = " . ";
                }
            } else {
                for (x in element.third..<element.third + element.second) {
                    result[x] = "[" + element.first + "]";
                }
            }


        }

        println(result.joinToString(""))
    }
}