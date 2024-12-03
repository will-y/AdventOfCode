package puzzles.year24.day3;

import util.Puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 implements Puzzle<Integer> {
    private static final Pattern PATTERN = Pattern.compile("mul\\((\\d+),(\\d+)\\)");

    @Override
    public Integer getAnswer(String inputString) {
        Matcher matcher = PATTERN.matcher(inputString);
        int result = 0;

        while (matcher.find()) {
            result += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
        }

        return result;
    }
}
