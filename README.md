# advent-of-code-2025

Solving puzzles from https://adventofcode.com/2025

One folder per programming language / environment in case I get bored of one.

## JVM

Setup to use Java 25.

### Gradle

Custom task `setup-java-puzzle` allows easy setup of the usual structure I use to
solve the puzzles. The folder `templates` contains the files I start with. Package structure is
`ch.neukom.advent2025.dayX` containing at least one file each for both parts of the puzzle. The
task can also automatically download the puzzle input for the day, if a file `.session-key` with
the session key from the AoC website exists.
