package ch.neukom.advent2025.day7;

import ch.neukom.advent2025.util.count.OccurrenceCounter;
import ch.neukom.advent2025.util.data.Direction;
import ch.neukom.advent2025.util.data.Position;
import ch.neukom.advent2025.util.inputreaders.InputMapReader;

import java.io.IOException;
import java.util.Map;
import java.util.stream.IntStream;

public class Part2 {
    public static void main(String[] args) throws IOException {
        try (InputMapReader reader = new InputMapReader(Part2.class)) {
            run(reader);
        }
    }

    private static void run(InputMapReader reader) {
        Map<Position, Character> map = reader.filterIntoMap(line -> line.contains("^") || line.contains("S"));
        Position startPosition = map.entrySet()
            .stream()
            .filter(entry -> entry.getValue() == 'S')
            .map(Map.Entry::getKey)
            .findAny()
            .orElseThrow();

        int maxY = map.keySet().stream().mapToInt(Position::y).max().orElseThrow();
        int maxX = map.keySet().stream().mapToInt(Position::x).max().orElseThrow();
        OccurrenceCounter positionCounts = new OccurrenceCounter();
        positionCounts.add(startPosition);
        for (int y = 1; y <= maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                Position currentPosition = new Position(x, y);
                Position abovePosition = currentPosition.move(Direction.NORTH);
                if (positionCounts.contains(abovePosition)) {
                    Character currentCharacter = map.get(currentPosition);
                    if (currentCharacter == '.') {
                        positionCounts.add(currentPosition, positionCounts.count(abovePosition));
                    } else if (currentCharacter == '^') {
                        positionCounts.add(currentPosition.move(Direction.WEST), positionCounts.count(abovePosition));
                        positionCounts.add(currentPosition.move(Direction.EAST), positionCounts.count(abovePosition));
                    }
                }
            }
        }

        long timelineCount = IntStream.rangeClosed(0, maxX)
            .mapToObj(x -> new Position(x, maxY))
            .mapToLong(positionCounts::count)
            .sum();
        System.out.printf("There are a total of %s timelines.\n", timelineCount);
    }
}
