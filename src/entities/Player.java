package entities;

import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Constants.*;
import static utils.Constants.ObjectConstants.GetSpriteAmount;
import static utils.Constants.PlayerConstants.*;
import static utils.HelpMethod.*;
public class Player extends Entity{
    private BufferedImage [][]playerAnimation_idle;
    //private int playerAction = IDLE;
    private boolean left, right, jump;
    private boolean moving = false, attacking = false;
    private int [][] lvlData;
    private float xDrawOffset = 4 * Game.SCALE;
    private float yDrawOffset = 0 * Game.SCALE;

    // GRAVITY - JUMPING
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;

    private final  float MAXFALLINGSPEED = 7.9f;

    // STATUS BAR
    private BufferedImage statusBarImg;
    private int statusBarWidth = (int)(192 * Game.SCALE);
    private int statusBarHeight = (int)(58 * Game.SCALE);
    private int statusBarX = (int)(10 * Game.SCALE);
    private int statusBarY = (int)(10 * Game.SCALE);
    private int healthBarWidth = (int)(142 * Game.SCALE);
    private int healthBarHeight = (int)(15 * Game.SCALE);
    private int healthBarX = (int)(35 * Game.SCALE);
    private int healthBarY = (int)(14 * Game.SCALE);
//    private int maxHealth = 100;
//    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;
    private Playing playing;
    // ATTACK BOX
    private int flipX = 0;
    private int flipW = 1;
    private boolean attackChecked;
    private int tyleY = 0;

    public Player(float x, float y,  int width, int height, Playing playing) {
        super (x, y, width, height);
        this.playing = playing;
        this.state = IDLE;
        this.maxHealth = 100;
        this.currentHealth = maxHealth;
        this.walkSpeed = 1.0f * Game.SCALE;
        loadAnimation ();
        initHitbox (20 ,29);
        initAttackBox();
    }

    public void setSpawn(Point spawn){
        this.x = spawn.x;
        this.y = spawn.y;
        hitbox.x = x;
        hitbox.y = y;
    }
    private void initAttackBox() {
        attackBox = new Rectangle2D.Float (x, y, (int)(20*Game.SCALE), (int)(20*Game.SCALE));
    }

    public void update(){
        updateHealth ();

        if(currentHealth <= 0){
            if(state != DIE){
                state = DIE;
                aniTick = 0;
                aniIndex = 0;
                playing.setPlayerDying(true);
            } else if (aniIndex == GetSpriteAmount(DIE) && aniTick >= ANI_SPEED_PLAYER - 1) {
                playing.setGameOver(true);
            } else
                updateAnimationTick();

        //playing.setGameOver(true);
            return;
        }

        updateAttackBox();
        updatePosition();

        if(moving){
            checkPotionTouched();
            checkSpikeTouched();
            tyleY = (int) (hitbox.y / Game.TILES_SIZE);
        }

        if(attacking){
            checkAttack();
        }
        updateAnimationTick();
        setAnimation();


    }

    private void checkSpikeTouched() {
        playing.checkSpikeTouched(this);
    }

    private void checkPotionTouched() {
        playing.checkPotionTouched(hitbox);
    }

    private void checkAttack() {
        if(attackChecked || aniIndex != 1){
            return;
        }
        attackChecked = true;
        playing.chechEnemyHit(attackBox);
        playing.checkObjHit(attackBox);
    }

    private void updateAttackBox() {
        if(right){
            attackBox.x = hitbox.x + hitbox.width +(int)(Game.SCALE * 10);
        }else if(left){
            attackBox.x = hitbox.x - hitbox.width -(int)(Game.SCALE * 10);
        }
        attackBox.y = hitbox.y + (int)(Game.SCALE * 10);
    }

    private void updateHealth() {
        healthWidth = (int) ((currentHealth / (float)maxHealth)  *healthBarWidth);
    }

