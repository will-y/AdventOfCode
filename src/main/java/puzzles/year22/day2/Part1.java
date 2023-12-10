package puzzles.year22.day2;

import util.Puzzle;

import java.util.HashMap;
import java.util.Map;

public class Part1 implements Puzzle<Integer> {
    Map<String, Integer> playingPoints = new HashMap<>() {{
        put("X", 1);
        put("Y", 2);
        put("Z", 3);
    }};

    Map<String, Integer> winPoints = new HashMap<>() {{
        put("A X", 3);
        put("A Y", 6);
        put("A Z", 0);
        put("B X", 0);
        put("B Y", 3);
        put("B Z", 6);
        put("C X", 6);
        put("C Y", 0);
        put("C Z", 3);

    }};

    @Override
    public Integer getAnswer(String inputString) {
        String[] turns = inputString.split(System.lineSeparator());

        int points = 0;

        for (String turn : turns) {
            String[] turnArray = turn.split(" ");
            points += playingPoints.get(turnArray[1]);
            points += winPoints.get(turn);
        }

        return points;
    }
}
