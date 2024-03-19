package entities;
import main.Game;

import static utils.Constants.EnemyConstants.*;
public class KEKKO extends Enemy{
    public KEKKO(float x, float y) {
        super (x, y, KEKKO_SIZE, KEKKO_SIZE, KEKKO);
        initHitbox (x, y, (int)(22* Game.SCALE),  (int)(17* Game.SCALE));
    }
}
