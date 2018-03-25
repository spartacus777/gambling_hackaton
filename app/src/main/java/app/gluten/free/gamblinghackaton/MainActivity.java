package app.gluten.free.gamblinghackaton;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import app.gluten.free.recievers.NotificationReciever;

public class MainActivity extends AppCompatActivity {

    private Button btnRoulette, btnSlot, btnShop, btnSettings;
    MediaPlayer mpSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        btnRoulette = (Button)findViewById(R.id.btnRoulette);
        btnSlot = (Button)findViewById(R.id.btnSlot);
        btnShop = (Button)findViewById(R.id.btnShop);
        btnSettings = (Button)findViewById(R.id.btnSettings);

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent slot = new Intent(MainActivity.this, SlotActivity.class);
                MainActivity.this.startActivity(slot);
            }
        });
        createNotification();
    }

    private void createNotification(){
        Intent notifyIntent = new Intent(this, NotificationReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (MainActivity.this, 3, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) MainActivity.this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                1000 * 60 * 60 * 1, pendingIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mpSound = MediaPlayer.create(this, R.raw.menu_music);
        mpSound.setLooping(true);
        mpSound.setVolume(1, 1);
        mpSound.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mpSound.release();
    }
}
