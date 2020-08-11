package game;

import animation.Animation;

/**
 * The interface Menu.
 *
 * @param <T> the type parameter
 */
public interface Menu<T> extends Animation {

    /**
     * Add selection.
     * adds an option to select from the menu.
     *
     * @param key       the key.
     * @param message   the message to be shown.
     * @param returnVal the return value.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * returns the status.
     *
     * @return the status
     */
    T getStatus();
}