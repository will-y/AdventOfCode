package puzzles.year24.day2;

import util.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Part1 implements Puzzle<Integer> {
    @Override
    public Integer getAnswer(String inputString) {
       String[] reports = inputString.split("\r\n");

       return (int) Arrays.stream(reports).filter(this::valid).count();
    }

    private boolean valid(String levels) {
        int[] intLevels = Arrays.stream(levels.split(" ")).mapToInt(Integer::parseInt).toArray();

        return isIncreasing(intLevels) || isDecreasing(intLevels);
    }

    private boolean isDecreasing(int[] levels) {
        for (int i = 1; i < levels.length; i++) {
            int diff = levels[i] - levels[i - 1];

            if (diff > 3 || diff < 1) {
                return false;
            }
        }

        return true;
    }

    private boolean isIncreasing(int[] levels) {
        for (int i = 1; i < levels.length; i++) {
            int diff = levels[i - 1] - levels[i];

            if (diff > 3 || diff < 1) {
                return false;
            }
        }

        return true;
    }
}
