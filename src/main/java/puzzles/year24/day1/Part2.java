package puzzles.year24.day1;

import util.Puzzle;

import java.util.ArrayList;
import java.util.List;

public class Part2 implements Puzzle<Integer>  {
    @Override
    public Integer getAnswer(String inputString) {
        String[] lines = inputString.split("\r\n");
        List<Integer> first = new ArrayList<>();
        List<Integer> second = new ArrayList<>();

        for (String line : lines) {
            String[] pair = line.split(" {3}");
            first.add(Integer.parseInt(pair[0]));
            second.add(Integer.parseInt(pair[1]));
        }

        first.sort(Integer::compareTo);
        second.sort(Integer::compareTo);

        int result = 0;
        for (Integer i : first) {
            int count = 0;
            boolean found = false;
            for (Integer j : second) {
                if (i.equals(j)) {
                    count++;
                    found = true;
                } else if (found) {
                    break;
                }
            }
            result += count * i;
        }

        return result;
    }
}
