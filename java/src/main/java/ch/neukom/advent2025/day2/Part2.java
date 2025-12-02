package ch.neukom.advent2025.day2;

import ch.neukom.advent2025.util.data.Range;
import ch.neukom.advent2025.util.inputreaders.InputResourceReader;
import ch.neukom.advent2025.util.splitter.Splitters;

import java.io.IOException;
import java.util.stream.Gatherers;
import java.util.stream.IntStream;

/* honestly surprised there is no pitfall that makes this straightforward approach take forever */
public class Part2 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Part2.class)) {
            run(reader);
        }
    }

    private static void run(InputResourceReader reader) {
        long sum = Splitters.COMMA_SPLITTER.splitToStream(reader.getFirstLine())
            .map(Splitters.DASH_SPLITTER::splitToList)
            .map(rangeLimits -> Range.ofString(rangeLimits.getFirst(), rangeLimits.getLast()))
            .flatMapToLong(Range::asStream)
            .mapToObj(String::valueOf)
            .filter(Part2::consistsOfRepeatedParts)
            .mapToLong(Long::parseLong)
            .sum();
        System.out.printf("The sum of all invalid ids is %s\n", sum);
    }

    private static boolean consistsOfRepeatedParts(String id) {
        return IntStream.rangeClosed(1, id.length() / 2)
            .filter(size -> id.length() % size == 0)
            .mapToObj(Gatherers::<Integer>windowFixed)
            .mapToLong(gatherer -> id.chars().boxed().gather(gatherer).distinct().count())
            .filter(count -> count == 1)
            .findAny()
            .isPresent();
    }
}
