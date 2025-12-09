package ch.neukom.advent2025.day9;

import ch.neukom.advent2025.util.data.Position;
import ch.neukom.advent2025.util.inputreaders.InputResourceReader;
import ch.neukom.advent2025.util.splitter.Splitters;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Gatherer;

public class Part1 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Part1.class)) {
            run(reader);
        }
    }

    private static void run(InputResourceReader reader) {
        long largestRectangle = reader.readInput()
            .map(Splitters.COMMA_SPLITTER::splitToList)
            .filter(coordinates -> coordinates.size() == 2)
            .map(coordinates -> new Position(Integer.parseInt(coordinates.getFirst()), Integer.parseInt(coordinates.getLast())))
            .gather(Gatherer.of(Sets::newHashSet, calculateRectangles(), Gatherer.defaultCombiner(), Gatherer.defaultFinisher()))
            .mapToLong(l -> l)
            .max()
            .orElseThrow();
        System.out.printf("The largest rectangle is %s.\n", largestRectangle);
    }

    private static Gatherer.Integrator<Set<Position>, Position, Long> calculateRectangles() {
        return (previousPositions, position, downstream) -> {
            for (Position otherPosition : previousPositions) {
                long width = Math.abs((long) position.x() - (long) otherPosition.x()) + 1;
                long height = Math.abs((long) position.y() - (long) otherPosition.y()) + 1;
                long rectangleArea = width * height;
                downstream.push(rectangleArea);
            }
            previousPositions.add(position);
            return true;
        };
    }
}
