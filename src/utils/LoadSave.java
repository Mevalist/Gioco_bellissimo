package utils;

import entities.KEKKO;
import main.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import static utils.Constants.EnemyConstants.*;

public class LoadSave {
    public static final String PLAYER_ATLAS = "player_sprite_update1.0.png";
    public static final String LEVEL_ATLAS = "texture.png";
    //public static final String LEVEL_ONE_DATA = "level_one_data_ext1.2_width.png";
    public static final String MENU_BUTTONS = "menu_sprite.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String PAUSE_BACKGROUND = "pause.background.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String MENU_BACKGROUND_IMG = "backgrd.png";
    public static final String BACKGROUND_BELLISSIMO = "background_bellissimo.png";
    public static final String KEKKO_SPRITE = "kekko_sprite.png";
    public static final String BUX_SPRITE = "bux_sprite.png";
    public static final String HEALT_POWER_BAR = "healt_power_bar.png";
    public static final String LEVEL_COMPLETED = "level_completed.png";
    public static final String POTION_ATLAS = "potion_sprite.png";
    public static final String CONTAINER_ATLAS = "object_sprite.png";
    public static final String TRAP_ATLAS = "trap_sprite.png";
    public static final String DALMATOCannon_ATLAS = "dalmatocannon_sprite.png";
    public static final String PLASMA_BALL = "plasmaball_sprite.png";
    public static final String DEATH_SCREEN = "death_screen.png";
    public static final String OPTION_MENU = "options_background.png";
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

    public static BufferedImage[] GetAllLevels(){
        URL url = LoadSave.class.getResource ("/lvls");
        File file = null;

        try {
            file = new File (url.toURI ());
        } catch (URISyntaxException e) {
            throw new RuntimeException (e);
        }

        File[] files = file.listFiles ();
        File[] filesSorted = new File[files.length];

        for(int i = 0; i<filesSorted.length; i++){
            for(int g = 0; g < files.length; g++){
                if(files[g].getName ().equals ((i+1)+ ".png")){
                    filesSorted[i] = files[g];
                }
            }
        }
        BufferedImage[] imgs= new BufferedImage[filesSorted.length];
        for(int i = 0; i<imgs.length; i++){
            try {
                imgs[i] = ImageIO.read (filesSorted[i]);
            } catch (IOException e) {
                throw new RuntimeException (e);
            }
        }
        return imgs;
    }

}
