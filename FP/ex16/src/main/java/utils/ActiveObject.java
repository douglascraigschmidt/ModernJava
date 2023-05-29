package utils;

import java.util.concurrent.*;
import java.util.function.Function;

/**
 * This class implements a variant of the <A
 * HREF="https://en.wikipedia.org/wiki/Active_object">Active Object
 * </A> pattern using modern Java features. An instance of this class
 * calls a generic {@link Function} with a generic param to perform a
 * computation asynchronously. This class implements the {@link
 * Future} interface so clients can check if the computation is
 * complete, wait for its completion, and retrieve the result of the
 * asynchronous computation.
 */
@SuppressWarnings("SameParameterValue")
public class ActiveObject<T, R>
       implements Future<R> {
    /**
     * The closure below updates this private field to store the
     * results of the {@link Function} applied to the param {@code n}.
     */
    R mResult;

    /**
     * This field stores the {@link FutureTask} that wraps the virtual
     * {@link Thread} used to perform the {@link Function}.
     */
    RunnableFuture<R> mRunnableFuture;

    /**
     * Store the virtual {@link Thread} object used to perform a
     * {@link Function} in the background.
     */
    Thread mThread;

    /**
     * This factory method creates a closure that will run in a
     * background {@link Thread} and run the {@link Function}.
     *
     * @param function The {@link Function} to apply to the param
     *                 {@code n}
     * @param n The param to apply the {@link Function} to
     * @return A {@link Thread} that runs in the background and
     *         applies the {@link Function} to the param {@code n}
     */
    private RunnableFuture<R> makeThreadClosure
        (Function<T, R> function,
         T n) {
        // Create a FutureTask that defines a closure that applies the
        // 'function' param to the param 'n'.
        mRunnableFuture = new FutureTask<R>(() -> {
                // The field is updated within the closure.
                return mResult = function
                    // Apply the function to the param 'n'.
                    .apply(n);
        });

        // Create and return a new virtual Thread whose Runnable
        // lambda expression defines a closure that applies the
        // 'function' on the param 'n' and stores the result in the
        // mResult field.
        mThread = Thread.startVirtualThread(mRunnableFuture);

        // Return the RunnableFuture.
        return mRunnableFuture;
    }

    /**
     * The constructor creates/starts/runs a virtual {@link Thread}
     * closure to apply the {@link Function} to the param {@code n}.
     *
     * @param function The function to apply to the param {@code n}
     * @param n The param to apply the function to
     */
    public ActiveObject(Function<T, R> function,
                        T n) {
        // Create and start a virtual Thread closure.
        mRunnableFuture = makeThreadClosure(function, n);
    }

    /**
     * This method cancels the {@link Thread} that is running the
     * {@link Function}.
     *
     * @param mayInterruptIfRunning {@code true} if the thread
     *                              executing this task should be
     *                              interrupted (if the thread is
     *                              known to the implementation);
     *                              otherwise, in-progress tasks are
     *                              allowed to complete
     * @return {@code true} if the task was successfully canceled,
     *         else false
     */
    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return mRunnableFuture.cancel(mayInterruptIfRunning);
    }

    /**
     * This method returns true if the {@link Function} has been
     * cancelled, else false.
     *
     * @return true if the {@link Function}, else false
     */
    @Override
    public boolean isCancelled() {
        return mRunnableFuture.isCancelled();
    }

    /**
     * This method returns true if the {@link Function} has completed.
     *
     * @return true if the {@link Function} has completed, else false
     */
    @Override
    public boolean isDone() {
        return mRunnableFuture.isDone();
    }

    /**
     * This method returns the result of the {@link Function} or null.
     *
     * @return The result of the {@link Function} or null.
     * @throws InterruptedException If the Thread is interrupted
     * @throws ExecutionException If the {@link Function} failed
     */
    public R get()
        throws InterruptedException, ExecutionException {
        return mRunnableFuture.get();
    }

    /**
     * @return Returns the computed result, without waiting
     */
    @Override
    public R resultNow() {
        return mRunnableFuture.resultNow();
    }

    /**
     * This method returns the result of the {@link Function} or null
     * if the {@link Function} does not complete before the timeout.
     *
     * @param timeout the maximum time to wait
     * @param unit the time unit of the timeout argument
     * @return The result of the {@link Function} or null if the
     *         timeout expires before the result is available
     * @throws InterruptedException If the Thread is interrupted
     * @throws ExecutionException If the {@link Function} failed
     * @throws TimeoutException If the {@link Function} does not
     *         complete
     */
    @Override
    public R get(long timeout, TimeUnit unit)
        throws InterruptedException, ExecutionException, TimeoutException {
        return mRunnableFuture.get(timeout, unit);
    }
}
