import utils.TestDataFactory;

import java.text.BreakIterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.AbstractMap.SimpleImmutableEntry;
import static utils.TestDataFactory.makePhraseList;

/**
 * This case study shows how Java's {@link BreakIterator}, ,regular
 * expression methods, and other object-oriented features can be used
 * to search the complete works of Shakespeare for given words and
 * phrases.
 */
@SuppressWarnings("SameParameterValue")
public class BardWords {
    /**
     * The file containing the complete works of William Shakespeare.
     */
    private static final String sSHAKESPEARE_DATA_FILE =
        "completeWorksOfShakespeare.txt";

    /**
     * The {@link List} of phrases to search for in the works of
     * Shakespeare.
     */
    private static final List<SimpleImmutableEntry<String, String>> sPhrases =
        makePhraseList();

    /**
     * Main entry point into the program.
     */
    static public void main(String[] argv) {
        // Create a List of String objects containing the complete
        // works of Shakespeare, one work per String.
        List<String> bardWorks = TestDataFactory
            .getInput(sSHAKESPEARE_DATA_FILE,
                      // Split input into "works".
                      "@");

        // Process the List of phrases to see where they occur in the
        // complete works of Shakespeare.
        for (var entry : sPhrases)
            // Search the works of Shakespeare for a certain word/phrase.
            processBardWorks(bardWorks,
                             // Get the word to search for.
                             entry.getKey(),

                             // Get the regular expression to compile.
                             entry.getValue()
                             );
    }

    /**
     * Show how the Java regular expression methods can be used to
     * search the complete works of Shakespeare ({@code bardWorks} for
     * {@code word}.
     */
    private static void processBardWorks(List<String> bardWorks,
                                         String word,
                                         String regex) {
        // Create a List of Shakespeare works containing 'word'.
        List<String> bardWorksMatchingWord = new ArrayList<>();

        // Loop through each work in the original list.
        for (String work : bardWorks)
            // If 'word' appears in 'work', add 'work' to the List.
            if (findMatch(work, word))
                bardWorksMatchingWord.add(work);

        // Compile the regular expression to perform case-insensitive
        // matches.
        Pattern pattern = Pattern
            .compile(regex,
                     Pattern.CASE_INSENSITIVE);

        // Show the portions of the works of Shakespeare that match
        // the pattern.
        showRegexMatches(bardWorksMatchingWord, pattern);
    }

    /**
     * Return true if the {@code work} contains the {@code searchWord}.
     *
     * @param work       The text to search
     * @param searchWord The word to search for
     * @return true if the {@code work} contains the {@code searchWord}
     */
    private static boolean findMatch(String work,
                                     String searchWord) {
        // Create a BreakIterator that will break words.
        BreakIterator iterator = BreakIterator
            .getWordInstance(Locale.US);

        // Set the text to search.
        iterator.setText(work);

        // Get the first and second boundary from the iterator.
        int previous = iterator.first();

        // Iterate through all the text.
        for (int current = iterator.next();

             // Keep iterating until the BreakIterator is done.
             current != BreakIterator.DONE;

             // Update the current boundary.
             current = iterator.next()) {
            // Get the text between the previous and current
            // boundaries.
            String word = work.substring(previous, current);

            // Check if the item matches the predicate and
            // that 'word' contains 'searchWord'.
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
     * Show the portions of the works of Shakespeare that match the
     * {@link Pattern}.
     *
     * @param bardWorksMatchingWord The Shakespeare works matching
     *                              a search word
     * @param pattern               The compiled regular expression to search for
     */
    private static void showRegexMatches
        (List<String> bardWorksMatchingWord,
         Pattern pattern) {
        // Process each work in the List.
        for (String work : bardWorksMatchingWord) {
            // Iterate through each match in the work.
            for (Matcher matcher = pattern
                     // Create a Matcher that associates the regex pattern with
                     // the work.
                     .matcher(work);
                 // Process each match in the work.
                 matcher.find();
                 ) {
                // Print the title of the work.
                System.out.println(getFirstLine(work));

                // Print the match.
                System.out.println("\""
                                   + matcher.group()
                                   + "\" ["
                                   // Print match location.
                                   + matcher.start()
                                   + "]");
            }
        }
    }

    /**
     * @return The first line of the {@code input}.
     */
    public static String getFirstLine(String work) {
        // Create a Matcher.
        Matcher m = Pattern
            // Compile a regex that matches only the first line in the
            // input.
            .compile("(?m)^.*$")

            // Create a matcher for this pattern.
            .matcher(work);

        // Find/return the first line in the String.
        return m.find()
            // Return the title string if there's a match.
            ? m.group()

            // Return an empty String if there's no match.
            : "";
    }
}
