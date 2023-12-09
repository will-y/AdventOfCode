package puzzles.year23.day2;

import util.Puzzle;

public class Part2 implements Puzzle<Integer> {
    private static int MAX_RED = 12;
    private static int MAX_GREEN = 13;
    private static int MAX_BLUE = 14;

    @Override
    public Integer getAnswer(String inputString) {
        String[] games = inputString.split("\n");
        int total = 0;

        for (int i = 0; i < games.length; i++) {
            String[] gameFormat = games[i].split(": ");
            int gameId = Integer.parseInt(gameFormat[0].split(" ")[1]);
            String game = gameFormat[1];
            String[] turns = game.split(";");

            int minRed = 0;
            int minBlue = 0;
            int minGreen = 0;

            for (String turn : turns) {
                String[] colors = turn.split(",");

                for (String s : colors) {
                    String[] colorArray = s.trim().split(" ");
                    int amount = Integer.parseInt(colorArray[0]);
                    String color = colorArray[1];
                    switch (color) {
                        case "red" -> {
                            if (amount > minRed) {
                                minRed = amount;
                            }
                        }
                        case "green" -> {
                            if (amount > minGreen) {
                                minGreen = amount;
                            }
                        }
                        case "blue" -> {
                            if (amount > minBlue) {
                                minBlue = amount;
                            }
                        }
                    }
                }
            }

            total += minRed * minBlue * minGreen;
        }

        return total;
    }
}
