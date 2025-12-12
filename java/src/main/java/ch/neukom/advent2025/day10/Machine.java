package ch.neukom.advent2025.day10;

import ch.neukom.advent2025.util.filter.Distinct;
import ch.neukom.advent2025.util.splitter.Splitters;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.Arrays;
import java.util.List;

public class Machine {
    private final List<Button> buttons;

    private final int[] targetLights;
    private final int[] targetJoltages;

    private final LoadingCache<CacheKey, int[]> lightsCache = CacheBuilder.newBuilder()
        .build(new CacheLoader<>() {
            @Override
            public int[] load(CacheKey key) {
                int[] newLights = Arrays.copyOf(key.lights(), key.lights().length);
                key.button().lights()
                    .forEach(light -> {
                        newLights[light]++;
                        newLights[light] %= 2;
                    });
                return newLights;
            }
        });

    public Machine(List<Light> lights,
                   List<Button> buttons,
                   List<Integer> joltages) {
        this.targetLights = lights.stream().map(Light::target).mapToInt(target -> target ? 1 : 0).toArray();
        this.targetJoltages = joltages.stream().mapToInt(joltage -> joltage).toArray();
        this.buttons = buttons;
    }

    public static Machine fromString(String definition) {
        int lightEndIndex = definition.indexOf(']');
        int joltageStartIndex = definition.indexOf('{');
        int joltageEndIndex = definition.indexOf('}');
        List<Light> lights = definition.substring(1, lightEndIndex)
            .trim()
            .chars()
            .mapToObj(c -> switch (c) {
                case '.' -> Light.targetOff();
                case '#' -> Light.targetOn();
                default -> throw new IllegalStateException("Unexpected value: %s".formatted(c));
            }).toList();
        List<Button> buttons = Splitters.WHITESPACE_SPLITTER.splitToStream(definition.substring(lightEndIndex + 1, joltageStartIndex))
            .map(Button::fromString)
            .toList();
        List<Integer> joltages = Splitters.COMMA_SPLITTER.splitToStream(definition.substring(joltageStartIndex + 1, joltageEndIndex))
            .map(Integer::parseInt)
            .toList();
        return new Machine(lights, buttons, joltages);
    }

    public int findFewestButtonPresses() {
        int buttonPresses = 1;

        List<int[]> sequences = buttons.stream()
            .map(button -> new CacheKey(new int[targetLights.length], button))
            .map(lightsCache::getUnchecked)
            .filter(Distinct.by(Arrays::hashCode))
            .toList();
        while (true) {
            boolean doLightsMatch = sequences.stream()
                .anyMatch(resultingLights -> Arrays.equals(resultingLights, targetLights));
            if (doLightsMatch) {
                return buttonPresses;
            }

            sequences = sequences.stream()
                .flatMap(sequence ->
                    buttons.stream()
                        .map(button -> new CacheKey(sequence, button))
                        .map(lightsCache::getUnchecked)
                )
                .filter(Distinct.by(Arrays::hashCode))
                .toList();
            buttonPresses++;
        }
    }

    public record Light(boolean target, boolean on) {
        public static Light targetOff() {
            return new Light(false, false);
        }

        public static Light targetOn() {
            return new Light(true, false);
        }
    }

    public record Button(List<Integer> lights) {
        public static Button fromString(String definition) {
            List<Integer> lights = Splitters.COMMA_SPLITTER.splitToStream(definition.substring(1, definition.length() - 1))
                .map(Integer::parseInt)
                .toList();
            return new Button(lights);
        }
    }

    private record CacheKey(int[] lights, Button button) {
    }
}
