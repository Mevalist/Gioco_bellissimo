package gamestates;

import main.Game;
import ui.MenuButton;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State implements Statemethods{
    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage backgroundImage, bk_img;
    private int menuX, menuY, menuWidth, menuHeigth;
    public Menu(Game game) {
        super (game);
        loadButtons();
        loadBackground();
        bk_img = LoadSave.GetSpriteAtlas (LoadSave.MENU_BACKGROUND_IMG);
    }

    private void loadBackground() {
        backgroundImage = LoadSave.GetSpriteAtlas (LoadSave.MENU_BACKGROUND);
        menuWidth = (int) (backgroundImage.getWidth ()*Game.SCALE);
        menuHeigth = (int) (backgroundImage.getHeight ()*Game.SCALE);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int)(45 * Game.SCALE);
    }

    private void loadButtons() {
        buttons[0] = new MenuButton (Game.GAME_WIDTH/2, (int) (150*Game.SCALE),0,GameStates.PLAYING);
        buttons[1] = new MenuButton (Game.GAME_WIDTH/2, (int) (220*Game.SCALE),1,GameStates.OPTIONS);
        buttons[2] = new MenuButton (Game.GAME_WIDTH/2, (int) (290*Game.SCALE),2,GameStates.QUIT);
    }

    @Override
    public void update() {
        for (MenuButton mg : buttons){
            mg.update ();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage (bk_img, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage (backgroundImage, menuX, menuY, menuWidth, menuHeigth, null);
        for (MenuButton mg : buttons){
            mg.draw (g);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mg : buttons){
            if(isIn (e, mg)){
                mg.setMousePressed (true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mg : buttons){
            if(isIn (e, mg)){
                if (mg.isMousePressed ()){
                    mg.applyGamestate ();
                }
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for (MenuButton mg : buttons){
            mg.resetBools ();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mg : buttons) {
            mg.setMouseOver (false);
        }

        for (MenuButton mg : buttons){
            if(isIn (e, mg)){
                mg.setMouseOver (true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode () == KeyEvent.VK_G){
            GameStates.state = GameStates.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
