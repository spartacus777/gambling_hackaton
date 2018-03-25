package app.gluten.free.gamblinghackaton;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

/**
 * Created by K-Android 001 on 3/25/2018.
 */

public class SettingsActivity extends AppCompatActivity {

    MediaPlayer mpSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportActionBar().hide();
        Switch soundSwitch = (Switch)findViewById(R.id.soundSwitch);
        Switch notficationSwitch = (Switch)findViewById(R.id.notficationSwitch);

        final SharedPreferences storage = PreferenceManager.getDefaultSharedPreferences(App.getContext());

        soundSwitch.setChecked(storage.getBoolean("isSoundOn", true));
        notficationSwitch.setChecked(storage.getBoolean("isNotificationsOn", true));

        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    final SharedPreferences.Editor storage = PreferenceManager.getDefaultSharedPreferences(App.getContext()).edit();
                    storage.putBoolean("isSoundOn", b);
                    storage.apply();

                    if(b)
                        mpSound.setVolume(1,1);
                    else
                        mpSound.setVolume(0,0);
            }
        });

        notficationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                final SharedPreferences.Editor storage = PreferenceManager.getDefaultSharedPreferences(App.getContext()).edit();
                storage.putBoolean("isNotificationsOn", b);
                storage.apply();
            }
        });
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
