package gamestates;

import main.Game;
import ui.AudioOptions;
import ui.PauseButton;
import ui.UrmButtons;
import utils.Constants;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.URMButtons.URM_SIZE;

public class GameOptions extends State implements Statemethods{
    private AudioOptions audioOptions;
    private BufferedImage backgroundImg, optBackgroundImg;
    private int bgX, bgY, bgW, bgH;
    private UrmButtons menuB;
    public GameOptions(Game game){
        super(game);
        loadImgs();
        loadButton();
        audioOptions = game.getAudioOptions ();
    }

    private void loadImgs() {
        backgroundImg = LoadSave.GetSpriteAtlas (LoadSave.MENU_BACKGROUND_IMG);
        optBackgroundImg = LoadSave.GetSpriteAtlas (LoadSave.OPTION_MENU);
        bgW = (int) (optBackgroundImg.getWidth() * Game.SCALE);
        bgH = (int) (optBackgroundImg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (33 * Game.SCALE);
    }
    private void loadButton() {
        int menuX = (int) (403 * Game.SCALE);
        int menuY = (int) (360 * Game.SCALE);

        menuB = new UrmButtons (menuX, menuY, URM_SIZE, URM_SIZE, 2);
    }
    @Override
    public void update() {
        menuB.update ();
        audioOptions.update ();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage (backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(optBackgroundImg, bgX, bgY, bgW, bgH, null);

        menuB.draw(g);
        audioOptions.draw(g);
    }

    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged (e);
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuB)) {
            menuB.setMousePressed(true);
        } else
            audioOptions.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuB)) {
            if (menuB.isMousePressed())
                GameStates.state = GameStates.MENU;
        } else
            audioOptions.mouseReleased(e);

        menuB.resetBools();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);

        if (isIn(e, menuB))
            menuB.setMouseOver(true);
        else
            audioOptions.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            GameStates.state = GameStates.MENU;

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds ().contains(e.getX(), e.getY());
    }

}
