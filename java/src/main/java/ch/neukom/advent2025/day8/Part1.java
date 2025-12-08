package ch.neukom.advent2025.day8;

import ch.neukom.advent2025.util.data.Vector3;
import ch.neukom.advent2025.util.inputreaders.InputResourceReader;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static ch.neukom.advent2025.day8.Util.*;

public class Part1 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Part1.class)) {
            run(reader);
        }
    }

    private static void run(InputResourceReader reader) {
        List<Vector3> junctionBoxes = parseJunctionBoxes(reader);
        Table<Vector3, Vector3, Double> distances = calculateDistances(junctionBoxes);

        Map<Vector3, Circuit> circuits = Maps.newHashMap();
        distances.cellSet()
            .stream()
            .sorted(Comparator.comparingDouble(Table.Cell::getValue))
            .limit(1000)
            .forEach(cell -> {
                Vector3 left = cell.getColumnKey();
                Vector3 right = cell.getRowKey();
                connectJunctionBoxes(left, right, circuits);
            });

        long circuitSize = circuits.values()
            .stream()
            .distinct()
            .map(Circuit::getSize)
            .sorted(Comparator.reverseOrder())
            .limit(3)
            .mapToLong(Long::valueOf)
            .reduce(1L, Math::multiplyExact);

        System.out.printf("The multiplied circuit sizes are %s.\n", circuitSize);
    }
}
