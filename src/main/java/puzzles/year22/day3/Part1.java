package puzzles.year22.day3;

import util.Puzzle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Part1 implements Puzzle<Integer> {
    @Override
    public Integer getAnswer(String inputString) {
        String[] sacks = inputString.split(System.lineSeparator());

        return Arrays.stream(sacks).map(this::findDupItem).mapToInt(Integer::intValue).sum();
    }

    private int findDupItem(String sack) {
        Set<Character> inSack = new HashSet<>();

        for (int i = 0; i < sack.length() / 2; i++) {
            inSack.add(sack.charAt(i));
        }

        for (int i = sack.length() / 2; i < sack.length(); i++) {
            if (inSack.contains(sack.charAt(i))) {
                return getItemValue(sack.charAt(i));
            }
        }

        throw new RuntimeException("Bad Sack");
    }

    // A is 65
    // a is 97
    private int getItemValue(char item) {
        return Character.isLowerCase(item) ? item - 96 : item - 38;
    }
}
