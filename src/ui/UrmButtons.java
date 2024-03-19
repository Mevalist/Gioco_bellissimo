package ui;

import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import static utils.Constants.UI.URMButtons.*;

public class UrmButtons extends PauseButton{
    private BufferedImage []imgs;
    private int rowIndex, index;
    private boolean mouseOver, mousePressed;
    public UrmButtons(int x, int y, int width, int height, int rowIndex) {
        super (x, y, width, height);
        this.rowIndex = rowIndex;
        loadImgs();
    }

    private void loadImgs() {
        BufferedImage temp = LoadSave.GetSpriteAtlas (LoadSave.URM_BUTTONS);
        imgs = new BufferedImage[3];
        for(int i = 0; i< imgs.length; i++){
            imgs[i] = temp.getSubimage (i * URM_DF_SIZE, rowIndex * URM_DF_SIZE, URM_DF_SIZE, URM_DF_SIZE);
        }
    }

    public void update(){
        index = 0;
        if(mouseOver){
            index = 1;
        }else if(mousePressed){
            index =2;
        }
    }
    public void draw(Graphics g){
        g.drawImage (imgs[index], x, y, URM_SIZE, URM_SIZE, null);
    }
    public void resetBools(){
        mouseOver = false;
        mousePressed = false;
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
}
