package ch.neukom.advent2025.day2;

import ch.neukom.advent2025.util.data.Range;
import ch.neukom.advent2025.util.inputreaders.InputResourceReader;
import ch.neukom.advent2025.util.splitter.Splitters;

import java.io.IOException;

public class Part1 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Part1.class)) {
            run(reader);
        }
    }

    private static void run(InputResourceReader reader) {
        long sum = Splitters.COMMA_SPLITTER.splitToStream(reader.getFirstLine())
            .map(Splitters.DASH_SPLITTER::splitToList)
            .map(rangeLimits -> Range.ofString(rangeLimits.getFirst(), rangeLimits.getLast()))
            .flatMapToLong(Range::asStream)
            .mapToObj(String::valueOf)
            .filter(id -> id.length() % 2 == 0)
            .filter(id -> id.substring(0, id.length() / 2).equals(id.substring(id.length() / 2)))
            .mapToLong(Long::parseLong)
            .sum();
        System.out.printf("The sum of all invalid ids is %s\n", sum);
    }
}
