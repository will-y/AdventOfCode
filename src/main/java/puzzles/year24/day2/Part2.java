package puzzles.year24.day2;

import util.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Part2 implements Puzzle<Integer> {
    @Override
    public Integer getAnswer(String inputString) {
        String[] reports = inputString.split("\r\n");

        return (int) Arrays.stream(reports).filter(this::valid).count();
    }

    private boolean valid(String levels) {
        List<Integer> intLevels = Arrays.stream(levels.split(" ")).map(Integer::parseInt).toList();

        List<List<Integer>> permutations = getPermutations(intLevels);

        for (List<Integer> permutation : permutations) {
            if (isIncreasing(permutation) || isDecreasing(permutation)) {
                return true;
            }
        }

        return false;
    }

    private List<List<Integer>> getPermutations(List<Integer> levels) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < levels.size(); i++) {
            List<Integer> copy = new ArrayList<>(levels);
            copy.remove(i);
            result.add(copy);
        }

        result.add(levels);
        return result;
    }

    private boolean isDecreasing(List<Integer> levels) {
        for (int i = 1; i < levels.size(); i++) {
            int diff = levels.get(i) - levels.get(i - 1);

            if (diff > 3 || diff < 1) {
                return false;
            }
        }

        return true;
    }

    private boolean isIncreasing(List<Integer> levels) {
        for (int i = 1; i < levels.size(); i++) {
            int diff = levels.get(i - 1) - levels.get(i);

            if (diff > 3 || diff < 1) {
                return false;
            }
        }

        return true;
    }

//    private boolean isDecreasing(int[] levels) {
//        boolean usedDampener = false;
//        for (int i = 1; i < levels.length; i++) {
//            int diff = levels[i] - levels[i - 1];
//
//            if (diff > 3 || diff < 1) {
//                if (!usedDampener && i < levels.length - 1) {
//                    int newDif = levels[i + 1] - levels[i - 1];
//
//                    if (newDif > 0 && newDif <= 3) {
//                        usedDampener = true;
//                        continue;
//                    }
//                }
//
//                if (i == 1) {
//                    usedDampener = true;
//                    continue;
//                }
//
//                return i == levels.length - 1 && !usedDampener;
//            }
//        }
//
//        return true;
//    }
//
//    private boolean isIncreasing(int[] levels) {
//        boolean usedDampener = false;
//        for (int i = 1; i < levels.length; i++) {
//            int diff = levels[i - 1] - levels[i];
//
//            if (diff > 3 || diff < 1) {
//                if (!usedDampener && i < levels.length - 1) {
//                    int newDif = levels[i - 1] - levels[i + 1];
//
//                    if (newDif > 0 && newDif <= 3) {
//                        usedDampener = true;
//                        continue;
//                    }
//                }
//
//                if (i == 1) {
//                    usedDampener = true;
//                    continue;
//                }
//
//                return i == levels.length - 1 && !usedDampener;
//            }
//        }
//
//        return true;
//    }
}
