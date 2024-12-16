package puzzles.year24.day16

enum class Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    fun clockwise(): Direction {
        return when (this) {
            NORTH -> EAST;
            EAST -> SOUTH;
            SOUTH -> WEST;
            WEST -> NORTH;
        }
    }

    fun counterClockwise(): Direction {
        return when (this) {
            NORTH -> WEST;
            EAST -> NORTH;
            SOUTH -> EAST;
            WEST -> SOUTH;
        }
    }

    fun nextPos(pos: Pair<Int, Int>): Pair<Int, Int> {
        when (this) {
            NORTH -> {
                return Pair(pos.first, pos.second - 1)
            }

            SOUTH -> {
                return Pair(pos.first, pos.second + 1)
            }

            EAST -> {
                return Pair(pos.first + 1, pos.second)
            }

            WEST -> {
                return Pair(pos.first - 1, pos.second)
            }
        }
    }
}