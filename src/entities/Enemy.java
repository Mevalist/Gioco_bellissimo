package entities;
import main.Game;

import java.awt.geom.Rectangle2D;

import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.*;
import static utils.Constants.*;
import static utils.HelpMethod.*;
public abstract class Enemy extends  Entity{
    protected int enemyTipe;
    protected boolean firstUpdate = true;
    protected float walkDir = LEFT;
    protected int tileY;
    protected float attackRange = Game.TILES_SIZE;
    protected boolean active = true;
    protected boolean attackChecked;


    public Enemy(float x, float y, int width, int height, int enemyTipe) {
        super (x, y, width, height);
        this.enemyTipe = enemyTipe;
        maxHealth = GetMaxHealth (enemyTipe);
        currentHealth = maxHealth;
        walkSpeed = 0.2f * Game.SCALE;
    }

    protected void firstUpdateCheck(int[][] lvlData){
            if(!IsEntityOnFloor (hitbox, lvlData)){
                inAir = true;
            }
            firstUpdate = false;
    }

    protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
        if(attackBox.intersects (player.hitbox)){
            player.changeHealth (-GetEnemyDMG (enemyTipe));
        }
        attackChecked = true;
    }

    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        NewState (IDLE);
        active = true;
        airSpeed = 0;
    }
    public void hurt(int amount){
        currentHealth -= amount;
        if(currentHealth <= 0){
            NewState (DIE);
        }else{
            NewState (HURT);
        }

    }
    protected void updateInAir(int[][] lvlData){
        if(CanMoveHere (hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)){
            hitbox.y += airSpeed;
            airSpeed += GRAVITY;
        }else{
            inAir = false;
            //hitbox.y = GetEntityPosNextWall (hitbox, fallSpeed);
            hitbox.y = GetEntityPosYPlayer (hitbox, airSpeed);
            tileY = (int)(hitbox.y / Game.TILES_SIZE);
        }
    }

    protected void move(int[][] lvlData){
        float xSpeed = 0;

        if (walkDir == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
            if (IsFloor(hitbox, xSpeed, lvlData)) {
                hitbox.x += xSpeed;
                return;
            }

        changewalkDir();

    }

    protected void NewState(int enemyState){
        this.state = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }


    protected void updateAnimationTick(){
        aniTick++;
        if(aniTick >= ANI_SPEED){
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount (enemyTipe, state)){
                aniIndex = 0;

                switch (state){
                    case ATTACK, HURT -> state = IDLE;
                    case DIE -> active = false;
                }

            }
        }
    }



    protected void changewalkDir() {
        if(walkDir == LEFT){
            walkDir = RIGHT;
        }else{
            walkDir = LEFT;
        }
    }

    protected boolean canSeePlayer(int[][] lvlData, Player player){
        int playerTilesY = (int) (player.getHitbox ().y / Game.TILES_SIZE);
        if(playerTilesY == tileY){
            if(playerInRange(player)){
                if(IsSightClear(lvlData, hitbox, player.hitbox, tileY)){
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean playerInRange(Player player) {
        int absValue = (int) Math.abs (player.hitbox.x - hitbox.x);
        return absValue <= attackRange * 5;
    }

    protected void turnTowardPlayer(Player player){
        if(player.hitbox.x > hitbox.x){
            walkDir = RIGHT;
        }else{
            walkDir = LEFT;
        }
    }

    protected boolean isPlayerCloseToAttack(Player player){
        int absValue = (int) Math.abs (player.hitbox.x - hitbox.x);
        return absValue <= attackRange;
    }

    /////////////////////////////////////////////////////////////////////////

    public boolean isActive(){
        return active;
    }
}
