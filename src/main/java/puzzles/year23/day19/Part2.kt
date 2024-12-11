package puzzles.year23.day19

import util.Puzzle

class Part2 : Puzzle<Long?> {
    override fun getAnswer(inputString: String): Long {
        val sections = inputString.split(System.lineSeparator() + System.lineSeparator())
        val ruleString = sections[0];
        val rules: Map<String, Rule> = ruleString.lines().associate(this::mapRule);

        // Includes min and max for now
        val originalInput = Pair("in",
            mutableMapOf(
                Pair('x', Range(1, 4000)),
                Pair('m', Range(1, 4000)),
                Pair('a', Range(1, 4000)),
                Pair('s', Range(1, 4000))
            )
        );

        val accepted = HashSet<Pair<String, Map<Char, Range>>>();
        var stepResult = mutableSetOf(originalInput);
        while (stepResult.isNotEmpty()) {
            stepResult = step(rules, stepResult, accepted);
        }

        return calculateFinalAnswer(accepted);
    }

    private fun step(rules: Map<String, Rule>, inputs: Set<Pair<String, MutableMap<Char, Range>>>, accepted: MutableSet<Pair<String, Map<Char, Range>>>): MutableSet<Pair<String, MutableMap<Char, Range>>> {
        val results = HashSet<Pair<String, MutableMap<Char, Range>>>();
        for (input in inputs) {
            results.addAll(applyRule(rules[input.first]!!, input.first, input));
        }

        val resultsIterator = results.iterator();

        while (resultsIterator.hasNext()) {
            val result = resultsIterator.next()
            if (result.first == "A") {
                resultsIterator.remove();
                accepted.add(result);
            } else if (result.first == "R") {
                resultsIterator.remove();
            }
        }

        return results;
    }

    private fun applyRule(rule: Rule, ruleKey: String, input: Pair<String, MutableMap<Char, Range>>): Set<Pair<String, MutableMap<Char, Range>>> {
        val processed = mutableSetOf<Pair<String, MutableMap<Char, Range>>>();
        var toProcess = mutableSetOf(input);
        for (subRule in rule.subRules) {
            val afterSplit = toProcess.flatMap { i -> splitOnSubRule(ruleKey, subRule, i.second) }
            processed.addAll(afterSplit.filter { i -> i.first != ruleKey })
            toProcess = afterSplit.filter {i -> i.first == ruleKey}.toMutableSet()
            toProcess.removeAll(afterSplit.filter { i -> i.first != ruleKey }.toSet())

            if (toProcess.isEmpty()) break;
        }

        // Need to be mapped to the default
        processed.addAll(toProcess.map { i -> Pair(rule.default, i.second) });
        return processed;
    }

    private fun splitOnSubRule(ruleKey: String, subRule: SubRule, input: MutableMap<Char, Range>): MutableSet<Pair<String, MutableMap<Char, Range>>> {
        val character = subRule.character;
        val min = input[character]!!.min;
        val max = input[character]!!.max;
        val threshold = subRule.value;

        if (subRule.condition == '>') {
            // All out of it
            if (threshold >= max) {
                return mutableSetOf();
            } else if (min > threshold) { // All in it
                return mutableSetOf(Pair(subRule.destination, input));
            } else { // Need to chop it up
                val passesRuleMap = HashMap(input);
                passesRuleMap[character] = Range(threshold + 1, max);
                val passesRule = Pair(subRule.destination, passesRuleMap);

                val noPassRuleMap = HashMap(input);
                noPassRuleMap[character] = Range(min, threshold);
                val noPassRule = Pair(ruleKey, noPassRuleMap);
                return mutableSetOf(passesRule, noPassRule);
            }
        } else {
            // All out of it
            if (threshold <= min) {
                return mutableSetOf();
            } else if (max < threshold) { // All in it
                return mutableSetOf(Pair(subRule.destination, input));
            } else { // Need to chop it up
                val passesRuleMap = HashMap(input);
                passesRuleMap[character] = Range(min, threshold - 1);
                val passesRule = Pair(subRule.destination, passesRuleMap);

                val noPassRuleMap = HashMap(input);
                noPassRuleMap[character] = Range(threshold, max);
                val noPassRule = Pair(ruleKey, noPassRuleMap);
                return mutableSetOf(passesRule, noPassRule);
            }
        }
    }

    private fun calculateFinalAnswer(accepted: HashSet<Pair<String, Map<Char, Range>>>): Long {
        var result = 0L;

        for (step in accepted) {
            result += (step.second['x']!!.max - step.second['x']!!.min + 1).toLong() *
                    (step.second['m']!!.max - step.second['m']!!.min + 1).toLong() *
                    (step.second['a']!!.max - step.second['a']!!.min + 1).toLong() *
                    (step.second['s']!!.max - step.second['s']!!.min + 1).toLong()
        }

        return result;
    }

    private fun mapRule(ruleString: String): Pair<String, Rule> {
        val label = ruleString.substring(0, ruleString.indexOf('{'));
        val rule = ruleString.substring(ruleString.indexOf('{') + 1, ruleString.lastIndexOf('}')).split(',');
        val default = rule[rule.size - 1];
        val subRules: MutableList<SubRule> = mutableListOf();
        for (i in 0..rule.size - 2) {
            val subRuleStringElements = rule[i].split(':');
            subRules.add(
                SubRule(
                    subRuleStringElements[0][0],
                    subRuleStringElements[0][1],
                    subRuleStringElements[0].substring(2).toInt(),
                    subRuleStringElements[1]
                )
            )
        }

        return Pair(label, Rule(default, subRules));
    }
}