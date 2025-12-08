package ch.neukom.advent2025.day8;

import ch.neukom.advent2025.util.data.Vector3;
import ch.neukom.advent2025.util.inputreaders.InputResourceReader;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    private static final Pattern INPUT_PATTERN = Pattern.compile("^([0-9]*),([0-9]*),([0-9]*)$");

    private Util() {
    }

    public static void connectJunctionBoxes(Vector3 left, Vector3 right, Map<Vector3, Circuit> circuits) {
        Circuit leftCircuit = circuits.get(left);
        Circuit rightCircuit = circuits.get(right);

        if (leftCircuit == null && rightCircuit != null) {
            circuits.put(left, rightCircuit);
            rightCircuit.increaseSize();
        } else if (rightCircuit == null && leftCircuit != null) {
            circuits.put(right, leftCircuit);
            leftCircuit.increaseSize();
        } else if (leftCircuit == null) {
            Circuit circuit = new Circuit(2);
            circuits.put(left, circuit);
            circuits.put(right, circuit);
        } else if (!leftCircuit.equals(rightCircuit)) {
            Circuit combinedCircuit = Circuit.combine(leftCircuit, rightCircuit);
            circuits.replaceAll((_, circuit) -> {
                if (circuit.equals(leftCircuit) || circuit.equals(rightCircuit)) {
                    return combinedCircuit;
                } else {
                    return circuit;
                }
            });
        }
    }

    public static Table<Vector3, Vector3, Double> calculateDistances(List<Vector3> junctionBoxes) {
        Table<Vector3, Vector3, Double> distances = HashBasedTable.create();
        for (Vector3 left : junctionBoxes) {
            for (Vector3 right : junctionBoxes) {
                if (left != right && !distances.contains(left, right) && !distances.contains(right, left)) {
                    distances.put(left, right, left.distance(right));
                }
            }
        }
        return distances;
    }

    public static List<Vector3> parseJunctionBoxes(InputResourceReader reader) {
        return reader.readInput()
            .map(INPUT_PATTERN::matcher)
            .filter(Matcher::matches)
            .map(pattern -> new Vector3(
                Double.parseDouble(pattern.group(1)),
                Double.parseDouble(pattern.group(2)),
                Double.parseDouble(pattern.group(3))
            ))
            .toList();
    }

    public record Connection(Vector3 left, Vector3 right) {
    }

    public static class Circuit {
        private long size;

        public Circuit(long initialSize) {
            this.size = initialSize;
        }

        public static Circuit combine(Circuit left, Circuit right) {
            return new Circuit(left.getSize() + right.getSize());
        }

        public void increaseSize() {
            increaseSize(1);
        }

        public void increaseSize(long size) {
            this.size += size;
        }

        public long getSize() {
            return size;
        }
    }
}
