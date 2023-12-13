package puzzles.year23.day11;

import util.Puzzle;

import java.util.*;

public class Part1 implements Puzzle<Long> {
    @Override
    public Long getAnswer(String inputString) {
        List<String> space = Arrays.stream(inputString.split(System.lineSeparator())).toList();
        List<String> space2 = new ArrayList<>();
        List<String> space3 = new ArrayList<>();
        Set<Integer> hasIndexes = new HashSet<>();
        Set<Integer> noneIndexes = new HashSet<>();

        for (int i = 0; i < space.get(0).length(); i++) {
            noneIndexes.add(i);
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

            space2.add(line);
            if (!found) {
                space2.add(line.trim());
            }
        }

        noneIndexes.removeAll(hasIndexes);

        for (String line : space2) {
            char[] lineArray = new char[line.length() + noneIndexes.size()];

            int additionalIndex = 0;
            for (int i : noneIndexes) {
                lineArray[i + additionalIndex++] = '.';
            }

            for (int i = 0; i < lineArray.length; i++) {
                if (lineArray[i] != '.') {
                    lineArray[i] = line.charAt(0);
                    line = line.substring(1);
                }
            }

            space3.add(new String(lineArray));
        }

        List<long[]> galaxies = new ArrayList<>();

        for (int i = 0; i < space3.size(); i++) {
            for (int j = 0; j < space3.get(0).length(); j++) {
                if (space3.get(i).charAt(j) == '#') {
                    galaxies.add(new long[] {j, i});
                }
            }
        }

        long total = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i; j < galaxies.size(); j++) {
                total+= getDistance(galaxies.get(i), galaxies.get(j));
            }
        }

        return total;
    }

    private long getDistance(long[] galaxy1, long[] galaxy2) {
        return Math.abs(galaxy1[0] - galaxy2[0]) + Math.abs(galaxy1[1] - galaxy2[1]);
    }
}
