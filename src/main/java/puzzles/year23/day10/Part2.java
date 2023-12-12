package puzzles.year23.day10;

import util.Puzzle;

import java.util.*;

import static puzzles.year23.day10.Direction.*;

public class Part2 implements Puzzle<Integer> {
    // New Idea
    // Find a dot, take everything that touches it
    @Override
    public Integer getAnswer(String inputString) {
        String[][] grid = Arrays.stream(inputString.split(System.lineSeparator())).map(s -> s.split("")).toArray(String[][]::new);
        int sIndex = inputString.replaceAll(System.lineSeparator(), "").indexOf('S');

        int sX = sIndex % grid[0].length;
        int sY = sIndex / grid[0].length;

        int[] firstPos = firstPos(grid, sX, sY);
        List<int[]> points = new ArrayList<>();
        points.add(new int[]{sX, sY});

        int distanceToS = backToS(sX, sY, firstPos[0], firstPos[1], sX, sY, grid, points);

        String[][] doubleGrid = new String[grid.length * 2][grid[0].length * 2];

        for (int i = 0; i < points.size(); i++) {
            int[] point = points.get(i);
            int[] nextPoint = i == points.size() - 1 ? points.get(0) : points.get(i + 1);

            doubleGrid[point[1] * 2][point[0] * 2] = "*";

            doubleGrid[point[1] * 2 + (nextPoint[1] - point[1])][point[0] * 2 + (nextPoint[0] - point[0])] = "*";
        }

        for (int i = 0; i < doubleGrid.length; i++) {
            for (int j = 0; j < doubleGrid[0].length; j++) {
                if ("*".equals(doubleGrid[i][j])) {
//                    System.out.print("*");
                } else {
                    doubleGrid[i][j] = ".";
//                    System.out.print(".");
                }
            }
//            System.out.println("");
        }

        markSpots(doubleGrid);

//        for (int i = 0; i < doubleGrid.length; i++) {
//            for (int j = 0; j < doubleGrid[0].length; j++) {
//                System.out.print(doubleGrid[i][j]);
//            }
//            System.out.println("");
//        }

        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (doubleGrid[i * 2][j * 2].equals("I")) {
                    count++;
                }
            }
        }

        return count;
    }

    private void markSpots(String[][] doubleGrid) {
        int[] index;

        do {
            index = indexOf(doubleGrid, ".");
            markFrom(doubleGrid, index[0], index[1]);
        } while (index[0] != -1);
    }

    private int[] indexOf(String[][] doubleGrid, String find) {
        for (int i = 0; i < doubleGrid.length; i++) {
            for (int j = 0; j < doubleGrid[0].length; j++) {
                if (doubleGrid[i][j].equals(find)) {
                    return new int[] {j, i};
                }
            }
        }

        return new int[] {-1, -1};
    }

    private void markFrom(String[][] doubleGrid, int x, int y) {
        Queue<int[]> points = new LinkedList<>();
        List<int[]> hitPoints = new ArrayList<>();
        points.add(new int[]{x, y});
        boolean out = false;

        while (!points.isEmpty()) {
            int[] point = points.poll();
            if (point[1] >= doubleGrid.length || point[0] >= doubleGrid[0].length || point[0] < 0 || point[1] < 0) {
                out = true;
                continue;
            }
            if (doubleGrid[point[1]][point[0]].equals(".")) {
                hitPoints.add(point);
                points.add(new int[] {point[0] + 1, point[1]});
                points.add(new int[] {point[0] - 1, point[1]});
                points.add(new int[] {point[0], point[1] + 1});
                points.add(new int[] {point[0], point[1] - 1});
                doubleGrid[point[1]][point[0]] = "X";
            }
        }

        if (out) {
            for (int[] point : hitPoints) {
                doubleGrid[point[1]][point[0]] = "O";
            }
        } else {
            for (int[] point : hitPoints) {
                doubleGrid[point[1]][point[0]] = "I";
            }
        }
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

    private int backToS(int sX, int sY, int startX, int startY, int initialPrevX, int initialPrevY, String[][] grid, List<int[]> points) {
        int currentX = startX;
        int currentY = startY;
        int prevX = initialPrevX;
        int prevY = initialPrevY;
        int steps = 0;

        while (true) {
            if (currentX == sX && currentY == sY) {
                break;
            }
            points.add(new int[] {currentX, currentY});
            int[] nextPos = nextPos(currentX, currentY, prevX, prevY, grid);
            prevX = currentX;
            prevY = currentY;

            currentX = nextPos[0];
            currentY = nextPos[1];

            steps++;
        }

        return steps;
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
