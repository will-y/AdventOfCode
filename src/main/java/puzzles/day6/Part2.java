package puzzles.day6;

import util.Puzzle;

public class Part2 implements Puzzle<Long> {
    @Override
    public Long getAnswer(String inputString) {
        String[] parse = inputString.split("\r\n");
        long time = Long.parseLong(parse[0].substring(5).replaceAll("\\s", ""));
        long distance = Long.parseLong(parse[1].substring(10).replaceAll("\\s", ""));

        return searchMax(time, distance) - searchMin(time, distance);
    }

    private long searchMin(long time, long distance) {
        for (int i = 1; i < time; i++) {
            if (i * (time - i) > distance) {
                return i;
            }
        }

        return 0;
    }

    private long searchMax(long time, long distance) {
        for (long i = time - 1; i > 0; i--) {
            if (i * (time - i) > distance) {
                return i + 1;
            }
        }

        return 0;
    }

    private int getDistance(int time, int hold) {
        if (hold >= time) {
            return 0;
        }

        return hold * (time - hold);
    }
}
