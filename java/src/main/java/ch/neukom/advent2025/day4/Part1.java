package ch.neukom.advent2025.day4;

import ch.neukom.advent2025.util.data.Position;
import ch.neukom.advent2025.util.inputreaders.InputArrayReader;

import java.io.IOException;

public class Part1 {
    public static void main(String[] args) throws IOException {
        try (InputArrayReader reader = new InputArrayReader(Part1.class)) {
            run(reader);
        }
    }

    private static void run(InputArrayReader reader) {
        Boolean[][] paperRolls = reader.readIntoArray(symbol -> symbol.symbol() == '@', Boolean.class);
        int height = (int) reader.getLineCount();
        int width = reader.getFirstLine().length();
        long accessibleCount = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Boolean paperRoll = paperRolls[y][x];
                if (paperRoll) {
                    long adjacentPaperRolls = new Position(x, y)
                        .getAdjacentPositions()
                        .filter(position -> position.isInside(width, height))
                        .filter(position -> paperRolls[position.y()][position.x()])
                        .count();
                    if (adjacentPaperRolls < 4) {
                        accessibleCount++;
                    }
                }
            }
        }
        System.out.printf("There are %s accessible paper rolls.\n", accessibleCount);
    }
}
