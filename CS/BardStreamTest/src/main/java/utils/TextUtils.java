package utils;

import org.w3c.dom.Text;

import static java.util.AbstractMap.SimpleEntry;
import static java.util.Spliterators.AbstractSpliterator;

import java.util.Spliterator;
import java.util.function.Consumer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * This Java utility class contains methods for manipulating text.
 */
public class TextUtils {
    /**
     * A Java utility class should have a private constructor.
     */
    private TextUtils() {}

    /**
     * This class defines a spliterator that finds occurrences of
     * phrases in an input {@link String}.
     */
    private static class PhraseSpliterator
            extends AbstractSpliterator<SimpleEntry<String, Integer>> {
        /**
         * The phrase.
         */
        private final String mPhrase;

        /**
         * The input work (e.g., "Hamlet", "MacBeth", etc.).
         */
        String mInput;

        /**
         * The index in the input.
         */
        int mIndex = 0;

        /**
         * The constructor initializes the fields.
         *
         * @param phrase The phrase to match in the input
         * @param input The work (e.g., "Hamlet", "MacBeth", etc.)
         */
        PhraseSpliterator(String phrase,
                          String input) {
            super(Long.MAX_VALUE, Spliterator.ORDERED);
            mInput = input;
            mPhrase = phrase;
        }

        /**
         * Try to advance the {@link Spliterator}.
         *
         * @param action The action to update with a match
         * @return true if there are more matches, false otherwise
         */
        public boolean tryAdvance
            (Consumer<? super SimpleEntry<String, Integer>> action) {
            // Try to find a match.
            mIndex = mInput.indexOf(mPhrase, mIndex);

            // If there is a match, update the action and advance.
            if (mIndex != -1) {
                action.accept(new SimpleEntry<>(mPhrase, mIndex));
                mIndex += mPhrase.length();
                return true;
            } else
                // Indicate there's no match.
                return false;
        }
    };

    /**
     * Return a {@link Stream}.
     *
     * @param phrase The phrase to match in the input
     * @param input The work (e.g., "Hamlet", "MacBeth", etc.)
     * @return A {@link Stream} of {@link SimpleEntry} objects
     *         that match the phrase in the input.
     */
    public static Stream<SimpleEntry<String, Integer>> match
        (String phrase, 
         String input) {
        // Create a PhraseSpliterator that will return the matches.
        var spliterator = new PhraseSpliterator(phrase, input);

        // Create a Stream from the PhraseSpliterator.
        return StreamSupport.stream(spliterator, false);
    }

    /**
     * @return The title portion of the {@code input} {@link String}
     */
    public static String getTitle(String input) {
        // Create a Matcher.
        Matcher m = Pattern
            // Compile a regex that matches only the first line in the
            // input since each title appears on the first line of the
            // work.
            .compile("(?m)^.*$")

            // Create a matcher for this pattern.
            .matcher(input);

        // Extract the title.
        return m.find()
            // Return the title String if there's a match.
            ? m.group()

            // Return an empty String if there's no match.
            : "";
    }
}

