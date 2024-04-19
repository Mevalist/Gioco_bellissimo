package objects;

import entities.Player;
import gamestates.Playing;
import levels.Level;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import static utils.Constants.ObjectConstants.*;
import static utils.Constants.Projectiles.*;
import static utils.HelpMethod.CanCannonSeePlayer;
import static utils.HelpMethod.IsProjectilesHittingLevel;

public class ObjectManager {
    private Playing playing;
    private BufferedImage[][] potionImg, containerImg;
    private BufferedImage[] spikeImg, dalmatoCannonImg;
    private BufferedImage plasmaBallImg;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;
    private ArrayList<Spike> spikes;
    private ArrayList<DalmatoCannon> dalmatoCannons;
    private ArrayList<Projectiles> projectiles = new ArrayList<> ();
    public ObjectManager(Playing playing){
        this.playing = playing;
        loadImgs();
        potions = new ArrayList<> ();
        containers = new ArrayList<> ();
        spikes = new ArrayList<> ();
        //dalmatoCannons = new ArrayList<> ();
    }

    public void loadObjects(Level newLevel) {
        potions = new ArrayList<> (newLevel.getPotions ());
        containers = new ArrayList<> (newLevel.getContainers ());
        spikes = newLevel.getSpikes ();
        dalmatoCannons = newLevel.getDalmatoCannons ();
        projectiles.clear ();
    }

    private void loadImgs() {
        BufferedImage potionTemp = LoadSave.GetSpriteAtlas (LoadSave.POTION_ATLAS);
        potionImg = new BufferedImage[2][7];
        for(int r = 0; r< potionImg.length; r++){
            for(int i = 0; i<potionImg[r].length; i++){
                potionImg[r][i] = potionTemp.getSubimage (12*i, 16*r, 12, 16);
            }
        }

        BufferedImage containerTemp = LoadSave.GetSpriteAtlas (LoadSave.CONTAINER_ATLAS);
        containerImg = new BufferedImage[2][8];
        for(int r = 0; r< containerImg.length; r++){
            for(int i = 0; i<containerImg[r].length; i++){
                containerImg[r][i] = containerTemp.getSubimage (40*i, 30*r, 40, 30);
            }
        }

        BufferedImage spikeTemp = LoadSave.GetSpriteAtlas (LoadSave.TRAP_ATLAS);
        spikeImg = new BufferedImage[1];
        for(int i = 0; i<spikeImg.length; i++){
            spikeImg[i] = spikeTemp.getSubimage (32*i, 32*i, 32, 32);
        }

        BufferedImage cannonTemp = LoadSave.GetSpriteAtlas (LoadSave.DALMATOCannon_ATLAS);
        dalmatoCannonImg = new BufferedImage[7];
        for(int i = 0; i<dalmatoCannonImg.length; i++){
            dalmatoCannonImg[i] = cannonTemp.getSubimage (40 * i, 0, 40, 26);
        }

        plasmaBallImg = LoadSave.GetSpriteAtlas (LoadSave.PLASMA_BALL);
    }

    public void update(int[][] lvlData, Player player){
        for(Potion p : potions){
            if(p.isActive ()){
                p.update ();
            }
        }

        for(GameContainer g : containers){
            if(g.isActive ()){
                g.update ();
            }
        }

        updateDalmatoCannons(lvlData, player);
        updateProjectiles(lvlData, player);
    }

    private void updateProjectiles(int[][] lvlData, Player player) {
        for(Projectiles p : projectiles){
            if(p.isActive ()){
                p.updatePos ();
                if(p.getHitbox ().intersects (player.getHitbox ())){
                    player.changeHealth (-50);
                    p.setActive (false);
                } else if (IsProjectilesHittingLevel (p, lvlData)) {
                    p.setActive (false);
                }
            }
        }
    }

    private void updateDalmatoCannons(int[][] lvlData, Player player) {
        for(DalmatoCannon d : dalmatoCannons){
            if(!d.doAnimation){
                if(d.getTyleY () == player.getTyleY()) {
                    if(IsPlayerInRange(d, player)){
                        if(IsPlayerInFrontOfCannon(d, player)){
                            if(CanCannonSeePlayer(lvlData, player.getHitbox (), d.getHitbox (), d.getTyleY ())){
                                d.setAnimation (true);
                            }
                        }
                    }
                }
            }
            d.update ();
            if(d.getAniIndex () == 6 && d.getAniTick() == 0){
                shootCannon (d);
            }
        }
    }

    private void shootCannon(DalmatoCannon d) {
        //d.setAnimation (true);
        int dir = 1;
        if(d.getObjType () == DALMATOCannon_LEFT){
            dir *= -1;
        }
        projectiles.add (new Projectiles ((int)d.getHitbox ().x, (int)d.getHitbox ().y, dir));
    }

