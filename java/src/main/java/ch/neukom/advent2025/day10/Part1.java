package ch.neukom.advent2025.day10;

import ch.neukom.advent2025.util.inputreaders.InputResourceReader;

import java.io.IOException;

public class Part1 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Part1.class)) {
            run(reader);
        }
    }

    private static void run(InputResourceReader reader) {
        int fewestButtonPresses = reader.readInput()
            .map(Machine::fromString)
            .mapToInt(Machine::findFewestButtonPresses)
            .sum();
        System.out.printf("The fewest amount of button presses is %s\n", fewestButtonPresses);
    }
}
