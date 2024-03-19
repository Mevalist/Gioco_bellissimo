package utils;

import main.Game;

import java.awt.geom.Rectangle2D;

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

        int value = lvlData[(int)yIndex][(int)xIndex];
        if((value >= 48 || value < 0) || value != 47){
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
        return isSolid (hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
    }
}
