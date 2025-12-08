package ch.neukom.advent2025.day8;

import ch.neukom.advent2025.util.data.Vector3;
import ch.neukom.advent2025.util.inputreaders.InputResourceReader;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static ch.neukom.advent2025.day8.Util.*;

public class Part2 {

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Part2.class)) {
            run(reader);
        }
    }

    private static void run(InputResourceReader reader) {
        List<Vector3> junctionBoxes = parseJunctionBoxes(reader);
        Table<Vector3, Vector3, Double> distances = calculateDistances(junctionBoxes);

        Map<Vector3, Circuit> circuits = Maps.newHashMap();
        AtomicReference<Connection> lastConnection = new AtomicReference<>();
        distances.cellSet()
            .stream()
            .sorted(Comparator.comparingDouble(Table.Cell::getValue))
            .takeWhile(_ -> circuits.isEmpty() || circuits.values().stream().anyMatch(circuit -> circuit.getSize() != junctionBoxes.size()))
            .forEach(cell -> {
                Vector3 left = cell.getColumnKey();
                Vector3 right = cell.getRowKey();

                Connection connection = new Connection(left, right);
                lastConnection.set(connection);

                connectJunctionBoxes(left, right, circuits);
            });

        Vector3 lastLeft = lastConnection.get().left();
        Vector3 lastRight = lastConnection.get().right();
        System.out.printf("The multiplied coordinates are %.0f.\n", lastLeft.x() * lastRight.x());
    }
}
