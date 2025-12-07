package ch.neukom.advent2025.util.inputreaders;

import ch.neukom.advent2025.util.characterMap.CharacterMapUtil;
import ch.neukom.advent2025.util.data.Position;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class InputMapReader extends InputResourceReader {
    public InputMapReader(Class<?> clazz) {
        super(clazz);
    }

    public InputMapReader(Class<?> clazz, String filename) {
        super(clazz, filename);
    }

    public Map<Position, Character> readIntoMap() {
        return CharacterMapUtil.buildCharacterMap(this);
    }

    public Map<Position, Character> filterIntoMap(Predicate<String> lineFilter) {
        return CharacterMapUtil.buildFilteredCharacterMap(this, lineFilter);
    }

    public <T> Map<Position, T> readIntoMap(Function<Character, T> transformer) {
        return CharacterMapUtil.buildCharacterMap(this, transformer);
    }

    public <T> Map<Position, T> readIntoMap(BiFunction<Position, Character, T> transformer) {
        return CharacterMapUtil.buildCharacterMap(this, transformer);
    }
}
