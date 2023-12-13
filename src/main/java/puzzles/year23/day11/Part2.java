package puzzles.year23.day11;

import util.Puzzle;

import java.util.*;

public class Part2 implements Puzzle<Long> {
    @Override
    public Long getAnswer(String inputString) {
        List<String> space = Arrays.stream(inputString.split(System.lineSeparator())).toList();
        Set<Integer> hasIndexes = new HashSet<>();
        Set<Integer> noneCols = new HashSet<>();
        Set<Integer> noneRows = new HashSet<>();

        for (int i = 0; i < space.get(0).length(); i++) {
            noneCols.add(i);
        }

        for (int i = 0; i < space.size(); i++) {
            String line = space.get(i);

            int index = line.indexOf('#');
            boolean found = false;

            while (index != -1) {
                hasIndexes.add(index);
                index = line.indexOf('#', index + 1);
                found = true;
            }

            if (!found) {
                noneRows.add(i);
            }
        }

        noneCols.removeAll(hasIndexes);
        List<int[]> galaxies = new ArrayList<>();
        for (int i = 0; i < space.size(); i++) {
            for (int j = 0; j < space.get(0).length(); j++) {
                if (space.get(i).charAt(j) == '#') {
                    galaxies.add(new int[] {j, i});
                }
            }
        }

        long total = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i; j < galaxies.size(); j++) {
                total+= dAxis(galaxies.get(i)[0], galaxies.get(j)[0], noneCols);
                total+= dAxis(galaxies.get(i)[1], galaxies.get(j)[1], noneRows);
            }
        }

        return total;
    }

    private long dAxis(int col1, int col2, Set<Integer> noneCols) {
        int x1 = Math.min(col1, col2);
        int x2 = Math.max(col1, col2);

        long bonus = 0;

        for (int i : noneCols) {
            if (i > x1 && i < x2) {
                bonus += 999999;
            }
        }

        return x2 - x1 + bonus;
    }
}
