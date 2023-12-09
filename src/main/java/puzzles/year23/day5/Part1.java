package puzzles.year23.day5;

import util.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Part1 implements Puzzle<Long> {
    @Override
    public Long getAnswer(String inputString) {
        String[] mapStrings = inputString.split(System.lineSeparator() + System.lineSeparator());

        long[] seeds = Arrays.stream(mapStrings[0].substring(7).split(" ")).mapToLong(Long::parseLong).toArray();

        List<SeedMap> maps = new ArrayList<>();

        for (int i = 1; i < mapStrings.length; i++) {
            SeedMap seedMap = new SeedMap();
            List<SeedMapLine> seedMapLines = new ArrayList<>();
            String[] mapLines = mapStrings[i].split(System.lineSeparator());

            for (int j = 1; j < mapLines.length; j++) {
                long[] params = Arrays.stream(mapLines[j].split(" ")).mapToLong(Long::parseLong).toArray();
                seedMapLines.add(new SeedMapLine(params[0], params[1], params[2]));
            }

            seedMap.setMapLines(seedMapLines);
            seedMap.sortMapLines();
            maps.add(seedMap);
        }

        long min = Long.MAX_VALUE;

        for (int i = 0; i < seeds.length; i++) {
            long value = seeds[i];
            for (int j = 0; j < maps.size(); j++) {
                value = maps.get(j).getValue(value);
            }

            if (value < min) {
                min = value;
            }
        }

        return min;
    }
}
