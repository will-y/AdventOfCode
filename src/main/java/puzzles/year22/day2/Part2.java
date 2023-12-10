package puzzles.year22.day2;

import util.Puzzle;

import java.util.HashMap;
import java.util.Map;

public class Part2 implements Puzzle<Integer> {
    Map<String, Integer> pointMap = new HashMap<>() {{
        put("A X", 0 + 3);
        put("A Y", 3 + 1);
        put("A Z", 6 + 2);
        put("B X", 0 + 1);
        put("B Y", 3 + 2);
        put("B Z", 6 + 3);
        put("C X", 0 + 2);
        put("C Y", 3 + 3);
        put("C Z", 6 + 1);

    }};

    @Override
    public Integer getAnswer(String inputString) {
        String[] turns = inputString.split(System.lineSeparator());

        int points = 0;

        for (String turn : turns) {
            points += pointMap.get(turn);
        }

        return points;
    }
}
