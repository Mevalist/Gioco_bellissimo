package gamestates;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import objects.GameObject;
import objects.ObjectManager;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Playing extends State implements Statemethods{
    private Player player;
    private LevelCompletedOverlay levelCompletedOverlay;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private LevelManager levelManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private boolean paused = false;
    private int xLvlOffset, yLvlOffset;
    private int leftBorder = (int)(0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int)(0.8 * Game.GAME_WIDTH);
    private int downBorder = (int)(0.2 * Game.GAME_HEIGHT);
    private int upBorder = (int)(0.8 * Game.GAME_HEIGHT);
    //private int lvlTilesWide_X = LoadSave.getLevelData ()[0].length;
    //private int maxTilesOffset_X = lvlTilesWide_X - Game.TILES_IN_WIDTH;
    private int maxLvlOffsetX;
    //private int lvlTilesWide_Y = LoadSave.getLevelData ().length;
    //private int maxTilesOffset_Y = lvlTilesWide_Y - Game.TILES_IN_HEIGHT;
    //private int maxLvlOffsetY;
    private BufferedImage bk_img;
    private boolean gameOver, lvlCompleted = false;
    private boolean playerDying;


    public Playing(Game game) {
        super (game);
        initClasses ();
        bk_img = LoadSave.GetSpriteAtlas (LoadSave.BACKGROUND_BELLISSIMO);
        calcLvlOffset();
        loadStartLevel();
    }

    public void loadNextLvl(){
        resetAll ();
        levelManager.LoadNextLevel();
        player.setSpawn (levelManager.getCurrentLevel ().getPlayerSpawn ());
    }
    private void loadStartLevel() {
        enemyManager.loadEnemies (levelManager.getCurrentLevel ());
        objectManager.loadObjects (levelManager.getCurrentLevel ());
    }

    private void calcLvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel ().getLvlOffset ();
    }

    private void initClasses() {
        levelManager = new LevelManager (game);
        enemyManager = new EnemyManager (this);
        objectManager = new ObjectManager (this);
        player = new Player (200, 200, (int) (32 * Game.SCALE), (int) (32 * Game.SCALE), this);
        player.loadlvlData (levelManager.getCurrentLevel ().getLvlData ());
        //player.setSpawn (levelManager.getCurrentLevel ().getPlayerSpawn ());
        pauseOverlay = new PauseOverlay (this);
        gameOverOverlay = new GameOverOverlay (this);
        levelCompletedOverlay = new LevelCompletedOverlay (this);
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
        if (paused) {
            pauseOverlay.update ();
        } else if (lvlCompleted) {
            levelCompletedOverlay.update ();
        } else if (gameOver) {
            gameOverOverlay.update();
        } else if(playerDying){
            player.update ();
        } else {
            levelManager.update ();
            objectManager.update (levelManager.getCurrentLevel ().getLvlData (), player);
            player.update ();
            enemyManager.update (levelManager.getCurrentLevel ().getLvlData (), player);
            checkcloseToBorder();
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
        if(xLvlOffset > maxLvlOffsetX) {
            xLvlOffset = maxLvlOffsetX;
        }else if(xLvlOffset < 0){
            xLvlOffset = 0;
        }
        /*
        if(diffY > upBorder){

            yLvlOffset += diffY - upBorder;
        }else if( diffY < downBorder){
            yLvlOffset += diffY - downBorder;
        }



        if(yLvlOffset > maxLvlOffsetY) {
            yLvlOffset = maxLvlOffsetY;
        }else if(yLvlOffset < 0){
            yLvlOffset = 0;
        }
        */
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage (bk_img, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        levelManager.draw (g, xLvlOffset/*, yLvlOffset*/);
        player.render (g, xLvlOffset/*, yLvlOffset*/);
        enemyManager.draw (g, xLvlOffset);
        objectManager.draw (g, xLvlOffset);

        if(paused){
            g.setColor (new Color (0,0,0,180));
            g.fillRect (0,0,Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw (g);
        }else if(gameOver){
            gameOverOverlay.draw (g);
        }else if(lvlCompleted){
            levelCompletedOverlay.draw (g);
        }

    }

    public void mouseDragged(MouseEvent e){
        if(!gameOver)
            if(paused){
                pauseOverlay.mouseDragged (e);
            }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!gameOver){
            if(e.getButton () == MouseEvent.BUTTON1){
                player.setAttacking (true);
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!gameOver) {
            if (paused) {
                pauseOverlay.mousePressed (e);
            } else if (lvlCompleted) {
                levelCompletedOverlay.mousePressed (e);
            }
        }else {
            gameOverOverlay.mousePressed (e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!gameOver){
            if(paused){
                pauseOverlay.mouseReleased (e);
            } else if (lvlCompleted) {
                levelCompletedOverlay.mouseReleased (e);
            }
        }else {
            gameOverOverlay.mouseReleased (e);
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!gameOver){
            if(paused){
                pauseOverlay.mouseMoved (e);
            } else if (lvlCompleted) {
                levelCompletedOverlay.mouseMoved (e);
            }
        }else {
            gameOverOverlay.mouseMoved (e);
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(gameOver)
            gameOverOverlay.keyPressed (e);
        else
            switch(e.getKeyCode ()){
                case KeyEvent.VK_LEFT:
                    player.setLeft (true);
                    break;
                case KeyEvent.VK_RIGHT:
                    player.setRight (true);
                    break;
                case KeyEvent.VK_UP:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_F:
                    paused = !paused;
                    break;
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!gameOver)
            switch(e.getKeyCode ()){
                case KeyEvent.VK_LEFT:
                    player.setLeft (false);
                    break;
                case KeyEvent.VK_RIGHT:
                    player.setRight (false);
                    break;
                case KeyEvent.VK_UP:
                    player.setJump(false);
                    break;
            }
    }

    public void unpausedGame(){
        paused = false;
    }

    public void resetAll(){
        gameOver = false;
        paused = false;
        lvlCompleted = false;
        playerDying = false;
        player.resetAll();
        enemyManager.resetAllEnemy();
        objectManager.resetAllObj();

    }
    public void chechEnemyHit(Rectangle2D.Float attackBox){
        enemyManager.checkEnemyHit (attackBox);
    }

    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
    }
    public EnemyManager getEnemyManager(){
        return enemyManager;
    }
    public void setMaxLvlOffsetX(int lvlOffset){
        this.maxLvlOffsetX = lvlOffset;
    }

    public void setLevelCompleted(boolean a) {
        this.lvlCompleted = a;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public void checkPotionTouched(Rectangle2D.Float hitbox) {
        objectManager.checkObjTouched (hitbox);
    }

    public void checkObjHit(Rectangle2D.Float attackBox) {
        objectManager.checkObjHit (attackBox);
    }

    public void checkSpikeTouched(Player player) {
        objectManager.checkSpikeTouched (player);
    }

    public void setPlayerDying(boolean playerDying) {
        this.playerDying = playerDying;
    }
}
