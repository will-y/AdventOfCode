package puzzles.year23.day12;

import util.Puzzle;

import java.util.*;
import java.util.regex.Pattern;

public class Part2 implements Puzzle<Long> {

    @Override
    public Long getAnswer(String inputString) {
        String[] lines = inputString.split(System.lineSeparator());
        long total = 0L;

        int start = 12;
        int count = 0;

        for (String line : lines) {
            if (count <= start) {
                count++;
                continue;
            }
            String[] lineComponents = line.split(" ");
            String pattern = lineComponents[0] + "?" + lineComponents[0] + "?" + lineComponents[0] + "?" + lineComponents[0] + "?" + lineComponents[0];
//            String pattern = lineComponents[0];
//            String repeatString = lineComponents[1];
            String repeatString = lineComponents[1] + "," + lineComponents[1] + "," + lineComponents[1] + "," + lineComponents[1] + "," + lineComponents[1];
            int[] repeat = Arrays.stream(repeatString.split(",")).mapToInt(Integer::parseInt).toArray();

            Queue<String> queue = new LinkedList<>();
            queue.add(pattern);

            // Iterate through the strings
            // Have a queue to add things to
            // For every ., reset counter
            // For every # increase counter
            // For every ?, if you need a # place it
            // If counter is 0, and there is a ?, need to branch
            int runningTotal = 0;
            while (!queue.isEmpty()) {
                char[] input = queue.poll().toCharArray();
                int counter = 0;
                int patternIndex = 0;
                boolean broken = false;
                for (int i = 0; i < input.length; i++) {
                    char c = input[i];

                    if (c == '.') {
                        if (counter != 0) {
                            if (counter != repeat[patternIndex]) {
                                broken = true;
                                break;
                            }
                            patternIndex++;
                        }
                        counter = 0;
                    } else if (c == '#') {
                        if (patternIndex >= repeat.length) {
                            broken = true;
                            break;
                        }
                        counter++;
                        if (counter > repeat[patternIndex]) {
                            broken = true;
                            break;
                        }
                    } else if (c == '?') {
                        if (patternIndex >= repeat.length) {
                            input[i] = '.';
                            counter = 0;
                        } else if (counter == 0) {
                            input[i] = '.';
                            queue.add(new String(input));
                            input[i] = '#';
                            counter++;
                            if (counter > repeat[patternIndex]) {
                                broken = true;
                                break;
                            }
                        } else if (counter < repeat[patternIndex]) {
                            input[i] = '#';
                            counter++;
                            if (counter > repeat[patternIndex]) {
                                broken = true;
                                break;
                            }
                        } else if (counter == repeat[patternIndex]) {
                            input[i] = '.';
                            patternIndex++;
                            counter = 0;
                        }
                    }
                }

                if (input[input.length - 1] == '#' && repeat[repeat.length - 1] == counter) {
                    patternIndex++;
                }

                if (!broken && patternIndex == repeat.length) {

                    // Got through the whole string
//                    System.out.println(Arrays.toString(input));
//                    System.out.println("Pattern Index: " + patternIndex + ", repeat: " + repeat.length);
                    runningTotal++;
                }
            }
            total += runningTotal;
            System.out.println(count++ + ": " + runningTotal);
        }

        return total;
    }
}
