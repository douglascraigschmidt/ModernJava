package utils;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

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
     * @return The input data in the given {@code filename} as a
     *         {@link List} of {@link String} objects
     */
    public static List<String> getInput(String filename,
                                        String splitter) {
        try {
            // Convert the filename into a pathname.
            var uri = ClassLoader.getSystemResource(filename).toURI();

            // Open the file and read all the bytes.
            var bytes = new String(Files.readAllBytes(Paths.get(uri)));

            // Split the File into a List of String objects using the
            // specified splitter.
            var strings = Arrays
                .asList(bytes.split(splitter));

            // Filter out any empty String objects.
            strings.removeIf(String::isEmpty);

            // Return the 'strings' List.
            return strings;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return The phrase {@link List} in the {@code filename} as a
     *         {@link List} of non-empty {@link String} objects
     */
    public static List<String> getPhraseList(String filename) {
        try {
            // Convert the filename into a pathname.
            var uri = Paths
                .get(ClassLoader.getSystemResource(filename).toURI());

            // Open the file and read all the lines.
            var lines = Files
                .readAllLines(uri);

            // Filter out any empty strings.
            lines.removeIf(String::isEmpty);

            // Return the 'lines' List.
            return lines;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
