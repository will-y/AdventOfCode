package puzzles.day3;

import util.Puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part2 implements Puzzle<Integer> {
    private static Pattern PATTERN = Pattern.compile("(^|\\D)\\d+(\\D|$)");
    @Override
    public Integer getAnswer(String inputString) {
        // Find all stars
        // Look for numbers
        // ???
        // Profit
        int rowLength = inputString.split("\r\n")[0].length();
        String actualString = inputString.replace("\r\n", "");
        int total = 0;

        Pattern pattern = Pattern.compile("\\*");
        Matcher matcher = pattern.matcher(actualString);



        while(matcher.find()) {
            int index = matcher.start();
            List<Integer> numbers = new ArrayList<>();
            List<Integer> usedIndexes = new ArrayList<>();
            for (Integer i : List.of(index - 1, index + 1, index - rowLength, index + rowLength, index - rowLength + 1, index - rowLength - 1, index + rowLength - 1, index + rowLength + 1)) {
                List<Integer> number = findNumber(actualString, i, usedIndexes);
                if (!number.isEmpty()) {
                    numbers.add(number.get(0));
                    usedIndexes.add(number.get(1));
                }
            }

            if (numbers.size() > 1) {
                total += numbers.stream().reduce(1, (a, b) -> a * b);
            }
        }
        return total;
    }

    private List<Integer> findNumber(String input, int index, List<Integer> usedIndexes) {
        if (index < 0 || index > input.length() - 1 || !Character.isDigit(input.charAt(index))) {
            return Collections.emptyList();
        }

        StringBuilder number = new StringBuilder();
        StringBuilder end = new StringBuilder();
        int i = 0;

        while (validIndex(input, index + i) && Character.isDigit(input.charAt(index + i))) {
            number.append(input.charAt(index + i));
            i++;
        }

        int j = -1;
        while(validIndex(input, index + j) && Character.isDigit(input.charAt(index + j))) {
            end.append(input.charAt(index + j));
            j--;
        }

        String numberString = number.toString();
        String endString = end.reverse().toString();

        if (usedIndexes.contains(index - endString.length())) {
            return Collections.emptyList();
        }

        return List.of(Integer.parseInt(endString + numberString), index - endString.length());
    }

    private boolean validIndex(String string, int index) {
        return index >= 0 && index < string.length();
    }
}
