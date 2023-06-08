import utils.RegexUtils;
import utils.TestDataFactory;

import java.io.File;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import static java.util.AbstractMap.SimpleEntry;
import static utils.TestDataFactory.makePhraseList;

/**
 * This case study shows how Java's {@link BreakIterator}, regular
 * expression methods, and other object-oriented features can be used
 * to search the complete works of Shakespeare for given words and
 * phrases.
 */
@SuppressWarnings("SameParameterValue")
public class BardWords {
    /**
     * The name of the {@link File} containing the complete works of
     * William Shakespeare.
     */
    private static final String sSHAKESPEARE_DATA_FILE =
        "completeWorksOfShakespeare.txt";

    /**
     * The {@link List} of phrases to search for in the works of
     * Shakespeare.
     */
    private static final List<SimpleEntry<String, String>> sPhrases =
        makePhraseList();

    /**
     * Main entry point into the program.
     */
    static public void main(String[] argv) {
        // Create a List of String objects containing the complete
        // works of Shakespeare, one work per String.
        var bardWorks = TestDataFactory
            .getInput(sSHAKESPEARE_DATA_FILE,
                      // Split File input into "works".
                      "@");

        // Process the List of phrases to see where they occur in the
        // complete works of Shakespeare.
        for (var entry : sPhrases)
            // Search the works of Shakespeare for a certain
            // word/phrase.
            processBardWorks(bardWorks,
                             // Get the word to search for.
                             entry.getKey(),

                             // Get the regular expression to compile.
                             entry.getValue());
    }

    /**
     * Use Java regular expression methods to search the complete
     * works of Shakespeare ({@code bardWorks}) for {@code word}.
     */
    private static void processBardWorks(List<String> bardWorks,
                                         String word,
                                         String regex) {
        // Create a List of Shakespeare works that will contain 'word'.
        List<String> bardWorksMatchingWord = new ArrayList<>();

        // Loop through each work in the 'bardWorks' List.
        for (var work : bardWorks)
            // If 'word' appears in 'work', add 'work' to the List.
            if (findMatch(work, word))
                bardWorksMatchingWord.add(work);

        // Compile the regular expression to perform case-insensitive
        // matches (e.g., "Lord", "lord", and "LORD" all match).
        var pattern = Pattern
            .compile(regex,
                     Pattern.CASE_INSENSITIVE);

        // Show the portions of the works of Shakespeare that match
        // the pattern.
        showRegexMatches(bardWorksMatchingWord, pattern);
    }

    /**
     * Use {@link BreakIterator} to determine whether the {@code work}
     * of Shakespeare contains the {@code searchWord} in its text.
     *
     * @param work The text to search
     * @param searchWord The word to search for
     * @return true if the {@code work} contains the {@code
     *              searchWord}
     */
    private static boolean findMatch(String work,
                                     String searchWord) {
        // Create a BreakIterator that breaks the works of
        // Shakespeare into words using the UK locale.
        var iterator = BreakIterator
            .getWordInstance(Locale.UK);

        // Set the text to search.
        iterator.setText(work);

        // Iterate through all the text in 'work'.
        for (// Get the first and second word boundary from the iterator.
             int previous = iterator.first(),
                 current = iterator.next();
             // Iterate until BreakIterator is done.
             current != BreakIterator.DONE;
             // Update the current boundary with the next word.
             current = iterator.next()) {
            // Get the "word" between the previous and current
            // boundaries.
            String word = work.substring(previous, current);

            // Check if 'word' is indeed a "word" and that it
            // contains 'searchWord'.
            if (Character.isLetterOrDigit(word.charAt(0))
                && word.toLowerCase().equals(searchWord))
                return true;

            // Make 'current' boundary the 'previous' boundary.
            previous = current;
        }

        // Return false if there's no match.
        return false;
    }

    /**
     * Display the portions of the works of Shakespeare that match the
     * {@link Pattern}.
     *
     * @param bardWorksMatchingWord The Shakespeare works matching a
     *                              search word
     * @param pattern The compiled regular expression to search for
     */
    private static void showRegexMatches
        (List<String> bardWorksMatchingWord,
         Pattern pattern) {
        // Print the regex pattern we're searching for.
        System.out.println("Pattern = \""
                           + pattern.toString()
                           + "\"");

        // Process each work in the List.
        for (var work : bardWorksMatchingWord) {
            // Iterate through each match in the work.
            for (var matcher = pattern
                     // Create a Matcher that associates the regex
                     // pattern with the work.
                     .matcher(work);
                 // Process the loop body long as there's a match.
                 matcher.find();
                 ) {
                // Print the title of the work.
                System.out.println(RegexUtils.getFirstLine(work));

                // Print the input subsequence matched by the
                // previous match.
                System.out.println("\""
                                   + matcher.group()
                                   + "\" ["
                                   // Print match location.
                                   + matcher.start()
                                   + "]");
            }
        }
    }
}
