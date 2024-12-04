package puzzles.year24.day4;

import util.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Part2 implements Puzzle<Integer>  {
    @Override
    public Integer getAnswer(String inputString) {
        char[][] grid = Arrays.stream(inputString.split(System.lineSeparator())).map(String::toCharArray).toArray(char[][]::new);
        int result = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                char c = grid[i][j];

                if (c != 'A') continue;
                if(isX(grid, i, j)) {
                    result++;
                }
            }
        }

        return result;
    }

    private boolean isX(char[][] grid, int i, int j) {
        return checkLeft(grid, i, j) && checkRight(grid, i, j);
    }

    private boolean checkLeft(char[][] grid, int i, int j) {
        return (step(grid, new int[] {1, 1}, i, j) == 'M' && step(grid, new int[] {-1, -1}, i, j) == 'S') || (step(grid, new int[] {1, 1}, i, j) == 'S' && step(grid, new int[] {-1, -1}, i, j) == 'M');
    }

    private boolean checkRight(char[][] grid, int i, int j) {
        return (step(grid, new int[] {1, -1}, i, j) == 'M' && step(grid, new int[] {-1, 1}, i, j) == 'S') || (step(grid, new int[] {1, -1}, i, j) == 'S' && step(grid, new int[] {-1, 1}, i, j) == 'M');
    }

    private char step(char[][] grid, int[] direction, int i, int j) {
        int iMax = grid.length;
        int jMax = grid[0].length;

        int iIndex = i + direction[0];
        int jIndex = j + direction[1];

        if (iIndex >= iMax || jIndex >= jMax || iIndex < 0 || jIndex < 0) {
            return '~';
        } else {
            return grid[iIndex][jIndex];
        }
    }
}
