package puzzles.year23.day12;

import util.Puzzle;

import java.util.*;
import java.util.regex.Pattern;

public class Part1 implements Puzzle<Integer> {
    @Override
    public Integer getAnswer(String inputString) {
        String[] lines = inputString.split(System.lineSeparator());

        int total = 0;

        for (String line : lines) {
            String[] lineComponents = line.split(" ");
            String pattern = lineComponents[0];
            int[] repeat = Arrays.stream(lineComponents[1].split(",")).mapToInt(Integer::parseInt).toArray();
            Set<String> seen = new HashSet<>();
            Queue<String> queue = new LinkedList<>();
            queue.offer(pattern);
            int possibilitiesCount = 0;

            while (!queue.isEmpty()) {
                String currentLine = queue.poll();
                seen.add(currentLine);

                if (isCorrect(currentLine, repeat)) {
                    possibilitiesCount++;
                    continue;
                }

                String[] possibilities = getPossibilities(currentLine, repeat);

                for (String possibility : possibilities) {
                    if (!seen.contains(possibility)) {
                        queue.offer(possibility);
                    }
                }
            }

            System.out.println(possibilitiesCount);
            total += possibilitiesCount;
        }

        return total;
    }

    private String[] getPossibilities(String line, int[] repeat) {
        if (!isPossible(line, repeat)) {
            return new String[]{};
        }

        List<String> possibilities = new ArrayList<>();

        int index = line.indexOf("?");

        if (index == -1) {
            return new String[]{line};
        }

        possibilities.add(line.replaceFirst("\\?", "."));
        possibilities.add(line.replaceFirst("\\?", "#"));

        return possibilities.toArray(String[]::new);
    }

    private boolean isPossible(String line, int[] repeat) {
        int count = 0;
        int index = line.indexOf("?");

        if (index == -1) return true;

        String[] lineArray = Arrays.stream(line.substring(0, index).split("\\.")).filter(s -> !s.isBlank()).toArray(String[]::new);

        for (int i = 0; i < Math.min(lineArray.length - 1, repeat.length); i++) {
            if (repeat[i] != lineArray[i].length()) {
                return false;
            }
        }

        for (char c : line.toCharArray()) {
            if (c != '.') count++;
        }

        return count >= Arrays.stream(repeat).sum();
    }

    private boolean isCorrect(String line, int[] repeat) {
        String[] lineArray = Arrays.stream(line.split("\\.")).filter(s -> !s.isBlank()).toArray(String[]::new);

        if (lineArray.length != repeat.length) return false;

        for (int i = 0; i < repeat.length; i++) {
            if (lineArray[i].length() != repeat[i]) {
                return false;
            }
        }

        return true;
    }
}
