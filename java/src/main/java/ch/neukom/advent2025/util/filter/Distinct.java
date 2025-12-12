package ch.neukom.advent2025.util.filter;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Distinct<T, K> implements Predicate<T> {
    private final List<K> seen = Lists.newArrayList();
    private final Function<T, K> function;

    private Distinct(Function<T, K> function) {
        this.function = function;
    }

    public static <T, K> Distinct<T, K> by(Function<T, K> predicate) {
        return new Distinct<>(predicate);
    }

    @Override
    public boolean test(T t) {
        K value = function.apply(t);
        if (seen.contains(value)) {
            return false;
        } else {
            seen.add(value);
            return true;
        }
    }
}
