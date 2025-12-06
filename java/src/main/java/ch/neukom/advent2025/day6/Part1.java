package ch.neukom.advent2025.day6;

import ch.neukom.advent2025.util.inputreaders.InputResourceReader;
import ch.neukom.advent2025.util.splitter.Splitters;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static ch.neukom.advent2025.day6.Util.Problem;

public class Part1 {
    public static void main(String[] args) throws IOException {
        try (InputResourceReader reader = new InputResourceReader(Part1.class)) {
            run(reader);
        }
    }

    private static void run(InputResourceReader reader) {
        List<Problem> problems = Util.parseInitialProblems(reader);

        reader.readInput()
            .takeWhile(line -> line.chars().anyMatch(Character::isDigit))
            .map(Splitters.WHITESPACE_SPLITTER::splitToStream)
            .map(stream -> stream.map(Long::parseLong))
            .forEach(stream -> {
                AtomicInteger index = new AtomicInteger(0);
                stream.forEach(value -> problems.get(index.getAndIncrement()).executeOperation(value));
            });

        long grandTotal = problems.stream()
            .mapToLong(Problem::getValue)
            .sum();

        System.out.printf("The grand total of the problems is %s.\n", grandTotal);
    }
}
