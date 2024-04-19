package utils;

import main.Game;

public class Constants {
    public static final float GRAVITY_PLAYER = 0.035f * Game.SCALE;
    public static final int ANI_SPEED_PLAYER = 30;
    public static final float GRAVITY = 0.04f * Game.SCALE;
    public static final int ANI_SPEED = 25;

    public static class Projectiles {
        public static final int PlASMABALL_DF_SIZE = 15;
        public static final int PlASMABALL_SIZE = (int)(Game.SCALE * PlASMABALL_DF_SIZE);
        public static final float SPEED_PLASMABALL = 2.f * Game.SCALE;
    }
    public static class ObjectConstants {

        public static final int RED_POTION = 0;
        public static final int BLUE_POTION = 1;
        public static final int BARREL = 2;
        public static final int BOX = 3;
        public static final int SPIKE = 4;
        public static final int DALMATOCannon_LEFT = 5;
        public static final int DALMATOCannon_RIGHT = 6;

        public static final int RED_POTION_VALUE = 15;
        public static final int BLUE_POTION_VALUE = 10;

        public static final int CONTAINER_WIDTH_DEFAULT = 40;
        public static final int CONTAINER_HEIGHT_DEFAULT = 30;
        public static final int CONTAINER_WIDTH = (int) (Game.SCALE * CONTAINER_WIDTH_DEFAULT);
        public static final int CONTAINER_HEIGHT = (int) (Game.SCALE * CONTAINER_HEIGHT_DEFAULT);

        public static final int POTION_WIDTH_DEFAULT = 12;
        public static final int POTION_HEIGHT_DEFAULT = 16;
        public static final int POTION_WIDTH = (int) (Game.SCALE * POTION_WIDTH_DEFAULT);
        public static final int POTION_HEIGHT = (int) (Game.SCALE * POTION_HEIGHT_DEFAULT);

        public static final int SPIKE_WIDTH_DEFAULT = 32;
        public static final int SPIKE_HEIGHT_DEFAULT = 32;
        public static final int SPIKE_WIDTH = (int) (Game.SCALE * SPIKE_WIDTH_DEFAULT);
        public static final int SPIKE_HEIGHT = (int) (Game.SCALE * SPIKE_HEIGHT_DEFAULT);
        public static final int DALMATOCannon_DF_WIDTH = 40;
        public static final int DALMATOCannon_DF_HEIGHT = 26;
        public static final int DALMATOCannon_WIDTH = (int)(DALMATOCannon_DF_WIDTH * Game.SCALE);
        public static final int DALMATOCannon_HEIGHT = (int)(DALMATOCannon_DF_HEIGHT * Game.SCALE);
        public static int GetSpriteAmount(int object_type) {
            switch (object_type) {
                case RED_POTION, BLUE_POTION, DALMATOCannon_LEFT, DALMATOCannon_RIGHT:
                    return 7;
                case BARREL, BOX:
                    return 8;
            }
            return 1;
        }
    }


    public static class EnemyConstants{
        public static final int RUNNING = 0;
        public static final int IDLE = 1;
        public static final int HURT = 2;
        public static final int ATTACK = 3;
        public static final int DIE = 4;
        /// KEKKO STATISTICS ///
        public static final int KEKKO = 0;
        public static final int KEKKO_DEFAULT_SIZE = 32;
        public static final int KEKKO_SIZE = (int)(KEKKO_DEFAULT_SIZE * Game.SCALE);
        public static final int KEKKO_DRAWOFFSET_X = (int)(6*Game.SCALE);
        public static final int KEKKO_DRAWOFFSET_Y = (int)(12* Game.SCALE);
        /// END KEKKO STATISTICS ///

        /// START BUX STATISTICS ///
        public static final int BUX = 1;
        public static final int BUX_DEFAULT_SIZE = 64;
        public static final int BUX_SIZE = (int)(BUX_DEFAULT_SIZE * Game.SCALE);
        public static final int BUX_DRAWOFFSET_X = (int)(1*Game.SCALE);
        public static final int BUX_DRAWOFFSET_Y = (int)(2* Game.SCALE);
        /// END BUX STATISTICS ///

        public static int GetSpriteAmount(int enemy_tipe, int enemy_state){
            switch (enemy_tipe){
                case KEKKO:
                    switch (enemy_state){
                        case ATTACK:
                        case DIE:
                            return 4;
                        case IDLE:
                        case RUNNING:
                        case HURT:
                            return 3;
                    }
                case BUX:
                    switch (enemy_state){
                        case IDLE:
                            return 4;
                        case RUNNING:
                            return 5;
                        case HURT:
                            return 0;
                        case DIE:
                            return 6;
                        case ATTACK:
                            return 8;
                    }
            }
            return 0;
        }
        public static int GetMaxHealth(int enemy_tipe){
            switch (enemy_tipe){
                case KEKKO:
                    return 15;
                case BUX:
                    return 900;// over9000;
                default:
                    return 1;
            }
        }
        public static int GetEnemyDMG(int enemy_tipe){
            switch (enemy_tipe){
                case KEKKO:
                    return 10;
                case BUX:
                    return 90;
                default:
                    return 0;
            }
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
        public static final int DIE = 4;

        public static int getSpriteAmount(int player_action){
            switch (player_action){
                case DIE:
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
