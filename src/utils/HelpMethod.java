package utils;

import entities.BUX;
import entities.KEKKO;
import main.Game;
import objects.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.ObjectConstants.*;

public class HelpMethod {

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData){
        if(!isSolid (x,y,lvlData)){
            if(!isSolid (x+width, y+height, lvlData)){
                if(!isSolid (x+width, y, lvlData)){
                    if(!isSolid (x,y+height, lvlData)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean CanCannonSeePlayer(int [][]lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile){
        int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);

        if (firstXTile > secondXTile)
            return IsAllTilesClear(secondXTile, firstXTile, yTile, lvlData);
        else
            return IsAllTilesClear(firstXTile, secondXTile, yTile, lvlData);
    }
    public static boolean IsAllTilesClear(int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++)
            if (isTileSolid(xStart + i, y, lvlData))
                return false;
        return true;
    }
    private static boolean isSolid(float x, float y, int[][]lvlData){
        int maxWidth = lvlData[0].length * Game.TILES_SIZE;
        int maxHeight = lvlData.length * Game.TILES_SIZE;
        if(x<0 || x >= maxWidth){
            return true;
        }
        if(y<0 || y >= maxHeight){
            return true;
        }

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        return isTileSolid ((int)xIndex,(int)yIndex, lvlData);
    }

    private static boolean isTileSolid(int x, int y, int[][]lvlData){
        int value = lvlData[y][x];
        if((value >= 48 || value < 0) || (value != 47 && value != 46)){
            return true;
        }
        return false;
    }
    public static float GetEntityPosNextWall(Rectangle2D.Float hitbox, float  xSpeed){
        int currentTile = (int)(hitbox.x / Game.TILES_SIZE);
        if(xSpeed > 0){
            //right
            int tileXpos = currentTile * Game.TILES_SIZE;
            int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
            return tileXpos + xOffset - 1;
        }else{
            //left
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static float GetEntityPosYPlayer(Rectangle2D.Float hitbox, float  airSpeed){
        int currentTile = (int)(hitbox.y / Game.TILES_SIZE);
        if(airSpeed > 0){
            //falling
            int tileYpos = currentTile * Game.TILES_SIZE;
            int yOffset = (int)(Game.TILES_SIZE - hitbox.height);
            return tileYpos + yOffset - 1;
        }else{
            //jumping
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int [][]lvlData){
        if(!isSolid (hitbox.x, hitbox.y+ hitbox.height + 1, lvlData)){
            if(!isSolid (hitbox.x+ hitbox.width, hitbox.y+ hitbox.height + 1, lvlData)){
                return false;
            }
        }
        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int [][]lvlData){
        if(xSpeed > 0){
            return isSolid (hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData);
        }else
            return isSolid (hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
    }

    public static boolean isAllTileWalkable(int xStart, int xEnd, int y, int [][]lvlData){
        if(IsAllTilesClear (xStart, xEnd, y, lvlData))
            for(int i = 0; i < xEnd - xStart; i++) {
                if(!isTileSolid(xStart + i, y + 1, lvlData)){
                    return false;
                }
            }
        return true;
    }
    public static boolean IsSightClear(int [][]lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile){
        int firstxXtile = (int)(firstHitbox.x / Game.TILES_SIZE);
        int secondxXtile = (int)(secondHitbox.x / Game.TILES_SIZE);

        if(firstxXtile > secondxXtile){
            return isAllTileWalkable (secondxXtile, firstxXtile, yTile, lvlData);
        }else{
            return isAllTileWalkable (firstxXtile, secondxXtile, yTile, lvlData);
        }
    }

    public static boolean IsProjectilesHittingLevel(Projectiles p, int[][] lvlData){
        return isSolid(p.getHitbox().x + p.getHitbox().width / 2, p.getHitbox().y + p.getHitbox().height / 2, lvlData);
    }

    public static int[][] getLevelData(BufferedImage img){
        int[][] lvlGame = new int[img.getHeight ()][img.getWidth ()];

        for(int r = 0; r<img.getHeight (); r++){
            for (int i = 0; i<img.getWidth (); i++){
                Color color = new Color (img.getRGB (i, r));
                int value = color.getRed ();
                if(value >= 48){
                    value = 0;
                }
                lvlGame[r][i] = value;
            }
        }
        return lvlGame;
    }

    public static ArrayList<KEKKO> GetKekkos(BufferedImage img){
        ArrayList<KEKKO> list = new ArrayList<> ();
        for(int r = 0; r<img.getHeight (); r++){
            for (int i = 0; i<img.getWidth (); i++){
                Color color = new Color (img.getRGB (i, r));
                int value = color.getGreen ();
                if(value == Constants.EnemyConstants.KEKKO){
                    list.add (new KEKKO (i * Game.TILES_SIZE, r * Game.TILES_SIZE));
                }
            }
        }
        return list;
    }

    public static ArrayList<BUX> GetBuxes(BufferedImage img){
        ArrayList<BUX> list = new ArrayList<> ();
        for(int r = 0; r<img.getHeight (); r++){
            for (int i = 0; i<img.getWidth (); i++){
                Color color = new Color (img.getRGB (i, r));
                int value = color.getGreen ();
                if(value == Constants.EnemyConstants.BUX){
                    list.add (new BUX (i * Game.TILES_SIZE, r * Game.TILES_SIZE));
                }
            }
        }
        return list;
    }

    public static Point GetPlayerSpawn(BufferedImage img){
        for(int r = 0; r<img.getHeight (); r++){
            for (int i = 0; i<img.getWidth (); i++){
                Color color = new Color (img.getRGB (i, r));
                int value = color.getGreen ();
                if(value == 255){
                    return new Point (i * Game.TILES_SIZE, r * Game.TILES_SIZE);
                }
            }
        }
        return new Point (1 * Game.TILES_SIZE, 1 * Game.TILES_SIZE);
    }

    public static ArrayList<Potion> GetPotions(BufferedImage img){
        ArrayList<Potion> list = new ArrayList<> ();
        for(int r = 0; r<img.getHeight (); r++){
            for (int i = 0; i<img.getWidth (); i++){
                Color color = new Color (img.getRGB (i, r));
                int value = color.getBlue ();
                if(value == RED_POTION || value == BLUE_POTION){
                    list.add (new Potion (i * Game.TILES_SIZE, r * Game.TILES_SIZE, value));
                }
            }
        }
        return list;
    }
    public static ArrayList<GameContainer> GetGameContainer(BufferedImage img){
        ArrayList<GameContainer> list = new ArrayList<> ();
        for(int r = 0; r<img.getHeight (); r++){
            for (int i = 0; i<img.getWidth (); i++){
                Color color = new Color (img.getRGB (i, r));
                int value = color.getBlue ();
                if(value == BOX || value == BARREL){
                    list.add (new GameContainer (i * Game.TILES_SIZE, r * Game.TILES_SIZE, value));
                }
            }
        }
        return list;
    }

    public static ArrayList<Spike> GetSpikes(BufferedImage img) {
        ArrayList<Spike> list = new ArrayList<> ();
        for(int r = 0; r<img.getHeight (); r++){
            for (int i = 0; i<img.getWidth (); i++){
                Color color = new Color (img.getRGB (i, r));
                int value = color.getBlue ();
                if(value == SPIKE){
                    list.add (new Spike (i * Game.TILES_SIZE, r * Game.TILES_SIZE, value));
                }
            }
        }
        return list;
    }

    public static ArrayList<DalmatoCannon> GetDalmatoCannon(BufferedImage img) {
        ArrayList<DalmatoCannon> list = new ArrayList<> ();
        for(int r = 0; r<img.getHeight (); r++){
            for (int i = 0; i<img.getWidth (); i++){
                Color color = new Color (img.getRGB (i, r));
                int value = color.getBlue ();
                if(value == DALMATOCannon_LEFT || value == DALMATOCannon_RIGHT){
                    list.add (new DalmatoCannon (i * Game.TILES_SIZE, r * Game.TILES_SIZE, value));
                }
            }
        }
        return list;
    }
}
