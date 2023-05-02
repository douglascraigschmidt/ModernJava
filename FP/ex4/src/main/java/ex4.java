import java.util.concurrent.atomic.AtomicInteger;

/**
 * This example shows various ways to create a Java {@link Thread} and
 * pass a {@link Runnable} lambda expression to it.
 */
public class ex4 {
    /**
     * The main entry point into this program.
     */
    static public void main(String[] argv) throws InterruptedException {
        ex4.display("Entering test");

        // Showcase various ways of creating a Thread using Java
        // lambda expressions.
        threadSimpleHelloWorld();
        threadRunnableVariable();

        // Ensure the results are correct.
        assert threadEffectivelyFinal() == 42;
        assert threadCompileError() == 0;
        assert threadOneElementArray() == 42;
        assert threadAtomicInteger() == 42;

        ex4.display("Leaving test");
    }

    /**
     * This method creates a Java {@link Thread} that displays the
     * classic "hello world" greeting using an anonymous lambda.
     */
    private static void threadSimpleHelloWorld() throws InterruptedException {
        // Create and start new Thread that is passed a Runnable lambda to
        // execute.
        Thread t = startNewThread
                (() -> ex4.display("hello world"));

        ex4.display("Block until Thread is done");

        // Block until Thread 't' is done.
        t.join();
    }

    /**
     * This method creates a Java {@link Thread} that displays the
     * classic "hello world" greeting using a {@link Runnable} lambda
     * local variable.
     */
    private static void threadRunnableVariable() throws InterruptedException {
        // Assign a Runnable lambda to a local variable.
        Runnable r = () -> ex4.display("hello world");

        // Create and start a new Thread that executes Runnable 'r'.
        Thread t = startNewThread(r);

        ex4.display("Block until Thread is done");

        // Block until Thread 't' is done.
        t.join();
    }

    /**
     * This method creates a Java {@link Thread} that displays a
     * message containing an effectively final variable.
     */
    private static int threadEffectivelyFinal() throws InterruptedException {
        // Initialize an effectively final variable.
        int answer = 42;

        // Create a new Thread that displays a messaging containing
        // the value in an effectively final variable.
        Thread t = startNewThread(() ->
                              ex4.display("The answer is " + answer));

        ex4.display("Block until Thread is done");

        // Block until Thread 't' is done.
        t.join();

        // Return the answer.
        return answer;
    }

    /**
     * This method creates a Java {@link Thread} that invalidity
     * attempts to set an {@code int} value within its {@link
     * Runnable} lambda parameter.
     */
    private static int threadCompileError() throws InterruptedException {
        // Initialize a local non-final variable.
        int answer = 0;

        Thread t = startNewThread(() -> {
                // If 'answer = 42;' is uncommented, the code won't
                // compile since the 'answer' local variable can't be
                // modified in the Runnable lambda.
                // answer = 42;
                ex4.display("The answer is " + answer);
        });

        ex4.display("Block until Thread is done");

        // Block until Thread 't' is done.
        t.join();

        // Return the answer.
        return answer;
    }

    /**
     * This method creates a Java {@link Thread} that sets the value
     * in a one-element {@code int} array within its {@link Runnable}
     * lambda parameter.
     */
    private static int threadOneElementArray() throws InterruptedException {
        // Create and initialize a one-element array.
        int[] answer = new int[1];  

        // Create and start a new Thread.
        Thread t = startNewThread(() -> {
                // Set 'answer[0]' to 42, which is allowed since
                // 'answer[0]' accesses an object that can be
                // modified, even though 'answer' can't be modified.
                answer[0] = 42;

                // Display the value of 'answer[0]'.
                ex4.display("The answer is " + answer[0]);
        });

        ex4.display("Block until Thread is done");

        // Block until Thread 't' is done.
        t.join();

        // Return the value of 'answer[0]', which was updated in the
        // Runnable lambda.
        return answer[0];
    }

    /**
     * This method creates a Java {@link Thread} that sets an {@link
     * AtomicInteger} object within its {@link Runnable} lambda
     * parameter.
     *
     * @return An int value
     */
    private static int threadAtomicInteger() throws InterruptedException {
        // Create an AtomicInteger that can be set within the Runnable
        // lambda.
        AtomicInteger answer = new AtomicInteger(0);

        // Create and start a new Thread.
        Thread t = startNewThread(() -> {
                // Set the value of 'answer' to 42, which is allowed
                // since 'answer' is an AtomicInteger whose value can
                // be modified.
                answer.set(42);

                // Display the value of 'answer'.
                ex4.display("The answer is " + answer.get());
        });

        ex4.display("Block until Thread is done");

        // Block until Thread 't' is done.
        t.join();

        // Return the value of 'answer.get()', which was updated in
        // the Runnable lambda.
        return answer.get();
    }

    /**
     * Create and start a new Thread that is passed a {@link Runnable}
     * lambda to execute.
     *
     * @param runnable The {@link Runnable} to execute.
     * @return A started {@link Thread} that is passed the {@link Runnable}
     */
    private static Thread startNewThread(Runnable runnable) {
        // Create a new Thread that is passed a Runnable lambda to
        // execute.
        Thread t = new Thread(runnable);

        // Start Thread 't'.
        t.start();

        // Return the started Thread.
        return t;
    }

    /**
     * Display the {@code output} after prepending the current {@link
     * Thread} id.

     * @param output The {@code output} to display
     */
    private static void display(String output) {
        System.out.println("["
                           + Thread.currentThread().threadId()
                           + "] "
                           + output);
    }
}

