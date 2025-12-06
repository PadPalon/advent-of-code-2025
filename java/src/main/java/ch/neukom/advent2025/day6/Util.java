package ch.neukom.advent2025.day6;

import ch.neukom.advent2025.util.inputreaders.InputResourceReader;
import ch.neukom.advent2025.util.splitter.Splitters;

import java.util.List;

public class Util {
    private Util() {
    }

    public static List<Problem> parseInitialProblems(InputResourceReader reader) {
        return reader.readInput()
            .dropWhile(line -> line.chars().anyMatch(Character::isDigit))
            .findAny()
            .stream()
            .flatMap(Splitters.WHITESPACE_SPLITTER::splitToStream)
            .map(symbol -> switch (symbol) {
                case "*" -> Util.Operation.MULTIPLY;
                case "+" -> Util.Operation.ADD;
                default -> throw new IllegalStateException("Unexpected value: %s".formatted(symbol));
            })
            .map(Util.Problem::new)
            .toList();
    }

    public enum Operation {
        ADD, MULTIPLY
    }

    public static class Problem {
        private final Operation operation;

        private long value;
        private boolean started = false;

        public Problem(Operation operation) {
            this.operation = operation;
        }

        public void executeOperation(long operand) {
            if (!started) {
                started = true;
                value = operand;
            } else {
                value = switch (operation) {
                    case ADD -> value + operand;
                    case MULTIPLY -> value * operand;
                };
            }
        }

        public long getValue() {
            return value;
        }
    }
}
