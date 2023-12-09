package puzzles.day9;

import util.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Part1 implements Puzzle<Integer> {
    @Override
    public Integer getAnswer(String inputString) {
        String[] sequences = inputString.split("\r\n");

        int result = 0;
        for (String sequence : sequences) {
            result += getNextValue(Arrays.stream(sequence.split(" ")).mapToInt(Integer::parseInt).toArray());
        }

        return result;
    }

    private int getNextValue(int[] sequence) {
        List<int[]> rows = new ArrayList<>();
        rows.add(sequence);

        while (true) {
            rows.add(getNextRow(rows.get(rows.size() - 1)));
            if (Arrays.stream(rows.get(rows.size() - 1)).allMatch(value -> value == 0)) {
                break;
            }
        }

        int num = 0;

        for (int i = rows.size() - 1; i >= 0; i--) {
            num = num + rows.get(i)[rows.get(i).length - 1];
        }

        return num;
    }

    private int[] getNextRow(int[] sequence) {
        int[] result = new int[sequence.length - 1];
        for (int i = 0; i < sequence.length - 1; i++) {
            result[i] = sequence[i + 1] - sequence[i];
        }

        return result;
    }
}
