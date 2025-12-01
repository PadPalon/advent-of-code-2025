package ch.neukom.advent2025.day1;

import ch.neukom.advent2025.util.data.Rotation;

public class Util {
    private Util() {
    }

    public static DialMovement parseDialMovement(String line) {
        String direction = line.substring(0, 1);
        Rotation rotation = direction.equals("L") ? Rotation.LEFT : Rotation.RIGHT;
        return new DialMovement(rotation, Integer.parseInt(line.substring(1)));
    }

    static int calculateNewPosition(DialMovement dialMovement, int lastPosition) {
        int newPosition = lastPosition;
        if (dialMovement.rotation() == Rotation.LEFT) {
            newPosition -= dialMovement.distance();
        } else if (dialMovement.rotation() == Rotation.RIGHT) {
            newPosition += dialMovement.distance();
        }
        newPosition %= 100;
        newPosition = newPosition < 0 ? 100 + newPosition : newPosition;
        return newPosition;
    }

    public record DialMovement(Rotation rotation, int distance) {
    }
}
