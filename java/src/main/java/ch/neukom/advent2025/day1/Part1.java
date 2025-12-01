package ch.neukom.advent2025.day1;

import ch.neukom.advent2025.util.inputreaders.InputResourceReader;
import com.google.common.collect.Streams;

import java.io.IOException;
import java.util.List;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

public class Part1 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Part1.class)) {
            run(reader);
        }
    }

    private static void run(InputResourceReader reader) {
        long zeroPositionCount = reader.readInput()
            .map(Util::parseDialMovement)
            .gather(Gatherers.fold(() -> List.of(50), (positions, dialMovement) -> {
                int newPosition = Util.calculateNewPosition(dialMovement, positions.getLast());
                return Streams.concat(positions.stream(), Stream.of(newPosition)).toList();
            }))
            .flatMap(List::stream)
            .filter(position -> position == 0)
            .count();
        System.out.printf("The dial pointed at zero %s times%n", zeroPositionCount);
    }
}
