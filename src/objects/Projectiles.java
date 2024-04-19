package objects;

import main.Game;

import java.awt.geom.Rectangle2D;
import static utils.Constants.Projectiles.*;
public class Projectiles {
    private Rectangle2D.Float hitbox;
    private int dir;
    private boolean active = true;

    public Projectiles(int x, int y, int dir) {
        int xOffset = (int) (-3 * Game.SCALE);
        int yOffset = (int) (5 * Game.SCALE);

        if (dir == 1)
            xOffset = (int) (29 * Game.SCALE);

        hitbox = new Rectangle2D.Float(x + xOffset, y + yOffset, PlASMABALL_SIZE, PlASMABALL_SIZE);
        this.dir = dir;
    }

    public void updatePos() {
        hitbox.x += dir * SPEED_PLASMABALL;
    }

    public void setPos(int x, int y) {
        hitbox.x = x;
        hitbox.y = y;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}
