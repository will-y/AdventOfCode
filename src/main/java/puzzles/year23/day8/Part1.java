package puzzles.year23.day8;

import util.Puzzle;

import java.util.*;

public class Part1 implements Puzzle<Integer> {
    @Override
    public Integer getAnswer(String inputString) {
        String[] lines = inputString.split(System.lineSeparator());
        String instructions = lines[0];
        String[] map = Arrays.copyOfRange(lines, 2, lines.length);

        Map<String, List<String>> lookupMap = new HashMap<>();

        for (String mapLine : map) {
            String[] components = mapLine.split(" ");
            String key = components[0];
            String firstValue = components[2].substring(1, 4);
            String secondValue = components[3].substring(0, 3);
            lookupMap.put(key, List.of(firstValue, secondValue));
        }

        int steps = 0;
        String node = "AAA";

        while (true) {
            char character = instructions.charAt(steps % instructions.length());
            int index = character == 'R' ? 1 : 0;
            node = lookupMap.get(node).get(index);
            steps++;

            if (node.equals("ZZZ")) {
                return steps;
            }
        }
    }
}
