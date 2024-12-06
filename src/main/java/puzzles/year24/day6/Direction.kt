package puzzles.year24.day6

enum class Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    companion object {
        fun comingFrom(x: Int, y: Int, prevX: Int, prevY: Int): Direction {
            val dX = x - prevX
            val dY = y - prevY

            if (dY == -1) {
                return SOUTH
            } else if (dY == 1) {
                return NORTH
            } else if (dX == -1) {
                return EAST
            } else if (dX == 1) {
                return WEST
            }

            throw RuntimeException("Can't convert direction")
        }

        fun nextPos(direction: Direction?, pos: Pair<Int, Int>): Pair<Int, Int> {
            when (direction) {
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

                null -> throw RuntimeException("Shouldn't be able to get here")
            }
        }

        fun nextDirection(direction: Direction): Direction {
            return when (direction) {
                NORTH -> EAST;
                EAST -> SOUTH;
                SOUTH -> WEST;
                WEST -> NORTH;
            }
        }
    }
}
