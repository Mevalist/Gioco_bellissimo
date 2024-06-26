package entities;
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.Constants.Directions.LEFT;
import static utils.Constants.Directions.RIGHT;
import static utils.Constants.EnemyConstants.*;
import static utils.HelpMethod.*;

public class KEKKO extends Enemy{
    private int attackBoxOffsetX;
    public KEKKO(float x, float y) {
        super (x, y, KEKKO_SIZE, KEKKO_SIZE, KEKKO);
        initHitbox (22,  17);
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float (x, y, (int)(30* Game.SCALE),  (int)(17* Game.SCALE));
        attackBoxOffsetX = (int)(Game.SCALE * 4);
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

                    if(aniIndex == 2 && !attackChecked){

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
