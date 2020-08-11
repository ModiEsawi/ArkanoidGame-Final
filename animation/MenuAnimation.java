package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.Menu;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * The type Menu animation.
 *
 * @param <T> the type parameter
 */
public class MenuAnimation<T> implements Menu<T> {

    private String menuTitle;
    private TreeMap<String, T> map;
    private List<String> messages;
    private List<String> keys;
    private T status;
    private KeyboardSensor keyboard;
    private Image background;

    /**
     * Instantiates a new Menu animation.
     *
     * @param title      the menu title.
     * @param keyboard   the keyboard.
     * @param background the menu background.
     */
    public MenuAnimation(String title, KeyboardSensor keyboard, Image background) {
        this.menuTitle = title;
        this.messages = new ArrayList<>();
        this.keys = new ArrayList<>();
        this.keyboard = keyboard;
        this.map = new TreeMap<>();
        this.background = background;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.drawImage(0, 0, this.background);
        d.setColor(Color.lightGray);
        d.setColor(Color.black);
        d.drawText(295, 55, this.menuTitle, 30);
        d.setColor(Color.red);
        d.drawText(296, 56, this.menuTitle, 30);
        d.setColor(Color.black);
        d.drawText(40, 120, "Please select one of the following options :", 29);
        d.setColor(Color.BLUE);
        d.drawText(41, 121, "Please select one of the following options :", 29);
        d.setColor(Color.black);

        // the menu options
        for (int i = 0; i < this.messages.size(); i++) {
            d.drawText(91, 177 + (i * 50), "(" + this.keys.get(i) + ") " + this.messages.get(i), 30);
            d.drawText(92, 178 + (i * 50), "(" + this.keys.get(i) + ") " + this.messages.get(i), 30);
        }
    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.messages.add(message);
        this.keys.add(key);
        this.map.put(key, returnVal);
    }

    @Override
    public T getStatus() {
        return this.status;
    }

    @Override
    public boolean shouldStop() {
        for (int i = 0; i < this.map.size(); i++) {
            if (this.keyboard.isPressed(keys.get(i))) { // getting the status.
                this.status = this.map.get(keys.get(i));
                return true;
            }
        }
        return false;
    }
}
