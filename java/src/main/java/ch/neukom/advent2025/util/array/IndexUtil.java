package ch.neukom.advent2025.util.array;

import java.util.stream.IntStream;

public class IndexUtil {
    private IndexUtil() {
    }

    public static IntStream getIndexes(int[] array, int toFind) {
        IntStream.Builder builder = IntStream.builder();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == toFind) {
                builder.add(i);
            }
        }
        return builder.build();
    }
}
