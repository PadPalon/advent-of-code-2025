package ch.neukom.advent2025.util.streams;

import java.util.stream.IntStream;

public class IntStreamUtil {
    private IntStreamUtil() {
    }

    public static IntStream reverseRange(int from, int to) {
        return IntStream.iterate(from, i -> i > to, i -> i - 1);
    }
}
