package puzzles.year24.day5;

import util.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part1 implements Puzzle<Integer> {
    @Override
    public Integer getAnswer(String inputString) {
        String[] parts = inputString.split(System.lineSeparator() + System.lineSeparator());
        String[] ruleString = parts[0].split(System.lineSeparator());

        HashMap<Integer, List<Integer>> rules = new HashMap<>();

        for (String rule : ruleString) {
            String[] s = rule.split("\\|");
            rules.compute(Integer.parseInt(s[0]), (integer, integers) -> {
                if (integers == null) integers = new ArrayList<>();
                integers.add(Integer.parseInt(s[1]));
                return integers;
            });
        }

        String[] lines = parts[1].split(System.lineSeparator());

        int result = 0;
        for (String line : lines) {
            Set<Integer> seen = new HashSet<>();
            int[] pages = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
            boolean bad = false;
            for (int page : pages) {
                seen.add(page);

                List<Integer> rule = rules.get(page);

                if (rule != null && rule.stream().anyMatch(seen::contains)) {
                    bad = true;
                    break;
                }
            }

            if (!bad) {
                result += pages[pages.length / 2];
            }
        }
        return result;
    }
}
