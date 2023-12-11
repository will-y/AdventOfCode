package puzzles.year23.day10;

import util.Puzzle;

import java.util.Arrays;

import static puzzles.year23.day10.Direction.*;

public class Part1 implements Puzzle<Integer> {
    @Override
    public Integer getAnswer(String inputString) {
        String[][] grid = Arrays.stream(inputString.split(System.lineSeparator())).map(s -> s.split("")).toArray(String[][]::new);
        int sIndex = inputString.replaceAll(System.lineSeparator(), "").indexOf('S');

        int sX = sIndex % grid[0].length;
        int sY = sIndex / grid.length;

        int[] firstPos = firstPos(grid, sX, sY);

        int distanceToS = backToS(sX, sY, firstPos[0], firstPos[1], sX, sY, grid);

        return distanceToS / 2 + 1;
    }

    private int[] firstPos(String[][] grid, int sX, int sY) {
        char up = grid[sY - 1][sX].charAt(0);

        if (up == '|' || up == '7' || up == 'F') {
            return new int[] {sX, sY - 1};
        }

        char down = grid[sY + 1][sX].charAt(0);

        if (down == '|' || down == 'L' || down == 'J') {
            return new int[] {sX, sY + 1};
        }

        // only checking here because i only have to
        char left = grid[sY][Math.max(sX - 1, 0)].charAt(0);

        if (left == '-' || left == 'J' || left == '7') {
            return new int[] {sX - 1, sY};
        }

        char right = grid[sY][sX + 1].charAt(0);

        if (right == '-' || right == 'L' || right == 'F') {
            return new int[] {sX + 1, sY};
        }

        throw new RuntimeException("Shouldn't get here (end of firstPos)");
    }

    private int backToS(int sX, int sY, int startX, int startY, int initialPrevX, int initialPrevY, String[][] grid) {
        int currentX = startX;
        int currentY = startY;
        int prevX = initialPrevX;
        int prevY = initialPrevY;
        int steps = 0;

        while (true) {
            if (currentX == sX && currentY == sY) {
                break;
            }
            int[] nextPos = nextPos(currentX, currentY, prevX, prevY, grid);
            prevX = currentX;
            prevY = currentY;

            currentX = nextPos[0];
            currentY = nextPos[1];
            steps++;
        }

        return steps;
    }

    private int backToS(int sX, int sY, int x, int y, int prevX, int prevY, String[][] grid, int steps) {
        if (x == sX && y == sY) {
            return steps;
        }

        int[] nextPos = nextPos(x, y, prevX, prevY, grid);

        return backToS(sX, sY, nextPos[0], nextPos[1], x, y, grid, steps + 1);
    }

    private int[] nextPos(int x, int y, int prevX, int prevY, String[][] grid) {
        char current = grid[y][x].charAt(0);

        Direction comingFrom = Direction.comingFrom(x, y, prevX, prevY);

        switch (current) {
            case '|':
                // North to south
                if (comingFrom == NORTH) {
                    return Direction.nextPos(SOUTH, x, y);
                } else {
                    return Direction.nextPos(NORTH, x, y);
                }
            case '-':
                // East to west
                if (comingFrom == EAST) {
                    return Direction.nextPos(WEST, x, y);
                } else {
                    return Direction.nextPos(EAST, x, y);
                }
            case 'L':
                // North to east
                if (comingFrom == NORTH) {
                    return Direction.nextPos(EAST, x, y);
                } else {
                    return Direction.nextPos(NORTH, x, y);
                }
            case 'J':
                // North to west
                if (comingFrom == NORTH) {
                    return Direction.nextPos(WEST, x, y);
                } else {
                    return Direction.nextPos(NORTH, x, y);
                }
            case '7':
                // South to west
                if (comingFrom == SOUTH) {
                    return Direction.nextPos(WEST, x, y);
                } else {
                    return Direction.nextPos(SOUTH, x, y);
                }
            case 'F':
                // South to east
                if (comingFrom == SOUTH) {
                    return Direction.nextPos(EAST, x, y);
                } else {
                    return Direction.nextPos(SOUTH, x, y);
                }
        }

        throw new RuntimeException("Unexpected character: " + current);
    }
}
