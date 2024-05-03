package levels;

import gamestates.GameStates;
import main.Credits;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelManager {
    private Game game;
    public BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int lvlIndex = 0;
    public LevelManager(Game game){
        this.game = game;
        //levelSprite = LoadSave.GetSpriteAtlas (LoadSave.LEVEL_ATLAS);
        importOutsideSprites();
        levels = new ArrayList<> ();
        buildLevels();
    }

    private void buildLevels() {
        BufferedImage[] allImagE = LoadSave.GetAllLevels ();
        for(BufferedImage b : allImagE){
            levels.add (new Level (b));
        }
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

    public void draw(Graphics g, int xLvlOffset /*, int yLvlOffset*/){
        for(int r = 0; r<levels.get (lvlIndex).getLvlData ().length; r++){
            for(int i = 0; i<levels.get (lvlIndex).getLvlData ()[0].length; i++){
                int index = levels.get (lvlIndex).getSpriteIndex (i, r);
                g.drawImage (levelSprite[index],Game.TILES_SIZE * i - xLvlOffset,Game.TILES_SIZE * r /* - yLvlOffset*/, Game.TILES_SIZE, Game.TILES_SIZE,null);
            }
        }

    }
    public void update(){

    }

    public void LoadNextLevel(){
        lvlIndex++;

        if(lvlIndex >= levels.size ()){
            lvlIndex = 0;
            new Credits ();
            GameStates.state = GameStates.MENU;
        }
        Level newLevel = levels.get (lvlIndex);
        game.getPlaying ().getEnemyManager ().loadEnemies (newLevel);
        game.getPlaying ().getPlayer ().loadlvlData (newLevel.getLvlData ());
        game.getPlaying ().setMaxLvlOffsetX (newLevel.getLvlOffset ());
        game.getPlaying ().getObjectManager ().loadObjects(newLevel);
    }

    public Level getCurrentLevel(){
        return levels.get (lvlIndex);
    }

    public int getAmountOfLevels(){
        return levels.size ();
    }

    public int getLvlIndex() {
        return lvlIndex;
    }
}
