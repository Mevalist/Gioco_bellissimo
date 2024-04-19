package entities;

import gamestates.Playing;
import levels.Level;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.EnemyConstants.*;

public class EnemyManager {
    private Playing playing;
    private BufferedImage [][]kekkoImgs, buxImg;
    private ArrayList<KEKKO> kekkos = new ArrayList<> ();
    private ArrayList<BUX> buxes = new ArrayList<> ();
    public EnemyManager(Playing playing){
        this.playing = playing;
        loadEnemyImgs();
    }

    public void loadEnemies(Level level) {
        //buxes = level.getBuxes();//////////////////////
        kekkos = level.getKekkos ();
    }

    public void update(int[][] lvlData, Player player){
        boolean isAnyActive = false;
        for(KEKKO k : kekkos){
            if(k.isActive ()){
                k.update (lvlData, player);
                isAnyActive = true;
            }
        }
        ///////////////////////////////////////////////////////
        for(BUX b : buxes){
            if(b.isActive ()){
                b.update (lvlData, player);
                isAnyActive = true;
            }
        }

        if(!isAnyActive){
            playing.setLevelCompleted(true);
        }
    }
    public void draw(Graphics g, int xLvlOffset){
        drawKekko(g, xLvlOffset);
        //drawBuxes (g, xLvlOffset);
    }
    private void drawBuxes(Graphics g, int xLvlOffset) {
        for(BUX b : buxes)
            if(b.isActive ()){
                g.drawImage (buxImg[b.getEnemyState ()][b.getAniIndex ()], (int)b.getHitbox ().x- xLvlOffset - BUX_DRAWOFFSET_X + b.flipX (), (int)(b.getHitbox ().y -BUX_DRAWOFFSET_Y), BUX_SIZE * b.flipW (), BUX_SIZE, null);
                b.drawHitbox (g, xLvlOffset);
                b.drawAttBox (g, xLvlOffset);
            }
    }
    private void drawKekko(Graphics g, int xLvlOffset) {
        for(KEKKO k : kekkos)
            if(k.isActive ()){
                g.drawImage (kekkoImgs[k.getEnemyState ()][k.getAniIndex ()], (int)k.getHitbox ().x- xLvlOffset - KEKKO_DRAWOFFSET_X + k.flipX (), (int)(k.getHitbox ().y -KEKKO_DRAWOFFSET_Y), KEKKO_SIZE * k.flipW (), KEKKO_SIZE, null);
                //k.drawHitbox (g, xLvlOffset);
                //k.drawAttBox (g, xLvlOffset);
            }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox){
        for(KEKKO k : kekkos)
            if(k.isActive () && k.state != DIE){
                if(attackBox.intersects (k.getHitbox ())){
                    k.hurt(10);

                }
            }
        for(BUX b : buxes)
            if(b.isActive () && b.state != DIE){
                if(attackBox.intersects (b.getHitbox ())){
                    b.hurt(90);
                }
            }
    }

    private void loadEnemyImgs() {
        kekkoImgs = new BufferedImage[5][4];
        BufferedImage temp = LoadSave.GetSpriteAtlas (LoadSave.KEKKO_SPRITE);
        for(int j = 0; j<kekkoImgs.length; j++){
            for(int i = 0; i<kekkoImgs[0].length; i++){
                kekkoImgs[j][i] = temp.getSubimage (i * KEKKO_DEFAULT_SIZE, j * KEKKO_DEFAULT_SIZE, KEKKO_DEFAULT_SIZE, KEKKO_DEFAULT_SIZE);
            }
        }
        //////////////////////////////////////
        buxImg = new BufferedImage[5][8];
        BufferedImage temp2 = LoadSave.GetSpriteAtlas (LoadSave.BUX_SPRITE);
        for(int j = 0; j<buxImg.length; j++){
            for(int i = 0; i<buxImg[0].length; i++){
                buxImg[j][i] = temp2.getSubimage (i * BUX_DEFAULT_SIZE, j * BUX_DEFAULT_SIZE, BUX_DEFAULT_SIZE, BUX_DEFAULT_SIZE);
            }
        }
    }

    public void resetAllEnemy() {
        for(KEKKO k : kekkos){
            k.resetEnemy();
        }
        for(BUX b : buxes){
            b.resetEnemy();
        }
    }
}
