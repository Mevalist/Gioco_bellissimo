package levels;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {
    private Game game;
    public BufferedImage[] levelSprite;
    private Level level;
    public LevelManager(Game game){
        this.game = game;
        //levelSprite = LoadSave.GetSpriteAtlas (LoadSave.LEVEL_ATLAS);
        importOutsideSprites();
        level = new Level (LoadSave.getLevelData ());
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas (LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for(int r = 0; r < 4; r++){
            for(int i = 0; i< 12; i++){
                int index = r*12 + i;
                levelSprite[index] = img.getSubimage (i*32, r*32, 32,32);
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset, int yLvlOffset){
        for(int r = 0; r<level.getLvlData ().length; r++){
            for(int i = 0; i<level.getLvlData ()[0].length; i++){
                int index = level.getSpriteIndex (i, r);
                g.drawImage (levelSprite[index],Game.TILES_SIZE * i - xLvlOffset,Game.TILES_SIZE * r - yLvlOffset, Game.TILES_SIZE, Game.TILES_SIZE,null);
            }
        }

    }
    public void update(){

    }

    public Level getCurrentLevel(){
        return level;
    }
}
