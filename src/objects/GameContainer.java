package objects;
import main.Game;

import static utils.Constants.ObjectConstants.*;


public class GameContainer extends GameObject {
    public GameContainer(int x, int y, int objType) {
        super (x, y, objType);
        createHitbox();
    }

    private void createHitbox() {
        if(objType == BOX){
            initHitbox (32, 25);
            xDrawOffset = (int) (4 * Game.SCALE);
            yDrawOffset = (int) (4 * Game.SCALE);
        }else if(objType == BARREL){
            initHitbox (26, 26);
            xDrawOffset = (int) (6 * Game.SCALE);
            yDrawOffset = (int) (3 * Game.SCALE);
        }

        hitbox.y += yDrawOffset + (int)(Game.SCALE * 2);
        hitbox.x += xDrawOffset / 2;
    }

    public void update(){
        if(doAnimation){
            updateAnimationTick ();
        }
    }
}
