package ch.neukom.advent2025.day3;

import ch.neukom.advent2025.util.inputreaders.InputResourceReader;

import java.io.IOException;

public class Part1 {
    private static final int BATTERIES_TO_ACTIVATE = 2;

    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Part1.class)) {
            run(reader);
        }
    }

    private static void run(InputResourceReader reader) {
        double summedJoltage = reader.readInput()
            .map(line -> BatteryBank.fromString(line, BATTERIES_TO_ACTIVATE))
            .mapToDouble(BatteryBank::getJoltage)
            .sum();
        System.out.printf("The total joltage is %.0f\n", summedJoltage);
    }
}
