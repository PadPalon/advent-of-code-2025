package ch.neukom.advent2025.day3;

import ch.neukom.advent2025.util.array.IndexUtil;
import ch.neukom.advent2025.util.characters.CharacterConversionUtil;
import ch.neukom.advent2025.util.streams.IntStreamUtil;

import java.util.OptionalInt;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

record BatteryBank(int[] batteries, int batteriesToActivate) {
    public static BatteryBank fromString(String line, int batteriesToActive) {
        int[] batteries = line.chars()
            .map(c -> CharacterConversionUtil.toInteger((char) c))
            .toArray();
        return new BatteryBank(batteries, batteriesToActive);
    }

    public double getJoltage() {
        int[] activeBatterySlots = new int[batteriesToActivate];
        int currentlyFilling = 0;

        while (currentlyFilling < batteriesToActivate) {
            Integer nextIndex = IntStreamUtil.reverseRange(9, 0)
                .mapToObj(getNextIndex(currentlyFilling, activeBatterySlots))
                .filter(OptionalInt::isPresent)
                .map(OptionalInt::getAsInt)
                .findFirst()
                .orElseThrow();
            activeBatterySlots[currentlyFilling] = nextIndex;
            currentlyFilling++;
        }

        return IntStream.range(0, batteriesToActivate)
            .mapToDouble(index -> {
                int batteryToActivate = activeBatterySlots[batteriesToActivate - index - 1];
                return batteries[batteryToActivate] * Math.pow(10, index);
            })
            .sum();
    }

    private IntFunction<OptionalInt> getNextIndex(int currentlyFilling, int[] activeBatterySlots) {
        return number -> IndexUtil.getIndexes(batteries, number)
            .filter(index ->
                {
                    boolean hasEnoughBatteriesLeft = index <= (batteries.length - batteriesToActivate + currentlyFilling);
                    boolean isAfterPreviousBattery = currentlyFilling == 0 || index > activeBatterySlots[currentlyFilling - 1];
                    return hasEnoughBatteriesLeft && isAfterPreviousBattery;
                }
            )
            .min();
    }
}
