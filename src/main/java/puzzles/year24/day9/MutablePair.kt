package puzzles.year24.day9

import java.io.Serializable

data class MutablePair<A, B>(
    var first: A,
    var second: B
) : Serializable {

    override fun toString(): String = "($first, $second)"
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MutablePair<*, *>

        if (first != other.first) return false
        if (second != other.second) return false

        return true
    }

    override fun hashCode(): Int {
        var result = first?.hashCode() ?: 0
        result = 31 * result + (second?.hashCode() ?: 0)
        return result
    }


}