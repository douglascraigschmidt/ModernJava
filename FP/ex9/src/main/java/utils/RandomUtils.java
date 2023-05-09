package utils;

import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

public class RandomUtils {
    /**
     * Generate random data for use by the various hashmaps.
     *
     * @return A {@link List} of random {@link Integer} objects
     */
    public static List<Integer> generateRandomData
        (int count, int maxValue) {
        // Generate a list of random large integers.
        return new Random()
                // Generate a stream of "count" random large ints.
                .ints(count,
                        // Try to generate duplicates.
                        maxValue - count,
                        maxValue)

                // Convert each primitive int to Integer.
                .boxed()

                // Trigger intermediate operations and collect into list.
                .collect(toList());
    }

}
