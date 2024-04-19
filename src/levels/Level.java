package levels;

import entities.BUX;
import entities.KEKKO;
import main.Game;
import objects.DalmatoCannon;
import objects.GameContainer;
import objects.Potion;
import objects.Spike;
import utils.HelpMethod;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.HelpMethod.*;

public class Level {

    private BufferedImage img;
    private int lvlData[][];
    private ArrayList<KEKKO> kekkos;
    private ArrayList<BUX> buxes;
    private ArrayList<Potion> potions;
    private ArrayList<Spike> spikes;
    private ArrayList<GameContainer> containers;
    private ArrayList<DalmatoCannon> dalmatoCannons;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;
    public Level(BufferedImage img){
        this.img = img;
        createLevelData();
        createEnemies();
        createPotions();
        createContainers();
        createDalmatoCannons();
        createSpikes();
        calcLvlOffset();
        calcPlayerSpawn();
    }

    private void createDalmatoCannons() {
        dalmatoCannons = HelpMethod.GetDalmatoCannon (img);
    }

    private void createSpikes() {
        spikes = HelpMethod.GetSpikes(img);
    }

    private void createPotions() {
        potions = HelpMethod.GetPotions (img);
    }

    private void createContainers() {
        containers = HelpMethod.GetGameContainer (img);
    }

    private void calcPlayerSpawn() {
        playerSpawn = GetPlayerSpawn (img);
    }

    private void calcLvlOffset() {
        lvlTilesWide = img.getWidth ();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
    }

    private void createEnemies() {
        kekkos = GetKekkos (img);
        buxes = GetBuxes(img);
    }

    private void createLevelData() {
        lvlData = getLevelData(img);

    }

    public int getSpriteIndex(int x, int y){
        return lvlData[y][x];
    }
    public int[][] getLvlData(){
        return lvlData;
    }

    public int getLvlOffset(){
        return maxLvlOffsetX;
    }
    public ArrayList<KEKKO> getKekkos(){
        return kekkos;
    }

    public ArrayList<BUX> getBuxes() {
        return buxes;
    }

    public Point getPlayerSpawn(){
        return playerSpawn;
    }

    public ArrayList<Potion> getPotions() {
        return potions;
    }

    public ArrayList<GameContainer> getContainers() {
        return containers;
    }

    public ArrayList<Spike> getSpikes() {
        return spikes;
    }

    public ArrayList<DalmatoCannon> getDalmatoCannons() {
        return dalmatoCannons;
    }
}
