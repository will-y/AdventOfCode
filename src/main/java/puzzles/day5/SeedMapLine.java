package puzzles.day5;

public class SeedMapLine {
    private long destinationRangeStart;
    private long sourceRangeStart;
    private long range;

    public SeedMapLine(long destinationRangeStart, long sourceRangeStart, long range) {
        this.destinationRangeStart = destinationRangeStart;
        this.sourceRangeStart = sourceRangeStart;
        this.range = range;
    }

    public long mapValue(long value) {
        if (value < sourceRangeStart + range) {
            return value + (destinationRangeStart - sourceRangeStart);
        }

        return value;
    }

    public long getAddingFactor() {
        return destinationRangeStart - sourceRangeStart;
    }

    public long getDestinationRangeStart() {
        return destinationRangeStart;
    }

    public long getSourceRangeStart() {
        return sourceRangeStart;
    }

    public long getRange() {
        return range;
    }
}
