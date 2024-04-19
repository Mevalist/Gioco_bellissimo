package main;

import entities.Player;
import gamestates.GameOptions;
import gamestates.GameStates;
import gamestates.Menu;
import gamestates.Playing;
import levels.LevelManager;
import ui.AudioOptions;

import java.awt.*;
import java.nio.file.LinkOption;

public class Game implements Runnable{
    private AudioOptions audioOptions;
    private GameOptions options;
    private GameWindow gamewindow;
    private GamePanel gamepanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private Playing playing;
    private Menu menu;
    public static final int TILES_DEFAULT_SIZE = 32;
    public static final float SCALE = 1.5f;
    public static final int TILES_IN_WIDTH = 26;
    public static final int TILES_IN_HEIGHT = 14;
    public static final int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
    public static final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public static final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    public Game(){
        initClasses();
        gamepanel = new GamePanel (this);
        gamewindow = new GameWindow (gamepanel);
        gamepanel.setFocusable (true);
        gamepanel.requestFocus ();

        startGameLoop ();
    }

    private void initClasses() {
        audioOptions = new AudioOptions ();
        menu = new Menu (this);
        playing = new Playing (this);
        options = new GameOptions (this);
    }

    private void startGameLoop(){
        gameThread = new Thread (this);
        gameThread.start ();
    }
    @Override
    public void run() {
        //time per frame
        double tpf = 1000000000.0 / FPS_SET;
        double tpu = 1000000000.0 / UPS_SET;
        long lastchek = System.currentTimeMillis ();

        long previousTime = System.nanoTime ();
        int frames = 0;
        int update = 0;
    
        double deltaU = 0;
        double deltaF = 0;


        while (true){
            long currentTime = System.nanoTime ();

            deltaU += (currentTime - previousTime) / tpu;
            deltaF += (currentTime - previousTime) / tpf;
            previousTime = currentTime;
            if (deltaU >= 1){
                updateP();
                update++;
                deltaU--;
            }

            if (deltaF >= 1){
                gamepanel.repaint ();
                frames++;
                deltaF--;
            }

            if(System.currentTimeMillis () - lastchek >= 1000){
                lastchek = System.currentTimeMillis ();
                System.out.println ("FPS = "+ frames +" /  UPS = "+update);
                frames = 0;
                update = 0;
            }
        }
    }

    public void updateP() {

        switch (GameStates.state){
            case MENU:
                menu.update ();
                break;
            case PLAYING:
                playing.update ();
                break;
            case OPTIONS:
                options.update ();
                break;
            case QUIT:
            default:
                System.exit (0);
                break;
        }
    }

    public void render(Graphics g){
        switch (GameStates.state){
            case MENU:
                menu.draw (g);
                break;
            case PLAYING:
                playing.draw (g);
                break;
            case OPTIONS:
                options.draw (g);
                break;
            default:
                break;
        }
    }

    public void windowFocusLost(){
        if(GameStates.state == GameStates.PLAYING){
            playing.getPlayer ().resetBool ();
        }
    }

    public Menu getMenu(){
        return menu;
    }
    public Playing getPlaying(){
        return playing;
    }

    public GameOptions getOptions() {
        return options;
    }

    public AudioOptions getAudioOptions() {
        return audioOptions;
    }
}
