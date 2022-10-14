import sets.SimpleAbstractSet;
import sets.SimpleConcurrentHashSet;
import sets.SimpleHashSet;
import sets.SimpleTreeSet;

import java.util.List;
import java.util.Set;

/**
 * This example shows how abstraction, inheritance, and polymorphism
 * work together synergistically in object-oriented Java.  It defines
 * a hierarchy of {@code Simple*Set} classes that use the corresponding
 * Java {@link Set} collection classes in their implementations. This
 * design applies the Decorator pattern.
 */
public class ex0 {
    /**
     * Main entry point into the test program.
     */
    public static void main(String[] args) {
        System.out.println("Entering test");

        // Iterate through the set types.
        for (String setType : List.of("HashSet",
                                      "TreeSet",
                                      "ConcurrentHashSet")) {
            // Factory method makes the concrete Simple*Set
            // subclass & exposes no lexical dependencies.
            var set =
                    makeSet(setType);

            // Test the Simple*Set add() method.
            testAdd(set);

            // Test the Simple*Set contains() method.
            testContains(set);

            // Test the Simple*Set iterator() method.
            testIterator(set);
        }

        System.out.println("Leaving test");
    }

    /**
     * Factory method that creates the designated {@code setType}.
     */
    private static SimpleAbstractSet<String> makeSet(String setType) {
        return switch (setType) {
            case "HashSet" -> new SimpleHashSet<>();
            case "TreeSet" -> new SimpleTreeSet<>();
            case "ConcurrentHashSet" ->
                    new SimpleConcurrentHashSet<>();
            default -> throw new IllegalArgumentException();
        };
    }

    /**
     * Test the {@code Simple*Set} add() method.
     * @param set The set to operator upon
     */
    private static void testAdd(SimpleAbstractSet<String> set) {
        // Add some elements to the set, all of
        // which should return "true".
        assert (set.add("I")
                && set.add("am")
                && set.add("Ironman"));

        // Try to add a duplicate element, which
        // should return "false".
        assert (!set.add("Ironman"));
    }

    /**
     * Test the {@code Simple*Set} add() method.
     * @param set The set to operator upon
     */
    private static void testContains(SimpleAbstractSet<String> set) {
        // Ensure contains() works properly for elements in the Set.
        assert(set.contains("I")
                && set.contains("am")
                && set.contains("Ironman"));

        // Ensure contains() works properly for elements not in the
        // Set.
        assert(!set.contains("Thanos")
                && !set.contains("Bill Ward"));
    }

    /**
     * Test the {@code Simple*Set} iterator() method.
     * @param set The set to operator upon
     */
    private static void testIterator(SimpleAbstractSet<String> set) {
        // Use a for-each loop to print the elements in the Set.
        // This for-each loop implicitly calls set.iterator().
        for (String s : set)
            System.out.println("item = " + s);
    }
}
