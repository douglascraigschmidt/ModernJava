package utils;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * This utility class contains methods that read input from
 * text files and return this input as a {@link List} of
 * {@link String} objects.
 */
public final class BardDataFactory {
    /**
     * A utility class should always define a private constructor.
     */
    private BardDataFactory() {
    }

    /**
     * Processes the content of an input file using the provided
     * {@link Function}.
     *
     * @param filename The name of the {@link File} to process, which
     *                 should be located in the resource path
     * @param fileProcessor A {@link Function} that takes the content
     *                      of the {@link File} as a {@link String},
     *                      processes it in a certain way, and returns
     *                      a {@link List} of {@link String}
     *                      objects
     * @return A {@link List} of non-empty {@link String} objects that
     *         result from processing the file content
     */
    private static List<String> processFile
        (String filename,
         Function<String, List<String>> fileProcessor) {
        try {
            // Convert the filename into a pathname.
            var uri = getResourceFilePath(filename);

            // Open the File and read all the bytes.
            var content = new String(Files.readAllBytes(uri));

            // Process the File content by applying the provided Function.
            var strings = fileProcessor.apply(content);

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
     * @return The contents associated with {@code filename} as a
     *         {@link List} of non-empty {@link String} objects
     */
    public static List<String> getInput(String filename,
                                        String splitter) {
        // Call processFile(), passing a Function that splits the File
        // associated with 'filename' by the 'splitter' and returns a
        // List.
        return processFile
            (filename, content ->
             Arrays.asList(content.split(splitter)));
    }

    /**
     * @return Return the phrases associated with the {@code filename}
     *         as a {@link List} of non-empty {@link String} objects
     */
    public static List<String> getPhraseList(String filename) {
        // Call processFile(), passing a Function that splits the File
        // associated with 'filename' by newlines and returns a List.
        return processFile
            (filename, content ->
             Arrays.asList(content.split("\\R")));
    }

    /**
     * Gets a {@link Path} to a file in the {@code resources} folder.
     *
     * @param filename The name of the file
     * @return A {@link Path} to the file
     * @throws URISyntaxException If the filename is invalid
     */
    private static Path getResourceFilePath(String filename)
        throws URISyntaxException {
        return Paths
            // Convert the filename into a pathname.
            .get(ClassLoader.getSystemResource(filename).toURI());
    }
}
