package ch.neukom.advent2025.util.count;

import com.google.common.collect.Maps;

import java.util.Map;

public class OccurrenceCounter {
    private final Map<Object, Long> occurrences = Maps.newHashMap();

    public void add(Object element) {
        add(element, 1);
    }

    public void add(Object element, long count) {
        Long previous = occurrences.getOrDefault(element, 0L);
        occurrences.put(element, previous + count);
    }

    public long count(Object element) {
        return occurrences.getOrDefault(element, 0L);
    }

    public boolean contains(Object element) {
        return occurrences.containsKey(element);
    }
}
