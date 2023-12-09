package puzzles.year23.day5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SeedMap {
    List<SeedMapLine> mapLines;

    public void setMapLines(List<SeedMapLine> mapLines) {
        this.mapLines = mapLines;
    }

    public void sortMapLines() {
        mapLines.sort(Comparator.comparingLong(SeedMapLine::getSourceRangeStart));
    }

    public long getValue(long value) {
        if (value < mapLines.get(0).getSourceRangeStart()) {
            return value;
        };
        for (SeedMapLine line : mapLines) {
            long result = line.mapValue(value);

            if (result != value) {
                return result;
            }
        }

        return value;
    }

    /**
     * Takes in range, gives out list of new ranges
     * @return
     */
    public List<Long[]> getRanges(long start, long end) {
        List<Long[]> initialRanges = new ArrayList<>();
//        List<Long[]> transformedRanges = new ArrayList<>();
        List<Long[]> secondRanges = new ArrayList<>();
        List<Long[]> finalRanges = new ArrayList<>();

        for (SeedMapLine line : mapLines) {
            initialRanges.add(new Long[] {line.getSourceRangeStart(), line.getSourceRangeStart() + line.getRange() - 1, line.getAddingFactor()});
//            transformedRanges.add(new Long[] {line.getSourceRangeStart() + line.getAddingFactor(), line.getSourceRangeStart() + line.getRange() - 1 + line.getAddingFactor()});
        }

        for (int i = 0; i < initialRanges.size(); i++) {
            long rangeStart = initialRanges.get(i)[0];
            long rangeEnd = initialRanges.get(i)[1];
            long scale = initialRanges.get(i)[2];

            if (start > rangeEnd) {
                // Passed the range
            } else if (end < rangeStart) {
                // Before the range
                break;
            } else if (start >= rangeStart && end <= rangeEnd) {
                // Completely inside
                secondRanges.add(new Long[] {start, end, scale});
                break;
            } else if (start < rangeStart && end < rangeEnd) {
                // Offset to left
                secondRanges.add(new Long[] {rangeStart, end, scale});
                break;
            } else if (start >= rangeStart && end > rangeEnd) {
                // Offset to right
                secondRanges.add(new Long[] {start, rangeEnd, scale});
            } else if (start < rangeStart && end > rangeEnd) {
                // Completely outside
                secondRanges.add(new Long[] {rangeStart, rangeEnd, scale});
            }
        }

        // Special case for passed all of them
        if (secondRanges.isEmpty()) {
            finalRanges.add(new Long[] {start, end, 0L});
            return finalRanges;
        }

        // Special case for a little bit at the start
        if (secondRanges.get(0)[0] > start) {
            finalRanges.add(new Long[] {start, secondRanges.get(0)[0] - 1, 0L});
        }

        for (int i = 0; i < secondRanges.size() - 1; i++) {
            finalRanges.add(secondRanges.get(i));

            if (secondRanges.get(i)[1] + 1 != secondRanges.get(i+1)[0]) {
                // If there is space
                finalRanges.add(new Long[] {secondRanges.get(i)[1] + 1, secondRanges.get(i+1)[0] - 1, 0L});
            }
        }

        finalRanges.add(secondRanges.get(secondRanges.size() - 1));

        if (secondRanges.get(secondRanges.size() - 1)[1] < end) {
            // Special case for a little bit passed
            finalRanges.add(new Long[] {secondRanges.get(secondRanges.size() - 1)[1] + 1, end , 0L});
        }

        return finalRanges;
    }
}
