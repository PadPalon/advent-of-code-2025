package ch.neukom.advent2025.day10;

import ch.neukom.advent2025.util.inputreaders.InputResourceReader;
import org.ssclab.log.SscLevel;
import org.ssclab.log.SscLogger;

import java.io.IOException;

public class Part2 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Part2.class)) {
            run(reader);
        }
    }

    private static void run(InputResourceReader reader) {
        SscLogger.setLevel(SscLevel.OFF);
        double fewestButtonPresses = reader.readInput()
            .map(Machine::fromString)
            .mapToDouble(Machine::findFewestButtonPressesJoltage)
            .sum();
        System.out.printf("The fewest amount of button presses is %.0f\n", fewestButtonPresses);
    }
}
