package puzzles.year23.day10;

public enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    public static Direction comingFrom(int x, int y, int prevX, int prevY) {
        int dX = x - prevX;
        int dY = y - prevY;

        if (dY == -1) {
            return SOUTH;
        } else if (dY == 1) {
            return NORTH;
        } else if (dX == -1) {
            return EAST;
        } else if (dX == 1) {
            return WEST;
        }

        throw new RuntimeException("Can't convert direction");
    }

    public static int[] nextPos(Direction direction, int x, int y) {
        switch (direction) {
            case NORTH -> {
                return new int[]{x, y - 1};
            }
            case SOUTH -> {
                return new int[]{x, y + 1};
            }
            case EAST -> {
                return new int[]{x + 1, y};
            }
            case WEST -> {
                return new int[]{x - 1, y};
            }
        }

        throw new RuntimeException("Shouldn't be able to get here");
    }
}
