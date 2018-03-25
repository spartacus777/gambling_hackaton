package app.gluten.free.gamblinghackaton.spinner;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import app.gluten.free.gamblinghackaton.App;

public class User {

    final public static String      BALANCE = "BALANCE";
    final public static String      WINS = "WINS";
    final public static String      NOWINS = "NOWINS";


    public int balanceSpins = 10;

    public int rouletteWinCount = 0;

    public int rouletteRollsNoWinsCount = 0;

    public void save() {
        final SharedPreferences.Editor storage = PreferenceManager.getDefaultSharedPreferences(App.getContext()).edit();
        storage.putInt(BALANCE, balanceSpins);
        storage.putInt(WINS, rouletteWinCount);
        storage.putInt(NOWINS, rouletteRollsNoWinsCount);
        storage.apply();
    }

    public void load() {
        final SharedPreferences storage = PreferenceManager.getDefaultSharedPreferences(App.getContext());

        balanceSpins = storage.getInt(BALANCE, 2);
        rouletteWinCount = storage.getInt(BALANCE, 0);
        rouletteRollsNoWinsCount = storage.getInt(BALANCE, 0);

    }

    public void increaseBalance(int spins){
        balanceSpins += spins;
    }

    public int getBalance(){
        return balanceSpins;
    }
}
