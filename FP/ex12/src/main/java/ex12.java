import java.util.*;
import java.util.function.Supplier;

/**
 * This example shows how a Java {@link Supplier} interface is used
 * in conjunction with the Java {@link Optional} class to print a
 * default value if a key is not found in a {@link Map}.
 */
public class ex12 {
    /**
     * The main entry point into the Java program.
     */
    static public void main(String[] argv) {
        // Create a HashMap that associates beings with their
        // personas.
        Map<String, String> beingMap = new HashMap<String, String>() { { 
                put("Demon", "Naughty");
                put("Angel", "Nice");
                put("Wizard", "Wise");
            } };

        beingMap
            // Display the contents of the Map.
            .forEach(ex12::display);

        // The being to search for (who is not in the map).
        String being = "Demigod";

        // Try to find the being in the Map. Since it won't be
        // there, an empty Optional will be returned from ofNullable().
        Optional<String> disposition = 
            Optional.ofNullable(beingMap.get(being));

        display(being,
                // Pass a Supplier lambda expression that
                // returns a default value if the being is
                // not found.
                disposition.orElseGet(() -> "unknown"));

        String value = beingMap.getOrDefault(being, "unknown");
        display(being, value);
    }

    /**
     * Display the {@code disposition} associated with the {@code
     * being}.
     *
     * @param being The being
     * @param disposition The being's disposition
     */
    private static void display(String being,
                                String disposition) {
        System.out.println("disposition of "
                           + being + " = "
                           + disposition);
    }
}

