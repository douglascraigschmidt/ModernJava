package utils;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.AbstractMap.SimpleEntry;

/**
 * This utility class contains methods for obtaining test data.
 */
public class TestDataFactory {
    /**
     * A utility class should always define a private constructor.
     */
    private TestDataFactory() {
    }

    /**
     * Split the input data in the given {@code filename} using the
     * {@code splitter} regular expression and return a {@link List}
     * of {@link String} objects.
     *
     * @param filename The name of the file containing the input data
     * @param splitter The regular expression to use to split the input
     */
    public static List<String> getInput(String filename,
                                        String splitter) {
        try {
            // Convert the filename into a pathname.
            URI uri = ClassLoader.getSystemResource(filename).toURI();

            // Open the file and get all the bytes.
            CharSequence bytes =
                new String(Files.readAllBytes(Paths.get(uri)));

            // Compile splitter into a regular expression (regex).
            Pattern pattern = Pattern.compile(splitter);

            // Use the regex to split the file into an array of String
            // objects.
            String[] splitStrings = pattern.split(bytes);

            // Return the List of non-empty String objects.
            return Arrays.asList(splitStrings);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return A {@link List} of {@link SimpleEntry} objects
     *         containing the word to search for and the regular
     *         expression to compile
     */
    public static ArrayList<SimpleEntry<String, String>>
    makePhraseList() {
        return new ArrayList<>() {{
            add(new SimpleEntry<>(
                // The word to search for.
                "lord",

                // The regular expression to compile, which matches
                // the phrase "'lord' followed by either 'true' or
                // 'false'".
                "\\blord\\b.*(\\btrue\\b|\\bfalse\\b)"));
        }};
    }
}
