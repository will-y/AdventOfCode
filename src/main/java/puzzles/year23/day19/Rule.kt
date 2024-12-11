package puzzles.year23.day19

import java.io.Serializable

data class Rule(val default: String, val subRules: List<SubRule>): Serializable {
    override fun toString() = "$subRules | $default"
};

data class SubRule(val character: Char, val condition: Char, val value: Int, val destination: String): Serializable {
    override fun toString() = "$character$condition$value -> $destination"
};
