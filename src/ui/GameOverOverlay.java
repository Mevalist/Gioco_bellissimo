package ui;

import gamestates.GameStates;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.URMButtons.URM_SIZE;

public class GameOverOverlay {
    private Playing playing;
    private BufferedImage img;
    private int imgX, imgY, imgW, imgH;
    private UrmButtons menu, play;
    public GameOverOverlay(Playing playing){
        this.playing = playing;
        createImg();
        createButtons();
    }
    private void createButtons() {
        int menuX = (int) (350 * Game.SCALE);
        int playX = (int) (455 * Game.SCALE);
        int y = (int) (220 * Game.SCALE);
        play = new UrmButtons(playX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButtons(menuX, y, URM_SIZE, URM_SIZE, 2);

    }

    private void createImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.DEATH_SCREEN);
        imgW = (int) (img.getWidth() * Game.SCALE);
        imgH = (int) (img.getHeight() * Game.SCALE);
        imgX = Game.GAME_WIDTH / 2 - imgW / 2;
        imgY = (int) (100 * Game.SCALE);

    }

    public void draw(Graphics g){
        g.setColor (new Color (0, 0, 0, 200));
        g.fillRect (0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.drawImage (img, imgX, imgY, imgW, imgH, null);

        menu.draw (g);
        play.draw (g);
        //g.setColor (Color.red);
        //g.drawString ("Game Over(esc per continuare)", Game.GAME_WIDTH / 2, 150);
        //g.drawString ("THE SHADOW OF DEFEAT HAS FALLEN ON YOU", 550, 300);

    }

    public void keyPressed(KeyEvent e){

    }

    public void update(){
        menu.update ();
        play.update ();
    }

    private boolean isIn(UrmButtons b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        play.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn(menu, e))
            menu.setMouseOver(true);
        else if (isIn(play, e))
            play.setMouseOver(true);
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(menu, e)) {
            if (menu.isMousePressed()) {
                playing.resetAll();
                playing.setGamestate (GameStates.MENU);
            }
        } else if (isIn(play, e))
            if (play.isMousePressed()){
                playing.resetAll ();
                playing.getGame ().getAudioPlayer ().setLevelSong (playing.getLevelManager ().getLvlIndex ());
            }
        menu.resetBools();
        play.resetBools();
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e))
            menu.setMousePressed(true);
        else if (isIn(play, e))
            play.setMousePressed(true);
    }

}
