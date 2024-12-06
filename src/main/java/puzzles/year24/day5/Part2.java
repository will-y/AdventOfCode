package puzzles.year24.day5;

import util.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Part2 implements Puzzle<Integer>  {
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
            List<Integer> pages = new ArrayList<>(Arrays.stream(line.split(",")).map(Integer::parseInt).toList());
            boolean bad = false;
            for (int i = 0; i < pages.size(); i++) {
                int page = pages.get(i);
                seen.add(page);

                List<Integer> rule = rules.get(page);

                if (rule != null) {
                    Optional<Integer> seenValue = rule.stream().filter(seen::contains).findFirst();

                    if (seenValue.isPresent()) {
//                        System.out.println("Swapping " + seenValue.get() + " and " + pages.get(i));
                        swap(pages, seenValue.get(), i);
//                        pages.add(i + 1, seenValue.get());
//                        pages.remove(seenValue.get());
//                        pages.remove(i);
//                        pages.add(pages.indexOf(seenValue.get()), page);



                        i = -1;
                        seen = new HashSet<>();
                        bad = true;
                    }
                }
            }

            if (bad) {
                result += pages.get(pages.size() / 2);
//                System.out.println(line);
//                System.out.println(String.join(",", pages.stream().map(String::valueOf).toList()));
            }

        }
        return result;
    }

    private void swap(List<Integer> list, Integer first, int secondIndex) {
        int firstIndex = list.indexOf(first);

        list.set(firstIndex, list.get(secondIndex));
        list.set(secondIndex, first);
    }
}