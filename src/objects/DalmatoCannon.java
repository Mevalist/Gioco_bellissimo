package objects;

import main.Game;

public class DalmatoCannon extends GameObject{

    private int tyleY;
    public DalmatoCannon(int x, int y, int objType) {
        super (x, y, objType);
        tyleY = y / Game.TILES_SIZE;
        initHitbox (40, 26);
        hitbox.x -= (int)(Game.SCALE * 4);
        hitbox.y += (int)(Game.SCALE * 6);
    }

    public void update(){
        if(doAnimation){
            updateAnimationTick ();
        }
    }

    public int getTyleY() {
        return tyleY;
    }
}
