package puzzles.year23.day7;

import util.Puzzle;

import java.util.*;

/**
 * Hands:
 * 5 of a kind: 6
 * 4 of a kind: 5
 * full house: 4
 * three of a kind: 3
 * two pair: 2
 * one pair: 1
 * high card: 0
 */
public class Part2 implements Puzzle<Integer> {
    Map<String, Integer> valueMap = new HashMap<>() {{
        put("2", 2);
        put("3", 3);
        put("4", 4);
        put("5", 5);
        put("6", 6);
        put("7", 7);
        put("8", 8);
        put("9", 9);
        put("T", 10);
        put("J", 1);
        put("Q", 12);
        put("K", 13);
        put("A", 14);
    }};

    @Override
    public Integer getAnswer(String inputString) {
        String[] games = inputString.split(System.lineSeparator());

        Object[] sortedGames = Arrays.stream(games).sorted((s1, s2) -> {
            int hand1 = getHand(s1.substring(0, 5));
            int hand2 = getHand(s2.substring(0, 5));

            if (hand1 == hand2) {
                return getBiggerSameHand(s1, s2);
            } else if (hand1 < hand2) {
                return -1;
            } else {
                return 1;
            }
        }).toArray();

        int result = 0;
        for (int i = 0; i < sortedGames.length; i++) {
            int bid = Integer.parseInt(((String)(sortedGames[i])).split(" ")[1]);
            result += bid * (i + 1);
        }

        return result;
    }

    private int getHand(String hand) {
        Map<String, Integer> handToCards = getHandToCards(hand);
        int jokers = handToCards.getOrDefault("J", 0);

        if (jokers == 5) {
            return 6;
        }
        handToCards.remove("J");

        List<Integer> values = new ArrayList<>(handToCards.values().stream().toList());

        int maxIndex = 0;
        int maxValue = 0;
        int i = 0;

        for (Integer value : values) {
            if (value > maxValue) {
                maxValue = value;
                maxIndex = i;
            }
            i++;
        }

        values.set(maxIndex, maxValue + jokers);

        if (values.contains(5)) {
            // 5 of a kind
            return 6;
        } else if (values.contains(4)) {
            // 4 of a kind
            return 5;
        } else if (values.contains(2) && values.contains(3)) {
            // Full house
            return 4;
        } else if (values.contains(3)) {
            // 3 of a kind
            return 3;
        } else if (values.stream().filter(integer -> integer == 2).count() == 2) {
            // 2 pair
            return 2;
        } else if (values.contains(2)) {
            // pair
            return 1;
        } else {
            return 0;
        }
    }

    private Map<String, Integer> getHandToCards(String hand) {
        Map<String, Integer> handToCards = new HashMap<>();
        for (int i = 0; i < hand.length(); i++) {
            String card = String.valueOf(hand.charAt(i));
            if (handToCards.containsKey(card)) {
                handToCards.put(card, handToCards.get(card) + 1);
            } else {
                handToCards.put(card, 1);
            }
        }

        return handToCards;
    }

    private int getBiggerSameHand(String hand1, String hand2) {
        for (int i = 0; i < hand1.length(); i++) {
            int hand1Value = valueMap.get(hand1.substring(i, i+1));
            int hand2Value = valueMap.get(hand2.substring(i, i+1));

            if (hand1Value < hand2Value) {
                return -1;
            } else if (hand1Value > hand2Value) {
                return 1;
            }
        }

        return 0;
    }
}
