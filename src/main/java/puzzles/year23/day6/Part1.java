package puzzles.year23.day6;

import util.Puzzle;

import java.util.Arrays;

public class Part1 implements Puzzle<Integer> {
    @Override
    public Integer getAnswer(String inputString) {
        String[] parse = inputString.split(System.lineSeparator());
        int[] times = Arrays.stream(parse[0].substring(5).split(" ")).filter(s -> !s.isBlank()).mapToInt(Integer::parseInt).toArray();
        int[] distances = Arrays.stream(parse[1].substring(10).split(" ")).filter(s -> !s.isBlank()).mapToInt(Integer::parseInt).toArray();
        int result = 1;

        for (int i = 0; i < times.length; i++) {
            int waysToWin = 0;
            for (int j = 1; j < times[i]; j++) {
                if (getDistance(times[i], j) > distances[i]) {
                    waysToWin++;
                } else if (waysToWin > 0) {
                    break;
                }
            }

            result *= waysToWin;
        }

        return result;
    }

    private int getDistance(int time, int hold) {
        if (hold >= time) {
            return 0;
        }

        return hold * (time - hold);
    }
}
