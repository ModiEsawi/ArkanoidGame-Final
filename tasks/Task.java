package tasks;

/**
 * The interface Task.
 *
 * @param <T> the type parameter
 */
public interface Task<T> {
    /**
     * running the given T.
     *
     * @return the t.
     * @throws Exception the exception.
     */
    T run() throws Exception;

    /**
     * returns the flag.
     *
     * @return :the flag.
     */
    int getFlag();
}