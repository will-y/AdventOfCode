package puzzles.day2;

import util.Puzzle;

public class Part1 implements Puzzle<Integer> {
    private static int MAX_RED = 12;
    private static int MAX_GREEN = 13;
    private static int MAX_BLUE = 14;

    @Override
    public Integer getAnswer(String inputString) {
        String[] games = inputString.split("\n");
        int total = 0;

        for (String s : games) {
            String[] gameFormat = s.split(": ");
            int gameId = Integer.parseInt(gameFormat[0].split(" ")[1]);
            String game = gameFormat[1];
            String[] turns = game.split(";");

            boolean gameValid = true;
            for (String turn : turns) {
                String[] colors = turn.split(",");

                for (int k = 0; k < colors.length; k++) {
                    if (!isValidTurn(colors[k])) {
                        gameValid = false;
                    }
                }
            }
            if (gameValid) {
                total += gameId;
            }
        }

        return total;
    }

    private boolean isValidTurn(String input) {
        String[] colorArray = input.trim().split(" ");
        int amount = Integer.parseInt(colorArray[0]);
        String color = colorArray[1];
        switch (color) {
            case "red" -> {
                return amount <= MAX_RED;
            }
            case "green" -> {
                return amount <= MAX_GREEN;
            }
            case "blue" -> {
                return amount <= MAX_BLUE;
            }
        }

        return false;
    }
}
