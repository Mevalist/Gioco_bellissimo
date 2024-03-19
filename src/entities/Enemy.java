package entities;
import main.Game;

import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.*;
import static utils.HelpMethod.*;
public abstract class Enemy extends  Entity{
    private int aniIndex, enemyState, enemyTipe;
    private int aniTick, aniSpeed = 25;
    private boolean firstUpdate = true, inAir;
    private float fallSpeed, gravity = 0.04f * Game.SCALE;
    private float walkSpeed = 1.0f * Game.SCALE, walkDir = LEFT;
    public Enemy(float x, float y, int width, int height, int enemyTipe) {
        super (x, y, width, height);
        this.enemyTipe = enemyTipe;
        initHitbox (x,y,width,height);
    }

    private void updateAnimationTick(){
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount (enemyTipe, enemyState)){
                aniIndex = 0;
            }
        }
    }

    public void update(int[][] lvlData){
        updateMove (lvlData);
        updateAnimationTick ();

    }
    private void updateMove(int[][] lvlData){
        if(firstUpdate){
            if(!IsEntityOnFloor (hitbox, lvlData)){
                inAir = true;
            }
            firstUpdate = false;
        }
        if(inAir){
            if(CanMoveHere (hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)){
                hitbox.y += fallSpeed;
                fallSpeed += gravity;
            }else{
                inAir = false;
                //hitbox.y = GetEntityPosNextWall (hitbox, fallSpeed);
                hitbox.y = GetEntityPosYPlayer (hitbox, fallSpeed);
            }
        }else{
            switch (enemyState){
                case IDLE:
                    enemyState = RUNNING;
                    break;
                case RUNNING:
                        float xSpeed = 0;

                        if(walkDir == LEFT){
                            xSpeed = -walkDir;
                        }else{
                            xSpeed = walkDir;
                        }
                        if(CanMoveHere (hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)){
                            if(IsFloor (hitbox, xSpeed, lvlData)){
                                hitbox.x += xSpeed;
                                return;
                            }
                        }
                        changewalkDir();
                        break;
            }
        }
    }

    private void changewalkDir() {
        if(walkDir == LEFT){
            walkDir = RIGHT;
        }else{
            walkDir = LEFT;
        }
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public void setAniIndex(int aniIndex) {
        this.aniIndex = aniIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }

    public void setEnemyState(int enemyState) {
        this.enemyState = enemyState;
    }
}
