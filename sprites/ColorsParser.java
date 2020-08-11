package sprites;

import java.awt.Color;

/**
 * The type Colors parser.
 */
public class ColorsParser {
    /**
     * parse color definition and return the specified color.
     *
     * @param givenString the color - to - be.
     * @return a color.
     */

    public static java.awt.Color colorFromString(String givenString) {
        // if its in an RGB form
        if (givenString.startsWith("RGB")) {
            String degrees = givenString.substring(4, givenString.length() - 1);
            int flag = 0, i = 0, first = -1, second = -1;
            while (i < degrees.length()) {
                if (degrees.charAt(i) == ',' && flag == 1) {
                    second = i;
                }
                if (degrees.charAt(i) == ',' && flag == 0) {
                    flag = 1;

                    first = i;
                }
                i++;
            }

            // the RGB components.

            int y = Integer.parseInt(degrees.substring(first + 1, second));

            int x = Integer.parseInt(degrees.substring(0, first));

            int z = Integer.parseInt(degrees.substring(second + 1));

            return new Color(x, y, z);
        }
        if (givenString.equals("magenta")) {
            return Color.magenta;
        }

        if (givenString.equals("lightGray")) {
            return Color.lightGray;
        }
        if (givenString.equals("green")) {
            return Color.green;
        }
        if (givenString.equals("orange")) {
            return Color.orange;
        }
        if (givenString.equals("black")) {
            return Color.BLACK;
        }
        if (givenString.equals("gray")) {
            return Color.gray;
        }
        if (givenString.equals("pink")) {
            return Color.PINK;
        }
        if (givenString.equals("red")) {
            return Color.red;
        }
        if (givenString.equals("white")) {
            return Color.white;
        }
        if (givenString.equals("yellow")) {
            return Color.yellow;
        }
        if (givenString.equals("darkGray")) {
            return Color.darkGray;
        }
        if (givenString.equals("blue")) {
            return Color.blue;
        }
        if (givenString.equals("cyan")) {
            return Color.cyan;
        }

        // if string does not define any kind of color.
        return null;
    }
}
