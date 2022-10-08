import sets.SimpleAbstractSet;
import sets.SimpleConcurrentHashSet;
import sets.SimpleHashSet;
import sets.SimpleTreeSet;

/**
 * This class illustrates how inheritance and dynamic binding works in
 * Java.  It uses a simplified hierarchy of *Set classes that are
 * implemented by the corresponding "real" Java classes.
 */
public class ex0 {
    /**
     * Factory method that creates the designated {@code mapType}.
     */
    private static SimpleAbstractSet<String> makeSet(String mapType) {
        return switch (mapType) {
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
        // Factory method makes the appropriate type of Set subclass.
        SimpleAbstractSet<String> set =
            makeSet(args.length == 0 ? "HashSet" : args[0]);

        // Add some elements to the set.
        set.add("I");
        set.add("am");
        set.add("Ironman");

        if (set.add("Ironman"))
            System.out.println("add() failed");

        if (set.contains("I")
            || set.contains("am")
            || set.contains("Ironman"))
            System.out.println("contains() failed");

        // Print out the key/values pairs in the Set.
        for (String s : set)
            System.out.println("next item = " + s);
    }
}
