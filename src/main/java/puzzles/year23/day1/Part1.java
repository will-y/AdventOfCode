package puzzles.year23.day1;

import util.Puzzle;

public class Part1 implements Puzzle<Integer> {
    @Override
    public Integer getAnswer(String inputString) {
        String[] inputArray = inputString.split("\n");
        int total = 0;

        for (int i = 0; i < inputArray.length; i++) {
            String toTest = inputArray[i];

            char[] toTestArray = toTest.toCharArray();

            String first = "";
            String last = "";



            for (int j = 0; j < toTestArray.length; j++) {
                char c = toTestArray[j];

                if (isNumeric(c)) {
                    if (first.isEmpty()) {
                        first = String.valueOf(c);
                    }

                    last = String.valueOf(c);
                }
            }

            int value = Integer.valueOf(first + last);
            total += value;
        }

        return total;
    }

    public static boolean isNumeric(char c) {
        try {
            Integer.parseInt(String.valueOf(c));

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
