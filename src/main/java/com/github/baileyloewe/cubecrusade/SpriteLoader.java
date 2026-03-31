package com.github.baileyloewe.cubecrusade;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class SpriteLoader {
    private final Game game;

    public SpriteLoader(Game game) {
        this.game = game;
    }

    public BufferedImage loadImage(String path) {
        synchronized (game) {
            try {
                // Use getResourceAsStream instead of File for JAR compatibility
                InputStream inputStream = getClass().getResourceAsStream(path);
                if (inputStream == null) {
                    throw new RuntimeException("Image file not found: " + path);
                }
                return ImageIO.read(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
