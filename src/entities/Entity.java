package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    protected float x, y, airSpeed;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    protected int aniTick, aniIndex;
    protected int state;
    protected boolean inAir = false;
    protected int maxHealth;
    protected int currentHealth;
    protected Rectangle2D.Float attackBox;
    protected float walkSpeed;
    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }


    protected void drawHitbox(Graphics g, int xLvlOffset){
        g.setColor (Color.CYAN);
        g.drawRect ((int)hitbox.x - xLvlOffset, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
    }
    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float (x, y,(int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }
    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }
    public int getEnemyState() {
        return state;
    }

    protected void drawAttBox(Graphics g, int xLvlOffset){
        g.setColor (Color.red);
        g.drawRect ((int)(attackBox.x - xLvlOffset),(int) attackBox.y,(int) attackBox.width,(int) attackBox.height);
    }
    public int getAniIndex() {
        return aniIndex;
    }


}
