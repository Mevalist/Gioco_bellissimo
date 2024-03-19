package entities;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.PlayerConstants.*;
import static utils.HelpMethod.*;
public class Player extends Entity{
    private BufferedImage [][]playerAnimation_idle;
    private int aniTick, aniSpeed = 30, aniIndex;
    private int playerAction = IDLE;
    private boolean left, right, up, down, jump;
    private boolean moving = false, attacking = false;
    private float playerSpeed = 1.0f * Game.SCALE;
    private int [][] lvlData;
    private float xDrawOffset = 4 * Game.SCALE;
    private float yDrawOffset = 0 * Game.SCALE;

    // GRAVITY - JUMPING
    private float airSpeed = 0f;
    private float gravity = 0.035f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;

    private final  float maxFallingSpeed = 7.9f;

    private boolean inAir = false;
    public Player(float x, float y,  int width, int height) {
        super (x, y, width, height);
        loadAnimation ();
        initHitbox (x,y,(int)(20*Game.SCALE),(int)(29*Game.SCALE));
    }

    public void update(){
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }
    public void render(Graphics g, int xLvlOffset, int yLvlOffset){
        g.drawImage (playerAnimation_idle[playerAction][aniIndex], (int)(hitbox.x - xDrawOffset) - xLvlOffset, (int)(hitbox.y - yDrawOffset) - yLvlOffset, width, height, null);
        //drawHitbox (g, xLvlOffset, yLvlOffset);
    }
    private void updatePosition() {
        moving = false;

        if (jump){
            jumping();
        }

        //if(!left && !right && !inAir){
        //    return;
        //}

        if(!inAir){
            if((!left && !right) || (left && right)){
                return;
            }
        }

        float xSpeed = 0;

        if(left){
            xSpeed -= playerSpeed;
        }
        if(right){
            xSpeed += playerSpeed;
        }

        if(!inAir){
            if(!IsEntityOnFloor(hitbox, lvlData)){
                inAir = true;
            }
        }

        if(inAir){
            if(CanMoveHere (hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)){
                hitbox.y += airSpeed;
                airSpeed += gravity;
                if(airSpeed > maxFallingSpeed)
                    airSpeed = maxFallingSpeed;
                updateXpos (xSpeed);
            }else {
                hitbox.y = GetEntityPosYPlayer(hitbox, airSpeed);
                if(airSpeed > 0){
                    resetInAir();
                }else {
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXpos (xSpeed);
            }
        }else {
            updateXpos (xSpeed);
        }
        moving = true;
    }

    private void jumping() {
        if(inAir){
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXpos(float xSpeed) {
        if(CanMoveHere (hitbox.x+xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)){
            hitbox.x += xSpeed;
        }else {
            hitbox.x = GetEntityPosNextWall(hitbox, xSpeed);
        }
    }

    private void setAnimation() {
        int startAnimation = playerAction;

        if(moving){
            playerAction = RUNNING;
        }else{
            playerAction = IDLE;
        }

        if(inAir){
            if(airSpeed < 0){
                playerAction = JUMP;
            }
        }

        if(attacking){
            playerAction = ATTACK;
        }

        if(startAnimation != playerAction){
            resetAnimationTick();
        }
    }

    private void resetAnimationTick(){
        aniTick = 0;
        aniIndex = 0;
    }

    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= getSpriteAmount (playerAction)){
                aniIndex= 0;
                attacking = false;
            }
        }

    }

    private void loadAnimation() {
        BufferedImage img = LoadSave.GetSpriteAtlas (LoadSave.PLAYER_ATLAS);

        playerAnimation_idle = new BufferedImage[4][4];
        for (int r = 0; r< playerAnimation_idle.length; r++){
            for(int i = 0; i<playerAnimation_idle[0].length; i++){
                playerAnimation_idle[r][i] = img.getSubimage (i*64, r*64, 64, 64);
            }
        }
    }

    public void resetBool(){
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void loadlvlData(int[][] lvlData){
        this.lvlData = lvlData;
        if(!IsEntityOnFloor (hitbox, lvlData)){
            inAir =true;
        }
    }

    public void setAttacking(boolean attacking){
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}