    public void render(Graphics g, int xLvlOffset/*, int yLvlOffset*/){
        g.drawImage (playerAnimation_idle[state][aniIndex], (int)(hitbox.x - xDrawOffset) - xLvlOffset + flipX, (int)(hitbox.y - yDrawOffset) /* -yLvlOffset*/, width * flipW, height, null);
        //drawHitbox (g, xLvlOffset, yLvlOffset);
        //drawAttackBox(g, xLvlOffset);
        drawUI(g);
    }
    private void drawUI(Graphics g) {
        g.drawImage (statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor (Color.red);
        g.fillRect (healthBarX + statusBarX, healthBarY + statusBarY, healthWidth, healthBarHeight);
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
            xSpeed -= walkSpeed;
            flipX = width;
            flipW = -1;
        }
        if(right){
            xSpeed += walkSpeed;
            flipX = 0;
            flipW = 1;
        }

        if(!inAir){
            if(!IsEntityOnFloor(hitbox, lvlData)){
                inAir = true;
            }
        }

        if(inAir){
            if(CanMoveHere (hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)){
                hitbox.y += airSpeed;
                airSpeed += GRAVITY_PLAYER;
                if(airSpeed > MAXFALLINGSPEED)
                    airSpeed = MAXFALLINGSPEED;
                updateXpos (xSpeed);
            }else {
                hitbox.y = GetEntityPosYPlayer(hitbox, airSpeed);
                if(airSpeed > 0){
                    if(airSpeed >= 4) {
                        changeHealth ((int) (-(airSpeed / MAXFALLINGSPEED) * 5));
                    }
                    resetInAir();
                } else {
                    changeHealth(-2);
                    airSpeed = 0;//fallSpeedAfterCollision;
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

    public void changeHealth(int value){
        currentHealth += value;

        if(currentHealth <= 0){
            currentHealth = 0;
            //gameOver();
        }else if(currentHealth >= maxHealth){
            currentHealth = maxHealth;
        }
    }

    private void setAnimation() {
        int startAnimation = state;

        if(moving){
            state = RUNNING;
        }else{
            state = IDLE;
        }

        if(inAir){
            if(airSpeed < 0){
                state = JUMP;
            }
        }

        if(attacking){
            state = ATTACK;
            if(startAnimation != ATTACK){
                aniIndex = 1;
                aniTick = 0;
                return;
            }
        }

        if(startAnimation != state){
            resetAnimationTick();
        }
    }

    private void resetAnimationTick(){
        aniTick = 0;
        aniIndex = 0;
    }

    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= ANI_SPEED_PLAYER){
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= getSpriteAmount (state)){
                aniIndex= 0;
                attacking = false;
                attackChecked = false;
            }
        }

    }

    private void loadAnimation() {
        BufferedImage img = LoadSave.GetSpriteAtlas (LoadSave.PLAYER_ATLAS);

        playerAnimation_idle = new BufferedImage[5][4];
        for (int r = 0; r< playerAnimation_idle.length; r++){
            for(int i = 0; i<playerAnimation_idle[0].length; i++){
                playerAnimation_idle[r][i] = img.getSubimage (i*64, r*64, 64, 64);
            }
        }

        statusBarImg = LoadSave.GetSpriteAtlas (LoadSave.HEALT_POWER_BAR);
    }

    public void resetBool(){
        left = false;
        right = false;
        //up = false;
        //down = false;
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

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void resetAll() {
        resetBool ();
        jump = false;
        inAir = false;
        attacking= false;
        moving = false;
        state = IDLE;
        currentHealth = maxHealth;
        hitbox.x = x;
        hitbox.y = y;

        if(!IsEntityOnFloor (hitbox, lvlData)){
            inAir =true;
        }
    }

    public void changePower(int value) {
        System.out.println ("VFVEWDFDFBVSF");
    }

    public void kill() {
        currentHealth = 0;
    }

    public int getTyleY() {
        return tyleY;
    }
}
