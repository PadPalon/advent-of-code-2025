package ch.neukom.advent2025.day1;

import ch.neukom.advent2025.util.data.Rotation;
import ch.neukom.advent2025.util.inputreaders.InputResourceReader;
import com.google.common.collect.Streams;

import java.io.IOException;
import java.util.List;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

public class Part2 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Part2.class)) {
            run(reader);
        }
    }

    private static void run(InputResourceReader reader) {
        long zeroPositionCount = reader.readInput()
            .map(Util::parseDialMovement)
            .gather(Gatherers.fold(() -> List.of(new Position(50, 0)), (positions, dialMovement) -> {
                int lastPosition = positions.getLast().position();

                int newPosition = Util.calculateNewPosition(dialMovement, lastPosition);

                int passesThroughZero = dialMovement.distance() / 100;
                if (
                    newPosition == 0
                        || dialMovement.rotation() == Rotation.LEFT && lastPosition != 0 && newPosition > lastPosition
                        || dialMovement.rotation() == Rotation.RIGHT && lastPosition != 0 && newPosition < lastPosition
                ) {
                    passesThroughZero++;
                }

                return Streams.concat(positions.stream(), Stream.of(new Position(newPosition, passesThroughZero))).toList();
            }))
            .flatMap(List::stream)
            .mapToInt(Position::passesThroughZero)
            .sum();
        System.out.printf("The dial passed through zero %s times%n", zeroPositionCount);
    }

    private record Position(int position, int passesThroughZero) {
    }
}
