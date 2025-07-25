package com.main;

import java.awt.*;
import java.awt.Point;

public class Star extends GameObject{

    private final Point[] locations = new Point[26];
    /**
     * Creates a game object with an x-coordinate, x-coordinate, ID, and attaches it to the ID
     */
    public Star(Mediator mediator) {
        super(mediator);
        setLocations();
    }

    public void setLocations() {
        // Manually created star locations
        locations[0] = new Point (292, 186);
        locations[1] = new Point (527, 107);
        locations[2] = new Point (759, 257);
        locations[3] = new Point (664, 502);
        locations[4] = new Point (36, 285);
        locations[5] = new Point (94, 476);
        locations[6] = new Point (146, 103);
        locations[7] = new Point (231, 380);
        locations[8] = new Point (597, 358);
        locations[9] = new Point (746, 61);
        locations[10] = new Point (403, 558);
        locations[11] = new Point (333, 34);
        locations[12] = new Point (398, 313);
        locations[13] = new Point (237, 592);
        locations[14] = new Point (129, 751);
        locations[15] = new Point (361, 798);
        locations[16] = new Point (592, 684);
        locations[17] = new Point (752, 840);
        locations[18] = new Point (1027, 826);
        locations[19] = new Point (903, 602);
        locations[20] = new Point (1168, 643);
        locations[21] = new Point (1038, 445);
        locations[22] = new Point (835, 396);
        locations[23] = new Point (1072, 252);
        locations[24] = new Point (915, 143);
        locations[25] = new Point (1083, 24);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        for (Point location : locations) {
            g.drawLine(location.x - 2, location.y, location.x + 2, location.y);
            g.drawLine(location.x, location.y - 2, location.x, location.y + 2);
        }
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
