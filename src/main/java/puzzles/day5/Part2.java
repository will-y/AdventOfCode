package puzzles.day5;

import util.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// 215108317
public class Part2 implements Puzzle<Long> {
    @Override
    public Long getAnswer(String inputString) {
        String[] mapStrings = inputString.split("\r\n\r\n");

        long[] seeds = Arrays.stream(mapStrings[0].substring(7).split(" ")).mapToLong(Long::parseLong).toArray();

        List<SeedMap> maps = new ArrayList<>();

        for (int i = 1; i < mapStrings.length; i++) {
            SeedMap seedMap = new SeedMap();
            List<SeedMapLine> seedMapLines = new ArrayList<>();
            String[] mapLines = mapStrings[i].split("\r\n");

            for (int j = 1; j < mapLines.length; j++) {
                long[] params = Arrays.stream(mapLines[j].split(" ")).mapToLong(Long::parseLong).toArray();
                seedMapLines.add(new SeedMapLine(params[0], params[1], params[2]));
            }

            seedMap.setMapLines(seedMapLines);
            seedMap.sortMapLines();
            maps.add(seedMap);
        }

        List<Long[]> ranges = new ArrayList<>();

        for (int i = 0; i < seeds.length - 1; i+=2) {
            ranges.add(new Long[] {seeds[i], seeds[i] + seeds[i + 1] - 1, 0L});
        }

        for (SeedMap map : maps) {
            // If takes too long maybe collapse ranges here?
            ranges = ranges.stream().map(longs -> map.getRanges(longs[0] + longs[2], longs[1] + longs[2])).flatMap(List::stream).toList();
        }


        // Only subtract 1 from the initial range when calculating
//        List<Long[]> ranges = maps.get(0).getRanges(60, 100 - 1);

        return ranges.stream().map(longs -> longs[0] + longs[2]).mapToLong(Long::valueOf).min().orElse(0);
    }
}
