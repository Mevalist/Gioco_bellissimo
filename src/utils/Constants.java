package utils;

import main.Game;

public class Constants {
    public static class EnemyConstants{
        public static final int KEKKO = 0;
        public static final int IDLE = 1;
        public static final int RUNNING = 0;
        public static final int ATTACK = 3;
        public static final int DIE = 2;
        public static final int KEKKO_DEFAULT_SIZE = 32;
        public static final int KEKKO_SIZE = (int)(KEKKO_DEFAULT_SIZE * Game.SCALE);
        public static final int KEKKO_DRAWOFFSET_X = (int)(6*Game.SCALE);
        public static final int KEKKO_DRAWOFFSET_Y = (int)(12* Game.SCALE);
        public static int GetSpriteAmount(int enemy_tipe, int enemy_state){
            switch (enemy_tipe){
                case KEKKO:
                    switch (enemy_state){
                        case ATTACK:
                            return 4;
                        case IDLE:
                        case RUNNING:
                        case DIE:
                            return 3;
                    }
            }
            return 0;
        }

    }

    public static class UI{
        public static class Buttons{
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int)(B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int)(B_HEIGHT_DEFAULT * Game.SCALE);

        }
        public static class PauseButton{
            public static final int SOUND_DF_SIZE = 42;
            public static final int SOUND_SIZE = (int)(SOUND_DF_SIZE * Game.SCALE);

        }

        public static class URMButtons{
            public static final int URM_DF_SIZE = 56;
            public static final int URM_SIZE = (int)(URM_DF_SIZE*Game.SCALE)/2;
        }

        public static class VolumeButtons{
            public static final int VOLUME_DF_WIDTH = 28;
            public static final int VOLUME_DF_HEIGHT = 44;
            public static final int SLIDER_DF_WIDTH = 215;
            public static final int SLIDER_WIDTH = (int)(SLIDER_DF_WIDTH*Game.SCALE);
            public static final int VOLUME_WIDTH = (int)(VOLUME_DF_WIDTH * Game.SCALE);
            public static final int VOLUME_HEIGHT = (int)(VOLUME_DF_HEIGHT * Game.SCALE) / 2;
        }
    }

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }
    public static class PlayerConstants{
        public static final int RUNNING = 0;
        public static final int IDLE = 1;
        public static final int JUMP = 2;
        //public static final int FALLING = 3;
        //public static final int GROUND = 4;
        public static final int ATTACK = 3;

        public static int getSpriteAmount(int player_action){
            switch (player_action){
                case RUNNING:
                case IDLE:
                    return 4;
                case JUMP:
                    return 2;
                case ATTACK:
                    return 3;
                default:
                    return 1;
            }
        }
        public PlayerConstants(){

        }
    }
}
