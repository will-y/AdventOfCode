package puzzles.year24.day9

import java.io.Serializable

public data class MutablePair<A, B>(
    public var first: A,
    public var second: B
) : Serializable {

    public override fun toString(): String = "($first, $second)"
}