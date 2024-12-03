package puzzles.year24.day3;

import util.Puzzle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part2 implements Puzzle<Integer> {
    private static final Pattern MUL_PATTERN = Pattern.compile("(?s)^mul\\((\\d+),(\\d+)\\).*");
    private static final Pattern ENABLE_PATTERN = Pattern.compile("(?s)^do\\(\\).*");
    private static final Pattern DISABLE_PATTERN = Pattern.compile("(?s)^don't\\(\\).*");

    @Override
    public Integer getAnswer(String inputString) {
        boolean enabled = true;
        int result = 0;

        for (int i = 0; i < inputString.length(); i++) {
            String newString = inputString.substring(i);
            Matcher mulMatcher = MUL_PATTERN.matcher(newString);
            Matcher enableMatcher = ENABLE_PATTERN.matcher(newString);
            Matcher disableMatcher = DISABLE_PATTERN.matcher(newString);

            if (enabled) {
                if (mulMatcher.matches()) {
                    result += Integer.parseInt(mulMatcher.group(1)) * Integer.parseInt(mulMatcher.group(2));
                }
            }

            if (enableMatcher.matches()) {
                enabled = true;
            }

            if (disableMatcher.matches()) {
                enabled = false;
            }
        }

        return result;
    }
}
