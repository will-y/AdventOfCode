package puzzles.year23.day3;

import util.Puzzle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 implements Puzzle<Integer> {
    private static final Pattern SYMBOLS = Pattern.compile("[*#$@+=!%^&()/?\\-]");
    private static final Pattern SYMBOLS2 = Pattern.compile(".*[^.\\d].*");
    @Override
    public Integer getAnswer(String inputString) {
        String[] lines = inputString.split("\n");
        Pattern pattern = Pattern.compile("(^\\d+)|(\\D\\d+)");

        int result = 0;

        for (int i = 0; i < lines.length; i++) {
            Matcher matcher = pattern.matcher(lines[i]);
            while(matcher.find()) {
                String number = matcher.group();
                boolean notFirst = false;
                if (!isNumber(number)) {
                    number = number.substring(1);
                    notFirst = true;
                };

                int startIndex = matcher.start();
                int endIndex = matcher.end();
                String prevLine = i == 0 ? "" : lines[i - 1];
                String nextLines = i == lines.length - 1 ? "" : lines[i + 1];

                if (isTouchingSymbol(lines[i], nextLines, prevLine, endIndex - startIndex, startIndex, notFirst)) {
                    result += Integer.parseInt(number);
                }
            }
        }

        return result;
    }

    private boolean isNumber(String number) {
        if (number.startsWith("+") || number.startsWith("-")) return false;
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isTouchingSymbol(String line, String nextLine, String prevLine, int digits, int index, boolean notFirst) {
        line = line.trim();
        nextLine = nextLine.trim();
        prevLine = prevLine.trim();
        int offset = notFirst ? 1 : 0;
        String toLookPrev = prevLine.isEmpty() ? "" : prevLine.substring(Math.max(0, index - 1 + offset), Math.min(prevLine.length(), index + digits + 1));
        String toLookNext = nextLine.isEmpty() ? "" : nextLine.substring(Math.max(0, index - 1 + offset), Math.min(nextLine.length(), index + digits + 1));
        String toLookCurrent = line.substring(Math.max(0, index - 1 + offset), Math.min(line.length(), index + digits + 1));

        Matcher prevMatcher = SYMBOLS2.matcher(toLookPrev);
        Matcher nextMatcher = SYMBOLS2.matcher(toLookNext);
        Matcher currentMatcher = SYMBOLS2.matcher(toLookCurrent);

        return prevMatcher.find() || nextMatcher.find() || currentMatcher.find();
    }
}
