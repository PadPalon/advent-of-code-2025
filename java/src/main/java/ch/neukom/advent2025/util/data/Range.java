package ch.neukom.advent2025.util.data;

import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public record Range(long start, long end) {
    public static Range ofString(String start, String end) {
        return new Range(Long.parseLong(start), Long.parseLong(end));
    }

    public static List<Range> combine(List<Range> ranges, Range newRange) {
        if (ranges.isEmpty()) {
            return List.of(newRange);
        } else {
            Range previousRange = ranges.getLast();
            if (previousRange.end() >= newRange.start()) {
                if (previousRange.end() <= newRange.end()) {
                    Range combinedRange = new Range(previousRange.start(), newRange.end());
                    return Stream.concat(
                        ranges.stream().limit(ranges.size() - 1),
                        Stream.of(combinedRange)
                    ).toList();
                } else {
                    return ranges;
                }
            } else {
                return Stream.concat(
                    ranges.stream(),
                    Stream.of(newRange)
                ).toList();
            }
        }
    }

    public LongStream asStream() {
        return LongStream.rangeClosed(start, end);
    }

    public long count() {
        return end - start + 1;
    }
}
