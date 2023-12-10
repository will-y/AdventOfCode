package puzzles.year22.day1;

import util.Puzzle;

public class Part1 implements Puzzle<Integer> {
    @Override
    public Integer getAnswer(String inputString) {
        String[] lines = inputString.split(System.lineSeparator());

        int max = 0;
        int currentTotal = 0;

        for (int i = 0; i < lines.length; i++) {
            if (lines[i].isEmpty()) {
                if (currentTotal > max) {
                    max = currentTotal;
                }
                currentTotal = 0;
            } else {
                currentTotal += Integer.parseInt(lines[i]);
            }
        }
        return max;
    }
}
