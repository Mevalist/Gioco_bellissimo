package main;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;

public class GameWindow {
    private JFrame jframe;

    public GameWindow(GamePanel gamePanel){
        jframe = new JFrame ();
        //jframe.setSize (500, 500);
        jframe.add(gamePanel);
        jframe.setResizable (false);
        //jframe.setLocationRelativeTo (null);
        jframe.setLocation (100, 100);
        jframe.pack ();

        jframe.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

        jframe.setVisible (true);
        jframe.addWindowFocusListener (new WindowFocusListener () {
            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame ().windowFocusLost();
            }
        });
    }
}
