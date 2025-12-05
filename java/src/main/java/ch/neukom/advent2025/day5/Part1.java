package ch.neukom.advent2025.day5;

import ch.neukom.advent2025.util.data.Range;
import ch.neukom.advent2025.util.inputreaders.InputResourceReader;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class Part1 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Part1.class)) {
            run(reader);
        }
    }

    private static void run(InputResourceReader reader) {
        Predicate<Long> freshPredicate = Util.parseRanges(reader)
            .map(Part1::toPredicate)
            .orElseThrow();
        long freshCount = Util.parseIngredients(reader)
            .filter(freshPredicate)
            .count();
        System.out.printf("There are %s fresh ingredients.\n", freshCount);
    }

    private static Predicate<Long> toPredicate(List<Range> ranges) {
        return value -> ranges.stream().anyMatch(range -> range.start() <= value && range.end() >= value);
    }
}
