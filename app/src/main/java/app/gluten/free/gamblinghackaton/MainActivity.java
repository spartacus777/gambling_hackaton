package app.gluten.free.gamblinghackaton;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.imangazaliev.circlemenu.CircleMenuButton;

import app.gluten.free.gamblinghackaton.spinner.RouletteActivity;
import app.gluten.free.recievers.NotificationReciever;

public class MainActivity extends AppCompatActivity {

    private Button btnSettings;
    private MediaPlayer mpSound;
    private ViewGroup parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        btnSettings = (Button)findViewById(R.id.btnSettings);
        parent = (ViewGroup) findViewById(R.id.parent);

        com.imangazaliev.circlemenu.CircleMenu circleMenu = (com.imangazaliev.circlemenu.CircleMenu) findViewById(R.id.circleMenu);
        circleMenu.setOnItemClickListener(new com.imangazaliev.circlemenu.CircleMenu.OnItemClickListener() {
            @Override
            public void onItemClick(CircleMenuButton menuButton) {
                switch (menuButton.getId()){
                    case R.id.btnRoulette:
                        Intent slot1 = new Intent(MainActivity.this, RouletteActivity.class);
                        MainActivity.this.startActivity(slot1);
                        break;

                    case R.id.btnSlot:
                        Intent slot = new Intent(MainActivity.this, SlotActivity.class);
                        MainActivity.this.startActivity(slot);
                        break;

                    case R.id.btnShop:
                        Intent shop = new Intent(MainActivity.this, ShopActivity.class);
                        MainActivity.this.startActivity(shop);
                        break;
                }
            }
        });

        createNotification();

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(settings);

            }
        });

        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {Color.parseColor("#ED66FF"), Color.parseColor("#717BFF")});
        gd.setCornerRadius(0f);
        parent.setBackground(gd);
    }

    private void createNotification(){
        Intent notifyIntent = new Intent(this, NotificationReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (MainActivity.this, 3, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) MainActivity.this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                1000 * 60 * 60 * 2, pendingIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mpSound = MediaPlayer.create(this, R.raw.menu_music);
        mpSound.setLooping(true);
        final SharedPreferences storage = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        if(storage.getBoolean("isSoundOn", true))
            mpSound.setVolume(1, 1);
        else
            mpSound.setVolume(0,0);
        mpSound.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mpSound.release();
    }
}
