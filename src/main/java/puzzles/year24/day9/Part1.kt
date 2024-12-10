package puzzles.year24.day9

import util.Puzzle

class Part1 : Puzzle<Long?> {
    override fun getAnswer(inputString: String): Long {
        // First number is actual value, second is number left
        val fileSystem = inputString.toCharArray().mapIndexed {index, char -> MutablePair(if (index % 2 == 0)  (index / 2) else 0, char.digitToInt()) };
        var result = 0L;
        var originalIndex = 0;
        var transformedIndex = 0;
        var endIndex = fileSystem.size - 1;

        while (originalIndex <= endIndex) {
           if (originalIndex % 2 == 0) {
               // File space
               val pair = fileSystem[originalIndex];
               while (pair.second > 0) {
                   result += pair.first * transformedIndex;
                   pair.second--;
                   transformedIndex++;
               }

               originalIndex++;
           } else {
               // Free space
               var toInsert = fileSystem[endIndex];
               val freeSpace = fileSystem[originalIndex];

               while (freeSpace.second > 0) {
                   // Out of stuff to move from this pair
                   if (toInsert.second == 0) {
                       endIndex -= 2;
                       toInsert = fileSystem[endIndex];
                       if (endIndex < originalIndex) break;
                       continue;
                   }

                   result += toInsert.first * transformedIndex;
                   freeSpace.second--;
                   toInsert.second--;
                   transformedIndex++;
               }

               originalIndex++;
           }
        }
        return result;
    }
}

// 593815965
