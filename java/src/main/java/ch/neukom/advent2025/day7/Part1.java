package ch.neukom.advent2025.day7;

import ch.neukom.advent2025.util.data.Direction;
import ch.neukom.advent2025.util.data.Position;
import ch.neukom.advent2025.util.inputreaders.InputMapReader;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Part1 {
    public static void main(String[] args) throws IOException {
        try (InputMapReader reader = new InputMapReader(Part1.class)) {
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

        long splitCount = 0;
        Set<Position> beams = Sets.newHashSet(startPosition);
        while (!beams.isEmpty()) {
            Set<Position> newBeams = Sets.newHashSet();
            for (Position position : beams) {
                List<Position> result = move(position, map);
                if (result.size() == 2) {
                    splitCount++;
                }
                newBeams.addAll(result);
            }
            beams = newBeams;
        }
        System.out.printf("The beam was split %s times.\n", splitCount);
    }

    public static List<Position> move(Position position, Map<Position, Character> map) {
        Position newPosition = position.move(Direction.SOUTH);
        Character newPositionData = map.get(newPosition);
        if (newPositionData == null) {
            return List.of();
        } else if (newPositionData == '^') {
            return List.of(
                newPosition.move(Direction.WEST),
                newPosition.move(Direction.EAST)
            );
        } else {
            return List.of(newPosition);
        }
    }
}
