package util;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PuzzleRunner {
    private static final String RESOURCES = "src/main/resources/";
    public void runPuzzle(int year, int day, boolean part1, boolean runActual) {

        try {
            String testInput = new String(Files.readAllBytes(Paths.get(String.format(RESOURCES + "year%d/day%d/part%d-test.txt", year, day, part1 ? 1 : 2))));
            String input =  new String(Files.readAllBytes(Paths.get(String.format(RESOURCES + "year%d/day%d/input.txt", year, day))));

            Class puzzleClass = Class.forName(String.format("puzzles.year%d.day%d.Part%d", year, day, part1 ? 1 : 2));
            Constructor puzzleConstructor = puzzleClass.getConstructor();

            Puzzle puzzle = (Puzzle) puzzleConstructor.newInstance();

            Object testAnswer = puzzle.getAnswer(testInput);
            System.out.println("Test Answer: " + testAnswer);

            if (runActual) {
                long startTime = System.nanoTime();
                Object realAnswer = puzzle.getAnswer(input);
                long endTime = System.nanoTime();
                System.out.println("Real Answer: " + realAnswer);
                System.out.println("Answer took " + (float) (endTime - startTime) / 1_000_000 + " milliseconds");
            }


        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
