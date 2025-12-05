package ch.neukom.advent2025.day5;

import ch.neukom.advent2025.util.data.Range;
import ch.neukom.advent2025.util.inputreaders.InputResourceReader;
import ch.neukom.advent2025.util.splitter.Splitters;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

public class Util {
    private Util() {
    }

    public static Optional<List<Range>> parseRanges(InputResourceReader reader) {
        return reader.readInput()
            .takeWhile(line -> !line.isEmpty())
            .map(Splitters.DASH_SPLITTER::splitToList)
            .filter(limits -> limits.size() == 2)
            .map(limits -> Range.ofString(limits.getFirst(), limits.getLast()))
            .sorted(Comparator.comparingLong(Range::start))
            .gather(Gatherers.fold(List::<Range>of, Range::combine))
            .findAny();
    }

    public static Stream<Long> parseIngredients(InputResourceReader reader) {
        return reader.readInput()
            .dropWhile(line -> !line.isEmpty())
            .skip(1)
            .map(Long::parseLong);
    }
}
