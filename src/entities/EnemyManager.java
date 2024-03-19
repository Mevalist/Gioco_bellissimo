package entities;

import gamestates.Playing;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.EnemyConstants.*;

public class EnemyManager {
    private Playing playing;
    private BufferedImage [][]kekkoImgs;
    private ArrayList<KEKKO> kekkos = new ArrayList<> ();
    public EnemyManager(Playing playing){
        this.playing = playing;
        loadEnemyImgs();
        addEnemies();
    }

    private void addEnemies() {
        kekkos = LoadSave.GetKekkos ();
        System.out.println (kekkos);
    }

    public void update(int[][] lvlData){
        for(KEKKO k : kekkos){
            k.update (lvlData);
        }
    }
    public void draw(Graphics g, int xLvlOffset, int yLvlOffset){
        drawKekko(g, xLvlOffset, yLvlOffset);
    }

    private void drawKekko(Graphics g, int xLvlOffset, int yLvlOffset) {
        for(KEKKO k : kekkos){
            g.drawImage (kekkoImgs[k.getEnemyState ()][k.getAniIndex ()], (int)(k.getHitbox ().x - KEKKO_DRAWOFFSET_X) - xLvlOffset, (int)(k.getHitbox ().y -KEKKO_DRAWOFFSET_Y)- yLvlOffset, KEKKO_SIZE, KEKKO_SIZE, null);
        }
    }

    private void loadEnemyImgs() {
        kekkoImgs = new BufferedImage[4][4];
        BufferedImage temp = LoadSave.GetSpriteAtlas (LoadSave.KEKKO_SPRITE);
        for(int j = 0; j<kekkoImgs.length; j++){
            for(int i = 0; i<kekkoImgs[0].length; i++){
                kekkoImgs[j][i] = temp.getSubimage (i * KEKKO_DEFAULT_SIZE, j * KEKKO_DEFAULT_SIZE, KEKKO_DEFAULT_SIZE, KEKKO_DEFAULT_SIZE);
            }
        }
    }
}
