package ch.neukom.advent2025.day4;

import ch.neukom.advent2025.util.data.Position;
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
        Boolean[][] paperRolls = reader.readIntoArray(symbol -> symbol.symbol() == '@', Boolean.class);
        int height = (int) reader.getLineCount();
        int width = reader.getFirstLine().length();
        long removedCount = 0;
        boolean anyRemoved;
        do {
            anyRemoved = false;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Boolean paperRoll = paperRolls[y][x];
                    if (paperRoll) {
                        List<Position> adjacentPaperRolls = new Position(x, y)
                            .getAdjacentPositions()
                            .filter(position -> position.isInside(width, height))
                            .filter(position -> paperRolls[position.y()][position.x()])
                            .toList();
                        if (adjacentPaperRolls.size() < 4) {
                            removedCount++;
                            paperRolls[y][x] = false;
                            anyRemoved = true;
                        }
                    }
                }
            }
        } while (anyRemoved);
        System.out.printf("We can remove %s paper rolls.\n", removedCount);
    }
}
