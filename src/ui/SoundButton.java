package ui;

import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import static utils.Constants.UI.PauseButton.*;

public class SoundButton extends PauseButton{

    private BufferedImage[][] soundImgs;
    private boolean mouseOver, mousePressed;
    private boolean muted;
    private  int rowIndex, colIndex;
    public SoundButton(int x, int y, int width, int height) {
        super (x, y, width, height);
        loadSoundImgs();
    }

    private void loadSoundImgs() {
        BufferedImage temp = LoadSave.GetSpriteAtlas (LoadSave.SOUND_BUTTONS);
        soundImgs = new BufferedImage[2][3];
        for(int r = 0; r<soundImgs.length; r++){
            for(int i = 0; i < soundImgs[r].length; i++){
                soundImgs[r][i] = temp.getSubimage (i * SOUND_DF_SIZE, r * SOUND_DF_SIZE, SOUND_DF_SIZE, SOUND_DF_SIZE);
            }
        }
    }

    public void update(){
        if(muted){
            rowIndex = 1;
        }else {
            rowIndex = 0;
        }

        colIndex = 0;
        if(mouseOver){
            colIndex = 1;
        }else if(mousePressed) {
            colIndex = 2;
        }
    }

    public void resetBools(){
        mouseOver = false;
        mousePressed = false;
    }

    public void draw(Graphics g){
        g.drawImage (soundImgs[rowIndex][colIndex], x, y, width, height, null);
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
}
