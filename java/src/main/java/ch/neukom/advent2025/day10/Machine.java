package ch.neukom.advent2025.day10;

import ch.neukom.advent2025.util.filter.Distinct;
import ch.neukom.advent2025.util.splitter.Splitters;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.ssclab.pl.milp.*;

import java.util.Arrays;
import java.util.List;

import static java.lang.Double.NaN;
import static org.ssclab.pl.milp.ConsType.EQ;
import static org.ssclab.pl.milp.ConsType.INT;

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

    public double findFewestButtonPressesJoltage() {
        try {
            return solveEquationSystem();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private double solveEquationSystem() throws Exception {
        double[][] equations = setupEquations();
        ListConstraints constraints = setupConstraints(equations);
        LinearObjectiveFunction objectiveFunction = setupObjectiveFunction();
        MILP equationSystem = new MILP(objectiveFunction, constraints);

        SolutionType solutionType = equationSystem.resolve();
        if (solutionType == SolutionType.OPTIMAL) {
            Solution solution = equationSystem.getSolution();
            return solution.getOptimumValue();
        } else {
            throw new IllegalStateException("No optimal solution found");
        }
    }

    private double[][] setupEquations() {
        double[][] data = new double[targetJoltages.length + 1][];
        for (int row = 0; row < targetJoltages.length; row++) {
            double[] values = new double[buttons.size()];
            data[row] = values;
            for (int column = 0; column < buttons.size(); column++) {
                Button button = buttons.get(column);
                values[column] = button.lights().contains(row) ? 1 : 0;
            }
        }
        data[targetJoltages.length] = createOnesArray(buttons);
        return data;
    }

    private ListConstraints setupConstraints(double[][] equations) throws LPException {
        ListConstraints constraints = new ListConstraints();
        for (int i = 0; i < equations.length; i++) {
            constraints.add(new Constraint(
                equations[i],
                i < targetJoltages.length ? EQ : INT,
                i < targetJoltages.length ? targetJoltages[i] : NaN
            ));
        }
        return constraints;
    }

    private LinearObjectiveFunction setupObjectiveFunction() throws LPException {
        return new LinearObjectiveFunction(createOnesArray(buttons), GoalType.MIN);
    }

    private double[] createOnesArray(List<?> sizeSource) {
        double[] weights = new double[sizeSource.size()];
        Arrays.fill(weights, 1);
        return weights;
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
