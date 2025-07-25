package com.main;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class Window extends Canvas {

    @Serial
    private static final long serialVersionUID = -240840600533728354L;

    public Window(int width, int height, String title, Game game) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        game.setPreferredSize(new Dimension(width, height));
        game.setMaximumSize(new Dimension(width, height));
        game.setMinimumSize(new Dimension(width, height));
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.start();
    }



}
