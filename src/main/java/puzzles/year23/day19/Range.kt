package puzzles.year23.day19

import java.io.Serializable

data class Range(
    val min: Int,
    val max: Int,
): Serializable {
    override fun toString() = "[$min, $max]"
}