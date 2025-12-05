import java.util.stream.Collectors

class PuzzleInput {
    static String getPuzzleInput(String puzzleDay) {
        String sessionKey = new File('.session-key').text.trim()
        if (sessionKey != null && !sessionKey.isEmpty()) {
            def puzzleInputUrl = "https://adventofcode.com/2025/day/%s/input".formatted(puzzleDay)
            def inputConnection = new URI(puzzleInputUrl).toURL().openConnection()
            inputConnection.addRequestProperty("Cookie", "session=%s".formatted(sessionKey))
            return new BufferedReader(new InputStreamReader(inputConnection.getInputStream())).lines().collect(Collectors.joining("\n"))
        } else {
            return ''
        }
    }
}
