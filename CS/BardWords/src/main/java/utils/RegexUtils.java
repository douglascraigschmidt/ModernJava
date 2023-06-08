package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A Java utility class that provides helper methods for Java regular
 * expressions.
 */
public final class RegexUtils {
    /**
     * A Java utility class should have a private constructor.
     */
    private RegexUtils() {}

    /**
     * @return The first line of the {@code input}
     */
    public static String getFirstLine(String input) {
        // Create a Matcher.
        Matcher matcher = Pattern
            // Compile a regex that matches only the first line of
            // multi-line input.
            .compile("(?m)^.*$")

            // Create a Matcher for this pattern.
            .matcher(input);

        // Find/return the first line in the String.
        return matcher.find()
            // Return String containing the first line
            // if there's a match.
            ? matcher.group()

            // Return an empty String if there's no match.
            : "";
    }
}    
