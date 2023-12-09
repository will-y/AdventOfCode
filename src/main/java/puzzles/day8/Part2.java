package puzzles.day8;

import util.Puzzle;

import java.util.*;
import java.util.stream.Collectors;

public class Part2 implements Puzzle<Long> {
    @Override
    public Long getAnswer(String inputString) {
        String[] lines = inputString.split("\r\n");
        String instructions = lines[0];
        String[] map = Arrays.copyOfRange(lines, 2, lines.length);

        Map<String, List<String>> lookupMap = new HashMap<>();

        for (String mapLine : map) {
            String[] components = mapLine.split(" ");
            String key = components[0];
            String firstValue = components[2].substring(1, 4);
            String secondValue = components[3].substring(0, 3);
            lookupMap.put(key, List.of(firstValue, secondValue));
        }

        List<String> nodes = getStartingNodes(lookupMap.keySet());

        List<Integer> steps = new ArrayList<>(nodes.stream().map(node -> getSteps(instructions, lookupMap, node)).toList());

        return findLCM(steps);
    }

    private long findLCM(List<Integer> steps) {
        // Get a list of each prime factor
        // Get number with max of that and use that in multiple

        List<Map<Integer, Integer>> primeFactors = steps.stream().map(this::getPrimeFactors).toList();
        Set<Integer> factorsUsed = primeFactors.stream().map(Map::keySet).flatMap(Set::stream).collect(Collectors.toSet());

        long result = 1;

        for (int factor : factorsUsed) {
            int maxExponent = 0;
            for (int i = 0; i < steps.size(); i++) {
                int exponent = primeFactors.get(i).getOrDefault(factor, 0);
                if (exponent > maxExponent) {
                    maxExponent = exponent;
                }
            }
            result *= (long) Math.pow(factor, maxExponent);
        }

        return result;
    }

    private int getSteps(String instructions, Map<String, List<String>> lookupMap, String node) {
        int steps = 0;

        while (true) {
            char character = instructions.charAt(steps % instructions.length());
            int index = character == 'R' ? 1 : 0;
            node = lookupMap.get(node).get(index);
            steps++;

            if (node.endsWith("Z")) {
                return steps;
            }
        }
    }

    private List<String> getStartingNodes(Set<String> nodes) {
        return nodes.stream().filter(node -> node.endsWith("A")).toList();
    }

    private Map<Integer, Integer> getPrimeFactors(int number) {
        Map<Integer, Integer> factors = new HashMap<>();
        int twos = 0;

        while (number % 2 == 0) {
            twos++;
            number /= 2;
        }

        if (twos > 0) {
            factors.put(2, twos);
        }

        for (int i = 3; i <= number; i+=2) {
            int count = 0;
            while (number % i == 0) {
                count++;
                number /= i;
            }

            if (count > 0) {
                factors.put(i, count);
            }
        }

        return factors;
    }
}
