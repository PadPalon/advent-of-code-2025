package ch.neukom.advent2025.util.data;

import java.util.stream.LongStream;

public record Range(long start, long end) {
    public static Range ofString(String start, String end) {
        return new Range(Long.parseLong(start), Long.parseLong(end));
    }

    public LongStream asStream() {
        return LongStream.rangeClosed(start, end);
    }
}