    private boolean IsPlayerInFrontOfCannon(DalmatoCannon d, Player player) {
        if (d.getObjType() == DALMATOCannon_LEFT) {
            if (d.getHitbox().x > player.getHitbox().x)
                return true;

        } else if (d.getHitbox().x < player.getHitbox().x)
            return true;
        return false;
    }

    private boolean IsPlayerInRange(DalmatoCannon d, Player player) {
        int absValue = (int) Math.abs (player.getHitbox ().x - d.getHitbox ().x);
        return absValue <= Game.TILES_SIZE * 5;
    }

    public void checkSpikeTouched(Player p){
        for(Spike s : spikes){
            if(s.getHitbox ().intersects (p.getHitbox ())){
                p.kill();
            }
        }
    }

    public void checkObjTouched(Rectangle2D.Float hitbox){
        for(Potion p : potions){
            if(p.isActive ()){
                if(hitbox.intersects (p.getHitbox ())){
                    p.setActive (false);
                    applyEffectToPlayer (p);
                }
            }
        }
    }
    public void applyEffectToPlayer(Potion p){
        if(p.getObjType () == RED_POTION){
            playing.getPlayer ().changeHealth (RED_POTION_VALUE);
        }else{
            playing.getPlayer ().changePower (BLUE_POTION_VALUE);
        }
    }
    public void checkObjHit(Rectangle2D.Float attackbox){
        Random type = new Random ();
        for(GameContainer gc : containers){
            if(gc.isActive () && !gc.doAnimation){
                if(gc.getHitbox ().intersects (attackbox)){
                    gc.setAnimation (true);
                    int a = type.nextInt (2);
                    if(gc.getObjType () == BOX){
                        potions.add (new Potion ((int)(gc.getHitbox ().x + gc.getHitbox ().width / 2), (int)(gc.getHitbox ().y + 2), a));
                    }
                    return;
                }
            }
        }
    }

    public void draw(Graphics g, int xLvlOffset){
        drawPotion(g, xLvlOffset);
        drawContainers(g, xLvlOffset);
        drawTraps(g, xLvlOffset);
        drawDalmatoCannons(g, xLvlOffset);
        drawProjectiles(g, xLvlOffset);
    }

    private void drawProjectiles(Graphics g, int xLvlOffset) {
        for(Projectiles p : projectiles){
            if(p.isActive ()){
                g.drawImage (plasmaBallImg, (int)(p.getHitbox ().x - xLvlOffset),(int)(p.getHitbox ().y), PlASMABALL_SIZE, PlASMABALL_SIZE, null);
            }
        }
    }

    private void drawDalmatoCannons(Graphics g, int xLvlOffset) {
        for (DalmatoCannon c : dalmatoCannons) {
            int x = (int) (c.getHitbox().x - xLvlOffset);
            int width = DALMATOCannon_WIDTH;

            if (c.getObjType() == DALMATOCannon_RIGHT) {
                x += width;
                width *= -1;
            }

            g.drawImage(dalmatoCannonImg[c.getAniIndex()], x, (int) (c.getHitbox().y), width, DALMATOCannon_HEIGHT, null);
        }

    }

    private void drawTraps(Graphics g, int xLvlOffset) {
        for(Spike s : spikes){
            int type = 0;
            g.drawImage (spikeImg[type], (int)(s.getHitbox ().x - xLvlOffset), (int)(s.getHitbox ().y - s.getyDrawOffset ()), SPIKE_WIDTH, SPIKE_HEIGHT, null);
        }
    }

    private void drawPotion(Graphics g, int xLvlOffset) {
        for(Potion p : potions){
            if(p.isActive ()){
                int type = 0;
                if(p.getObjType () == RED_POTION)
                    type = 1;
                g.drawImage (potionImg[type][p.getAniIndex ()],
                        (int)(p.getHitbox ().x - p.getxDrawOffset () - xLvlOffset),
                        (int)(p.getHitbox ().y - p.getyDrawOffset ()),
                        POTION_WIDTH,
                        POTION_HEIGHT,
                        null);
            }
        }
    }

    private void drawContainers(Graphics g, int xLvlOffset) {
        for(GameContainer gc : containers){
            if(gc.isActive ()){
                int type = 0;
                if(gc.getObjType () == BARREL)
                    type = 1;
                g.drawImage (containerImg[type][gc.getAniIndex ()],
                        (int)(gc.getHitbox ().x - gc.getxDrawOffset () - xLvlOffset),
                        (int)(gc.getHitbox ().y - gc.getyDrawOffset ()),
                        CONTAINER_WIDTH,
                        CONTAINER_HEIGHT,
                        null);
            }
        }
    }

    public void resetAllObj() {

        loadObjects (playing.getLevelManager ().getCurrentLevel ());
        for(Potion p : potions){
            p.reset ();
        }
        for(GameContainer gc : containers){
            gc.reset ();
        }
        for(DalmatoCannon d : dalmatoCannons){
            d.reset ();
        }
    }
}
