package main;

import inputs.KeyBoardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private Game game;
    public GamePanel(Game game){
        mouseInputs = new MouseInputs (this);
        this.game = game;

        setPanelSize();
        addKeyListener (new KeyBoardInputs (this));
        addMouseListener (mouseInputs);
        addMouseMotionListener (mouseInputs);
    }

    public void updateGame(){
    }

    private void setPanelSize() {
        Dimension size = new Dimension (GAME_WIDTH, GAME_HEIGHT);
        setMinimumSize (size);
        setPreferredSize (size);
        setMaximumSize (size);

    }

    public void paintComponent(Graphics g){
        super.paintComponent (g);
        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}
