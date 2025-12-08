package ch.neukom.advent2025.util.data;

public record Vector3(double x, double y, double z) {
    public double distance(Vector3 other) {
        return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2) + Math.pow(z - other.z, 2));
    }
}
