package utils;

import entities.KEKKO;
import main.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import static utils.Constants.EnemyConstants.*;

public class LoadSave {
    public static final String PLAYER_ATLAS = "player_sprite.png";
    public static final String LEVEL_ATLAS = "firstLVL_sprite_newTexture1.png";
    //public static final String LEVEL_ONE_DATA = "level_one_data.png";
    public static final String LEVEL_ONE_DATA = "level_one_data_ext1.png";
    public static final String MENU_BUTTONS = "menu_sprite.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String PAUSE_BACKGROUND = "pause_background.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String MENU_BACKGROUND_IMG = "backgrd.png";
    public static final String BACKGROUND_BELLISSIMO = "background_bellissimo.png";
    public static final String KEKKO_SPRITE = "kekko_sprite.png";
    public static BufferedImage GetSpriteAtlas(String fileName){
        BufferedImage img = null;
        InputStream ins = LoadSave.class.getResourceAsStream ("/"+fileName);

        try {
            img = ImageIO.read (ins);

        } catch (IOException e) {
            throw new RuntimeException (e);
        } finally {
            try{
                ins.close ();
            }catch (IOException e){
                e.printStackTrace ();
            }
        }
        return img;
    }

    public static ArrayList<KEKKO> GetKekkos(){
        BufferedImage img = GetSpriteAtlas (LEVEL_ONE_DATA);
        ArrayList<KEKKO> list = new ArrayList<> ();
        for(int r = 0; r<img.getHeight (); r++){
            for (int i = 0; i<img.getWidth (); i++){
                Color color = new Color (img.getRGB (i, r));
                int value = color.getGreen ();
                if(value == KEKKO){
                    list.add (new KEKKO (i * Game.TILES_SIZE, r * Game.TILES_SIZE));
                }
            }
        }
        return list;
    }

    public static int[][] getLevelData(){
        BufferedImage img = GetSpriteAtlas (LEVEL_ONE_DATA);
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
}
