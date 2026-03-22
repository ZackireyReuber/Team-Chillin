package game;

/**
 * Utility class containing helper methods for the chess game.
 */
public class Utils {

    /**
     * Private constructor — this is a static utility class and should not be instantiated.
     */
    private Utils() {}

    /**
     * Validates that an input string matches the expected move format "[FROM] [TO]",
     * e.g. "E2 E4". Accepts one or more whitespace characters between the two squares.
     * Each square must be exactly two characters: a letter (A-H) and a digit (1-8).
     *
     * @param input the raw input string from the player (should already be trimmed/uppercased)
     * @return true if the format is valid, false otherwise
     */
    public static boolean isValidMoveFormat(String input) {
        if (input == null) return false;
        // Pattern: letter (A-H), digit (1-8), whitespace, letter (A-H), digit (1-8)
        return input.matches("[A-H][1-8]\\s+[A-H][1-8]");
    }
}
