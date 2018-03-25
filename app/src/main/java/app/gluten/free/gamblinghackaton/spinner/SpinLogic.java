package app.gluten.free.gamblinghackaton.spinner;

import android.util.Log;

import java.util.Random;

import app.gluten.free.gamblinghackaton.App;

public class SpinLogic {

    private static final int SECTOR_COUNTER = 8;
    private static final int ANGLE_DEF = 45;

    private static final float[] REWARD = {1f, 0f, 10f, 0f, 3f, 5f, 0f, 2f};

    private static class Index{
        private int winIndex;
        private boolean isWin;
        private float multiplyer;

        private Index(int winIndex, boolean isWin){
            this.winIndex = winIndex;
            this.isWin = isWin;

            multiplyer = REWARD[winIndex];
        }
    }

    private static final Index WIN_1 = new Index(0, false);
    private static final Index WIN_03 = new Index(1, false);
    private static final Index WIN_10 = new Index(2, true);
    private static final Index WIN_0 = new Index(3, false);
    private static final Index WIN_3 = new Index(4, true);
    private static final Index WIN_5 = new Index(5, true);
    private static final Index WIN_05 = new Index(6, false);
    private static final Index WIN_2 = new Index(7, true);

    private int prevRotation = 0;

    private Random random = new Random();

    public SpinLogic(){}

    public class SpinLogicModel{
        public int rotation;
        public int duration;
        public float multiplier;
        public boolean isWin;
    }

    public SpinLogicModel generate(float percent) {
        int extraRounds;
        int duration;
        final int deltaDuration = 500;

        if (percent <= 0.25){
            extraRounds = 0;
            duration = 250 + random.nextInt(deltaDuration);
        } else if (percent <= 0.5){
            extraRounds = 1;
            duration = 550 + random.nextInt(deltaDuration);
        } else if (percent <= 0.75){
            extraRounds = 2;
            duration = 850 + random.nextInt(deltaDuration);
        }  else {
            extraRounds = 3;
            duration = 1150 + random.nextInt(deltaDuration);
        }

        Win winModel = generateAngle(percent);
        int generatedAngle = winModel.angle;
        int deltaRotation = 360 * extraRounds + generatedAngle;
        prevRotation += deltaRotation;

        SpinLogicModel model = new SpinLogicModel();
        model.duration = duration;
        model.rotation = prevRotation;
        model.isWin = winModel.index.isWin;
        model.multiplier = winModel.index.multiplyer;

        return model;
    }

    private class Win{
        int angle;
        Index index;
    }

    public int getWinCount(){
        return App.getCurrentUser().rouletteWinCount;
    }

    public double getBalance(){
        return App.getCurrentUser().rouletteWinCount;
    }

    public int getRollsNoWinCount(){
        return App.getCurrentUser().rouletteRollsNoWinsCount;
    }

    public void log(int gen){
        Log.d("WINNER", "gen: " + gen);
    }

    private Win generateAngle(float percent){
        int winCount = getWinCount();
        int rouletteRollsNoWinsCount = getRollsNoWinCount();

        int bonusProbability = 0;

        if (percent > 0.2f && percent < 0.3f){
            bonusProbability = 1;
        }

        if (percent > 0.46f && percent < 0.54f){
            bonusProbability = 2;
        }

        if (percent > 0.75f && percent < 0.79f){
            bonusProbability = 3;
        }

        Win model = new Win();
        Index index;

        int gen = random.nextInt(100);
        log(gen);

            if (winCount < 2 || rouletteRollsNoWinsCount > 15) {
                if (gen < 10 + bonusProbability) {
                    index = WIN_10;
                } else if (gen < 20 + bonusProbability) {
                    index = WIN_5;
                } else if (gen < 30 + bonusProbability) {
                    index = WIN_3;
                } else if (gen < 70 + bonusProbability) {
                    index = WIN_2;
                } else if (gen < 75 + bonusProbability) {
                    index = WIN_1;
                } else if (gen < 80 + bonusProbability) {
                    index = WIN_05;
                } else if (gen < 90 + bonusProbability) {
                    index = WIN_03;
                } else {//>=90
                    index = WIN_0;
                }
            } else if (winCount < 5 || rouletteRollsNoWinsCount > 8) {
                if (gen < 5 + bonusProbability) {
                    index = WIN_10;
                } else if (gen < 10 + bonusProbability) {
                    index = WIN_5;
                } else if (gen < 20 + bonusProbability) {
                    index = WIN_3;
                } else if (gen < 70 + bonusProbability) {
                    index = WIN_2;
                } else if (gen < 75 + bonusProbability) {
                    index = WIN_1;
                } else if (gen < 80 + bonusProbability) {
                    index = WIN_05;
                } else if (gen < 90 + bonusProbability) {
                    index = WIN_03;
                } else {//>=90
                    index = WIN_0;
                }
            } else if (winCount < 20 || rouletteRollsNoWinsCount > 4) {
                if (gen < 5) {
                    index = WIN_5;
                } else if (gen < 9 + bonusProbability) {
                    index = WIN_3;
                } else if (gen < 20 + bonusProbability) {
                    index = WIN_2;
                } else if (gen < 40 + bonusProbability) {
                    index = WIN_1;
                } else if (gen < 50 + bonusProbability) {
                    index = WIN_05;
                } else if (gen < 90 + bonusProbability) {
                    index = WIN_03;
                } else {//>=90
                    index = WIN_0;
                }
            } else {
                if (gen < 4) {
                    index = WIN_5;
                } else if (gen < 7 + bonusProbability) {
                    index = WIN_3;
                } else if (gen < 13 + bonusProbability) {
                    index = WIN_2;
                } else if (gen < 30 + bonusProbability) {
                    index = WIN_1;
                } else if (gen < 40 + bonusProbability) {
                    index = WIN_05;
                } else if (gen < 80 + bonusProbability) {
                    index = WIN_03;
                } else {//>=80
                    index = WIN_0;
                }
        }

        model.angle = ((SECTOR_COUNTER - prevRotation / ANGLE_DEF % SECTOR_COUNTER ) + index.winIndex )* ANGLE_DEF; // 0.5x
        model.index = index;

        return model;
    }
}
