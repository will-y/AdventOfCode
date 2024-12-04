package puzzles.year24.day4;

import util.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Part1 implements Puzzle<Integer> {
    private static final List<int[]> DIRECTIONS = List.of(new int[] {0, 1}, new int[] {0, -1}, new int[] {1, 0}, new int[] {-1, 0}, new int[] {1, 1}, new int[] {1, -1}, new int[] {-1, 1}, new int[] {-1, -1});

    @Override
    public Integer getAnswer(String inputString) {
        char[][] grid = Arrays.stream(inputString.split(System.lineSeparator())).map(String::toCharArray).toArray(char[][]::new);
        int result = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                char c = grid[i][j];

                if (c != 'X') continue;

                result += count(grid, i, j);
            }
        }
        return result;
    }

    private int count(char[][] grid, int i, int j) {
        int count = 0;

        for (int[] direction : DIRECTIONS) {
            char m = step(grid, direction, i, j, 1);
            if (m == 'M') {
                char a = step(grid, direction, i, j, 2);
                if (a == 'A') {
                    char x = step(grid, direction, i, j, 3);
                    if (x == 'S') {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private char step(char[][] grid, int[] direction, int i, int j, int amount) {
        int iMax = grid.length;
        int jMax = grid[0].length;

        int iIndex = i + direction[0] * amount;
        int jIndex = j + direction[1] * amount;

        if (iIndex >= iMax || jIndex >= jMax || iIndex < 0 || jIndex < 0) {
            return '~';
        } else {
            return grid[iIndex][jIndex];
        }
    }
}
