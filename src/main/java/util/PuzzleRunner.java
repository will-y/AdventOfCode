package util;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PuzzleRunner {
    private static final String RESOURCES = "src/main/resources/";
    public void runPuzzle(int day, boolean part1, boolean runActual) {

        try {
            String testInput = new String(Files.readAllBytes(Paths.get(String.format(RESOURCES + "day%d/part%d-test.txt", day, part1 ? 1 : 2))));
            String input =  new String(Files.readAllBytes(Paths.get(String.format(RESOURCES + "day%d/part%d.txt", day, part1 ? 1 : 2))));

            Class puzzleClass = Class.forName(String.format("puzzles.day%d.Part%d", day, part1 ? 1 : 2));
            Constructor puzzleConstructor = puzzleClass.getConstructor();

            Puzzle puzzle = (Puzzle) puzzleConstructor.newInstance();

            Object testAnswer = puzzle.getAnswer(testInput);
            System.out.println(testAnswer);

            if (runActual) {
                long startTime = System.nanoTime();
                Object realAnswer = puzzle.getAnswer(input);
                long endTime = System.nanoTime();
                System.out.println(realAnswer);
                System.out.println("Answer took " + (float) (endTime - startTime) / 1_000_000 + " miliseconds");
            }


        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
