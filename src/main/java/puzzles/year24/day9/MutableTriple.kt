package puzzles.year24.day9

import java.io.Serializable

data class MutableTriple<A, B, C>(
    var first: A,
    var second: B,
    var third: C
) : Serializable {

    override fun toString(): String = "($first, $second, $third)"
}