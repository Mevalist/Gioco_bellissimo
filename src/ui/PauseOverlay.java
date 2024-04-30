package ui;

import gamestates.GameStates;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import static utils.Constants.UI.PauseButton.*;
import static utils.Constants.UI.URMButtons.*;
import static utils.Constants.UI.VolumeButtons.*;
public class PauseOverlay {
    private Playing playing;
    private BufferedImage background;
    private int bgX, bgY, bgW, bgH;
    private AudioOptions audioOptions;
    private UrmButtons menuB, replayB, unpausedB;

    public PauseOverlay(Playing playing){
        this.playing = playing;
        loadBackground();
        audioOptions = playing.getGame ().getAudioOptions ();
        createUrmButton();
    }



    private void createUrmButton() {
        int menuX = (int)(300*Game.SCALE);
        int replayX = (int)(330*Game.SCALE);
        int unpausedX = (int)(360*Game.SCALE);
        int bY = (int)(390*Game.SCALE);
        menuB = new UrmButtons (menuX, bY, URM_SIZE, URM_SIZE, 2);
        replayB = new UrmButtons (replayX, bY, URM_SIZE, URM_SIZE, 1);
        unpausedB = new UrmButtons (unpausedX, bY, URM_SIZE, URM_SIZE, 0);
    }


    private void loadBackground() {
        background = LoadSave.GetSpriteAtlas (LoadSave.PAUSE_BACKGROUND);
        bgW = (int)(background.getWidth () * Game.SCALE);
        bgH = (int)(background.getHeight () * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int)(40 * Game.SCALE);
    }

    public void update(){
        menuB.update ();
        replayB.update ();
        unpausedB.update ();
        audioOptions.update ();
    }

    public void draw(Graphics g){
        g.drawImage (background, bgX, bgY, bgW, bgH, null);

        menuB.draw (g);
        replayB.draw (g);
        unpausedB.draw (g);
        audioOptions.draw (g);
    }

    public void mouseDragged(MouseEvent e){
        audioOptions.mouseDragged (e);
    }


    public void mousePressed(MouseEvent e) {
        if(isIn (e, menuB)){
            menuB.setMousePressed (true);
        }else if(isIn (e, replayB)){
            replayB.setMousePressed (true);
        }else if(isIn (e, unpausedB)){
            unpausedB.setMousePressed (true);
        }else
            audioOptions.mousePressed (e);
    }

    public void mouseReleased(MouseEvent e) {
        if(isIn (e, menuB)){
            if(menuB.isMousePressed ()){
                playing.setGamestate (GameStates.MENU);
                playing.unpausedGame ();
            }
        }else if(isIn (e, replayB)){
            if(replayB.isMousePressed ()){
                playing.resetAll ();
                playing.unpausedGame ();
            }
        }else if(isIn (e, unpausedB)){
            if(unpausedB.isMousePressed ()){
                playing.unpausedGame ();
            }
        }else
            audioOptions.mouseReleased (e);
        menuB.resetBools ();
        replayB.resetBools ();
        unpausedB.resetBools ();
    }


    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver (false);
        replayB.setMouseOver (false);
        unpausedB.setMouseOver (false);

        if(isIn (e, menuB)){
            menuB.setMouseOver (true);
        }else if(isIn (e, replayB)){
            replayB.setMouseOver (true);
        }else if(isIn (e, unpausedB)){
            unpausedB.setMouseOver (true);
        }else
            audioOptions.mouseMoved (e);
    }

    private boolean isIn(MouseEvent e, PauseButton p){
        return p.getBounds ().contains (e.getX (), e.getY ());
    }


}
