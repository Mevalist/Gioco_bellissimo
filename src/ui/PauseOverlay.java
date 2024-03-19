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
    private SoundButton musicButton, sfxButton;
    private UrmButtons menuB, replayB, unpausedB;
    private VolumeButton volumeButton;
    public PauseOverlay(Playing playing){
        this.playing = playing;
        loadBackground();
        createSoundButton();
        createUrmButton();
        createVolumeButton();
    }

    private void createVolumeButton() {
        int vX = (int)(310*Game.SCALE);
        int vY = (int)(363*Game.SCALE);
        volumeButton = new VolumeButton (vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
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

    private void createSoundButton() {
        int soundX = (int)(450*Game.SCALE);
        int musicY = (int)(150*Game.SCALE);
        int sfxY = (int)(210*Game.SCALE);
        musicButton = new SoundButton (soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton (soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    private void loadBackground() {
        background = LoadSave.GetSpriteAtlas (LoadSave.PAUSE_BACKGROUND);
        bgW = (int)(background.getWidth () * Game.SCALE);
        bgH = (int)(background.getHeight () * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int)(40 * Game.SCALE);
    }

    public void update(){
        musicButton.update ();
        sfxButton.update ();

        menuB.update ();
        replayB.update ();
        unpausedB.update ();

        volumeButton.update ();

    }

    public void draw(Graphics g){
        g.drawImage (background, bgX, bgY, bgW, bgH, null);

        musicButton.draw (g);
        sfxButton.draw (g);

        menuB.draw (g);
        replayB.draw (g);
        unpausedB.draw (g);

        volumeButton.draw (g);
    }

    public void mouseDragged(MouseEvent e){
        if(volumeButton.isMousePressed ()){
            volumeButton.change (e.getX ());
        }

    }


    public void mousePressed(MouseEvent e) {
        if(isIn (e, musicButton)){
            musicButton.setMousePressed (true);
        }else if(isIn (e, sfxButton)){
            sfxButton.setMousePressed (true);
        }else if(isIn (e, menuB)){
            menuB.setMousePressed (true);
        }else if(isIn (e, replayB)){
            replayB.setMousePressed (true);
        }else if(isIn (e, unpausedB)){
            unpausedB.setMousePressed (true);
        }else if(isIn (e, volumeButton)){
            volumeButton.setMousePressed (true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if(isIn (e, musicButton)){
            if(musicButton.isMousePressed ()){
                musicButton.setMuted (!musicButton.isMuted ());
            }
        }else if(isIn (e, sfxButton)){
            if(sfxButton.isMousePressed ()){
                sfxButton.setMuted (!sfxButton.isMuted ());
            }
        }else if(isIn (e, menuB)){
            if(menuB.isMousePressed ()){
                GameStates.state = GameStates.MENU;
                playing.unpausedGame ();
            }
        }else if(isIn (e, replayB)){
            if(replayB.isMousePressed ()){
                //EFDBNB NBNFGFGNM HDGHM; JGFHDR
            }
        }else if(isIn (e, unpausedB)){
            if(unpausedB.isMousePressed ()){
                playing.unpausedGame ();
            }
        }
        musicButton.resetBools ();
        sfxButton.resetBools ();
        menuB.resetBools ();
        replayB.resetBools ();
        unpausedB.resetBools ();
        volumeButton.resetBools ();
    }


    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver (false);
        sfxButton.setMouseOver (false);
        menuB.setMouseOver (false);
        replayB.setMouseOver (false);
        unpausedB.setMouseOver (false);
        volumeButton.setMouseOver (false);

        if(isIn (e, musicButton)){
            musicButton.setMouseOver (true);
        }else if(isIn (e, sfxButton)){
            sfxButton.setMouseOver (true);
        }else if(isIn (e, menuB)){
            menuB.setMouseOver (true);
        }else if(isIn (e, replayB)){
            replayB.setMouseOver (true);
        }else if(isIn (e, unpausedB)){
            unpausedB.setMouseOver (true);
        }else if(isIn (e, volumeButton)){
            volumeButton.setMouseOver (true);
        }
    }

    private boolean isIn(MouseEvent e, PauseButton p){
        return p.getBounds ().contains (e.getX (), e.getY ());
    }


}
