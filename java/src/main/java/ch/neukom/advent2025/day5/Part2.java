package ch.neukom.advent2025.day5;

import ch.neukom.advent2025.util.data.Range;
import ch.neukom.advent2025.util.inputreaders.InputResourceReader;

import java.io.IOException;
import java.util.Collection;

public class Part2 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Part2.class)) {
            run(reader);
        }
    }

    private static void run(InputResourceReader reader) {
        long allFreshCount = Util.parseRanges(reader)
            .stream()
            .flatMap(Collection::stream)
            .mapToLong(Range::count)
            .sum();
        System.out.printf("There are %s possible fresh ingredients.\n", allFreshCount);
    }
}
