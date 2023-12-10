package puzzles.year22.day1;

import util.Puzzle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Part2 implements Puzzle<Integer> {
    @Override
    public Integer getAnswer(String inputString) {
        String[] lines = inputString.split(System.lineSeparator());

        int currentTotal = 0;

        List<Integer> calories = new ArrayList<>();

        for (int i = 0; i < lines.length; i++) {
            if (lines[i].isEmpty()) {
                calories.add(currentTotal);
                currentTotal = 0;
            } else {
                currentTotal += Integer.parseInt(lines[i]);
            }
        }

        calories.add(currentTotal);

        return calories.stream().sorted(Comparator.comparingInt(value -> (Integer) value).reversed()).limit(3).mapToInt(value -> value).sum();
    }
}
