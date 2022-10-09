import sets.SimpleAbstractSet;
import sets.SimpleConcurrentHashSet;
import sets.SimpleHashSet;
import sets.SimpleTreeSet;

/**
 * This example shows how data abstraction, inheritance, and dynamic
 * binding work in object-oriented Java.  It defines a hierarchy of
 * {@code Simple*Set} classes that are implemented by the
 * corresponding Java {@link Set} collection classes.
 */
public class ex0 {
    /**
     * Factory method that creates the designated {@code setType}.
     */
    private static SimpleAbstractSet<String> makeSet(String setType) {
        return switch (setType) {
            case "HashSet" -> new SimpleHashSet<>();
            case "TreeSet" -> new SimpleTreeSet<>();
            case "ConcurrentHashSet" -> new SimpleConcurrentHashSet<>();
            default -> throw new IllegalArgumentException();
        };
    }
	
    /**
     * Main entry point into the test program.
     */
    public static void main(String[] args) {
        System.out.println("Entering test");

        // Factory method makes the Set subclass object.
        SimpleAbstractSet<String> set =
            makeSet(args.length == 0 ? "HashSet" : args[0]);

        // Add some elements to the set.
        set.add("I");
        set.add("am");
        set.add("Ironman");

        // Try to add a duplicate element.
        if (set.add("Ironman"))
            System.out.println("add() failed");

        // Ensure contains() works properly for elements in the Set.
        assert(set.contains("I") && set.contains("am") && set.contains("Ironman"));

        // Ensure contains() works properly for elements not in the Set.
        assert(!set.contains("Thanos"));

        // Print out the key/values pairs in the Set.
        for (String s : set)
            System.out.println("item = " + s);

        System.out.println("Leaving test");
    }
}
