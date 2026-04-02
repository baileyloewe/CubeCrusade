package com.github.baileyloewe.cubecrusade;

import java.awt.*;
import java.awt.event.MouseAdapter;

/**
 * A utility class for drawing to the screen
 */
public final class GraphicsUtil extends MouseAdapter {
    public enum Fonts {
        XLARGE(new Font("Monospaced", Font.PLAIN, 60)),
        LARGE(new Font("Monospaced", Font.PLAIN, 45)),
        MEDIUM(new Font("Monospaced", Font.PLAIN, 30)),
        SMALL(new Font("Monospaced", Font.PLAIN, 15));

        public final Font font;

        Fonts(Font font) {
            this.font = font;
        }

        public Font getFont() {
            return font;
        }
    }

    private GraphicsUtil() {
    }

    /**
     * drawRect in Java draws +1 to the x and y of a given MenuBoxItem, which is not in line with other draw methods from the same class
     * This removes 1 from the width and height (visually) of the given MenuBoxItem to fix that behavior
     */
    public static void drawRect(Graphics g, MenuBoxItem r) {
        g.drawRect(r.rect.x, r.rect.y, r.rect.width - 1, r.rect.height - 1);
    }

    /**
     * Fills a MenuBoxItem with a color using graphics. Sets the color back to the original color for the graphics
     *
     * @param graphics    graphics object to draw with
     * @param MenuBoxItem the MenuBoxItem to be drawn
     * @param color       the color to be used
     */
    public static void fillRect(Graphics graphics, MenuBoxItem MenuBoxItem, Color color) {
        Color storedColor = graphics.getColor();
        graphics.setColor(color);
        graphics.fillRect(MenuBoxItem.rect.x, MenuBoxItem.rect.y, MenuBoxItem.rect.width, MenuBoxItem.rect.height);
        graphics.setColor(storedColor);
    }

    /**
     * Draws a string within a MenuBoxItem
     *
     * @param graphics    graphics object to draw with
     * @param MenuBoxItem the MenuBoxItem the string is drawn in
     * @param font        the font the string is drawn with
     */
    public static void drawCenteredString(Graphics graphics, MenuBoxItem MenuBoxItem, Fonts font) {
        Font f = font.getFont();
        FontMetrics fontMetric = graphics.getFontMetrics(f);

        // Find x & y coordinate using fontMetrics' help. Use offset because even fontMetrics isn't perfect
        int xCoord = (MenuBoxItem.rect.x - 1 + (MenuBoxItem.rect.width - fontMetric.stringWidth(MenuBoxItem.text)) / 2);
        int yCoord = MenuBoxItem.rect.y - 2 + ((MenuBoxItem.rect.height - fontMetric.getHeight()) / 2) + fontMetric.getAscent();

        graphics.setFont(f);
        graphics.setColor(Color.white);
        graphics.drawString(MenuBoxItem.text, xCoord + 1, yCoord);
    }

    /**
     * Draws the MenuBoxItem and a string within the MenuBoxItem
     *
     * @param g           graphics object to draw with
     * @param MenuBoxItem the MenuBoxItem to be drawn
     * @param font        the font the string is drawn with
     */
    public static void drawRectAndString(Graphics g, MenuBoxItem MenuBoxItem, Fonts font) {
        fillRect(g, MenuBoxItem, Color.black);
        drawRect(g, MenuBoxItem);
        drawCenteredString(g, MenuBoxItem, font);
    }

    /**
     * Draws the MenuBoxItem and a string within the MenuBoxItem
     *
     * @param g           graphics object to draw with
     * @param MenuBoxItem the MenuBoxItem to be drawn
     * @param font        the font the string is drawn with
     * @param color       the color of the drawing
     */
    public static void drawRectAndStringWithColor(Graphics g, MenuBoxItem MenuBoxItem, Fonts font, Color color) {
        fillRect(g, MenuBoxItem, color);
        drawRect(g, MenuBoxItem);
        drawCenteredString(g, MenuBoxItem, font);
    }
}
