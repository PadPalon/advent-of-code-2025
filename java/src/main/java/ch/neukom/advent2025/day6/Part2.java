package ch.neukom.advent2025.day6;

import ch.neukom.advent2025.util.inputreaders.InputArrayReader;

import java.io.IOException;
import java.util.List;

public class Part2 {
    public static void main(String[] args) throws IOException {
        try (InputArrayReader reader = new InputArrayReader(Part2.class)) {
            run(reader);
        }
    }

    private static void run(InputArrayReader reader) {
        List<Util.Problem> problems = Util.parseInitialProblems(reader);

        long width = reader.getMaxLineLength();
        long height = reader.getLineCount() - 1;
        Character[][] characters = reader.readIntoArray(InputArrayReader.Symbol::symbol, Character.class);

        // swap x- and y-axis and parse results as numbers
        int problemCount = 0;
        for (int x = 0; x < width; x++) {
            StringBuilder lineBuilder = new StringBuilder();
            for (int y = 0; y < height; y++) {
                Character digit = characters[y][x];
                if (digit != null) {
                    lineBuilder.append(digit);
                }
            }
            String line = lineBuilder.toString().trim();

            if (line.isEmpty()) {
                problemCount++;
            } else {
                long value = Long.parseLong(line);
                problems.get(problemCount).executeOperation(value);
            }
        }

        long grandTotal = problems.stream()
            .mapToLong(Util.Problem::getValue)
            .sum();

        System.out.printf("The grand total of the problems is %s.\n", grandTotal);
    }
}
