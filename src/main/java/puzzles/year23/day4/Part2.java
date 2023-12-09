package puzzles.year23.day4;

import util.Puzzle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Part2 implements Puzzle<Integer> {
    @Override
    public Integer getAnswer(String inputString) {
        String[] cards = inputString.split(System.lineSeparator());

        int[] copies = new int[cards.length];
        Arrays.fill(copies, 1);

        for (int i = 0; i < cards.length; i++) {
            String[] firstSplit = cards[i].split(":");
            String game = firstSplit[1];
            String[] secondSplit = game.trim().split("\\|");
            String[] winning = secondSplit[0].trim().split(" ");
            String[] have = secondSplit[1].trim().split(" ");

            Set<Integer> winningInts = Arrays.stream(winning).filter(s -> !s.isEmpty()).mapToInt(Integer::parseInt).boxed().collect(Collectors.toSet());
            long result = Arrays.stream(have).filter(s -> !s.isEmpty()).mapToInt(Integer::parseInt).filter(winningInts::contains).count();

            for (int j = 0; j < result; j++) {
                copies[i + j + 1] += copies[i];
            }
        }

        return Arrays.stream(copies).sum();
    }
}
