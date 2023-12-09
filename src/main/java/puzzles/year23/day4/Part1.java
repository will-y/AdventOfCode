package puzzles.year23.day4;

import util.Puzzle;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Part1 implements Puzzle<Integer> {
    @Override
    public Integer getAnswer(String inputString) {
        String[] cards = inputString.split(System.lineSeparator());
        int total = 0;

        for (String card : cards) {
            String[] firstSplit = card.split(":");
            String game = firstSplit[1];
            String[] secondSplit = game.trim().split("\\|");
            String[] winning = secondSplit[0].trim().split(" ");
            String[] have = secondSplit[1].trim().split(" ");

            Set<Integer> winningInts = Arrays.stream(winning).filter(s -> !s.isEmpty()).mapToInt(Integer::parseInt).boxed().collect(Collectors.toSet());
            long result = Arrays.stream(have).filter(s -> !s.isEmpty()).mapToInt(Integer::parseInt).filter(winningInts::contains).count();
            if (result != 0) {
                total += (int) Math.pow(2, result - 1);
            }
        }

        return total;
    }
}
