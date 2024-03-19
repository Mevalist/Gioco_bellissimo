package gamestates;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.PauseOverlay;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Playing extends State implements Statemethods{
    private Player player;
    private EnemyManager enemyManager;
    private LevelManager levelManager;
    private PauseOverlay pauseOverlay;
    private boolean paused = false;
    private int xLvlOffset, yLvlOffset;
    private int leftBorder = (int)(0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int)(0.8 * Game.GAME_WIDTH);
    private int downBorder = (int)(0.2 * Game.GAME_HEIGHT);
    private int upBorder = (int)(0.8 * Game.GAME_HEIGHT);
    private int lvlTilesWide_X = LoadSave.getLevelData ()[0].length;
    private int maxTilesOffset_X = lvlTilesWide_X - Game.TILES_IN_WIDTH;
    private int maxLvlOffsetX = maxTilesOffset_X * Game.TILES_SIZE;
    private int lvlTilesWide_Y = LoadSave.getLevelData ().length;
    private int maxTilesOffset_Y = lvlTilesWide_Y - Game.TILES_IN_HEIGHT;
    private int maxLvlOffsetY = maxTilesOffset_Y * Game.TILES_SIZE;
    private BufferedImage bk_img;


    public Playing(Game game) {
        super (game);
        initClasses ();
        bk_img = LoadSave.GetSpriteAtlas (LoadSave.BACKGROUND_BELLISSIMO);
    }

    private void initClasses() {
        levelManager = new LevelManager (game);
        enemyManager = new EnemyManager (this);
        player = new Player (200, 200, (int) (32 * Game.SCALE), (int) (32 * Game.SCALE));
        player.loadlvlData (levelManager.getCurrentLevel ().getLvlData ());
        pauseOverlay = new PauseOverlay (this);
    }

    public LevelManager getLevelManager(){
        return levelManager;
    }
    public Player getPlayer(){
        return player;
    }
    public void windowFocusLost(){
        player.resetBool();
    }

    @Override
    public void update() {
        if(!paused){
            levelManager.update ();
            player.update ();
            enemyManager.update (levelManager.getCurrentLevel ().getLvlData ());
            checkcloseToBorder();
        }else {
            pauseOverlay.update ();
        }
    }

    private void checkcloseToBorder() {
        int playerX = (int) player.getHitbox ().x;
        int playerY = (int) player.getHitbox ().y;
        int diffX = playerX - xLvlOffset;
        int diffY = playerY - yLvlOffset;

        if(diffX > rightBorder){
            xLvlOffset += diffX - rightBorder;
        }else if( diffX < leftBorder){
            xLvlOffset += diffX - leftBorder;
        }

        if(diffY > upBorder){
            yLvlOffset += diffY - upBorder;
        }else if( diffY < downBorder){
            yLvlOffset += diffY - downBorder;
        }

        if(xLvlOffset > maxLvlOffsetX) {
            xLvlOffset = maxLvlOffsetX;
        }else if(xLvlOffset < 0){
            xLvlOffset = 0;
        }

        if(yLvlOffset > maxLvlOffsetY) {
            yLvlOffset = maxLvlOffsetY;
        }else if(yLvlOffset < 0){
            yLvlOffset = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage (bk_img, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        levelManager.draw (g, xLvlOffset, yLvlOffset);
        player.render (g, xLvlOffset, yLvlOffset);
        enemyManager.draw (g, xLvlOffset, yLvlOffset);

        if(paused){
            g.setColor (new Color (0,0,0,180));
            g.fillRect (0,0,Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw (g);
        }

    }

    public void mouseDragged(MouseEvent e){
        if(paused){
            pauseOverlay.mouseDragged (e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton () == MouseEvent.BUTTON1){
            player.setAttacking (true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(paused){
            pauseOverlay.mousePressed (e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(paused){
            pauseOverlay.mouseReleased (e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(paused){
            pauseOverlay.mouseMoved (e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode ()){
            case KeyEvent.VK_LEFT:
                player.setLeft (true);
                break;
            case KeyEvent.VK_RIGHT:
                player.setRight (true);
                break;
            case KeyEvent.VK_Z:
                player.setJump(true);
                break;
            case KeyEvent.VK_F:
                paused = !paused;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode ()){
            case KeyEvent.VK_LEFT:
                player.setLeft (false);
                break;
            case KeyEvent.VK_RIGHT:
                player.setRight (false);
                break;
            case KeyEvent.VK_Z:
                player.setJump(false);
                break;
        }
    }

    public void unpausedGame(){
        paused = false;
    }
}
