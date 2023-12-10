package puzzles.year22.day3;

import util.Puzzle;

import java.util.HashSet;
import java.util.Set;

public class Part2 implements Puzzle<Integer> {
    @Override
    public Integer getAnswer(String inputString) {
        String[] sacks = inputString.split(System.lineSeparator());
        int result = 0;

        for (int i = 0; i < sacks.length; i+=3) {
            Set<Character> firstCharacterSet = new HashSet<>();
            for (int j = 0; j < sacks[i].length(); j++) {
                firstCharacterSet.add(sacks[i].charAt(j));
            }

            Set<Character> secondCharacterSet = new HashSet<>();
            for (int j = 0; j < sacks[i+1].length(); j++) {
                if (firstCharacterSet.contains(sacks[i+1].charAt(j))) {
                    secondCharacterSet.add(sacks[i+1].charAt(j));
                }
            }

            for (int j = 0; j < sacks[i+2].length(); j++) {
                if (secondCharacterSet.contains(sacks[i+2].charAt(j))) {
                    result += getItemValue(sacks[i+2].charAt(j));
                    break;
                }
            }
        }

        return result;
    }

    // A is 65
    // a is 97
    private int getItemValue(char item) {
        return Character.isLowerCase(item) ? item - 96 : item - 38;
    }
}
