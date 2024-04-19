package entities;

import main.Game;

import java.awt.geom.Rectangle2D;

import static utils.Constants.Directions.RIGHT;
import static utils.Constants.EnemyConstants.*;
public class BUX extends Enemy{
    private int attackBoxOffsetX;
    public BUX(float x, float y) {
        super (x, y, BUX_SIZE, BUX_SIZE, BUX);
        initHitbox (22, 56);
        initAttacBox();
    }

    private void initAttacBox() {
        attackBox = new Rectangle2D.Float (x, y, (int)(Game.SCALE * 30), (int)(Game.SCALE * 56));
        attackBoxOffsetX = (int)(Game.SCALE * 38);
    }

    private void updateMove(int[][] lvlData, Player player){
        if(firstUpdate){
            firstUpdateCheck (lvlData);
        }
        if(inAir){
            updateInAir (lvlData);
        }else{
            switch (state){
                case IDLE:
                    NewState (RUNNING);
                    break;
                case RUNNING:
                    if (canSeePlayer (lvlData, player)){
                        turnTowardPlayer (player);
                        if(isPlayerCloseToAttack (player)){
                            NewState (ATTACK);
                        }
                    }
                    move (lvlData);
                    break;
                case ATTACK:
                    if(aniIndex == 0){
                        attackChecked = false;
                    }
                    if(aniIndex == 7 && !attackChecked){
                        checkPlayerHit(attackBox, player);
                    }
                    break;
                case HURT:
                    break;
            }
        }
    }
    public void update(int[][] lvlData, Player player){
        updateMove (lvlData, player);
        updateAnimationTick ();
        updateAttackBox();
    }

    private void updateAttackBox() {
        attackBox.x = hitbox.x - attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }
    public int flipX(){
        if(walkDir == RIGHT){
            return width;
        }else{
            return 0;
        }
    }

    public int flipW(){
        if(walkDir == RIGHT){
            return -1;
        }else{
            return 1;
        }
    }
}
