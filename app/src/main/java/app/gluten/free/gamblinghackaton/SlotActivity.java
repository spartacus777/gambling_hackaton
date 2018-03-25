package app.gluten.free.gamblinghackaton;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by K-Android 001 on 3/25/2018.
 */

public class SlotActivity extends AppCompatActivity {

    private ImageView img1, img2, img3;
    private Wheel wheel1, wheel2, wheel3;
    private Button btn, btnSound;
    private boolean isStarted;
    MediaPlayer mpSound;
    private boolean isSoundOn=true;

    public static final Random RANDOM = new Random();

    public static long randomLong(long lower, long upper) {
        return lower + (long) (RANDOM.nextDouble() * (upper - lower));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slot_activity);
        getSupportActionBar().hide();
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        btn = (Button) findViewById(R.id.btn);
        btnSound = (Button) findViewById(R.id.btnSound);

        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSoundOn){
                    btnSound.setBackgroundResource(R.drawable.sound_off);
                    mpSound.setVolume(0,0);
                    isSoundOn=false;
                }
                else{
                    btnSound.setBackgroundResource(R.drawable.sound_on);
                    mpSound.setVolume(1,1);
                    isSoundOn = true;
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStarted) {
                    wheel1.stopWheel();
                    wheel2.stopWheel();
                    wheel3.stopWheel();

                    if (wheel1.currentIndex == wheel2.currentIndex && wheel2.currentIndex == wheel3.currentIndex) {
//                        msg.setText("You win the big prize");
                    } else if (wheel1.currentIndex == wheel2.currentIndex || wheel2.currentIndex == wheel3.currentIndex
                            || wheel1.currentIndex == wheel3.currentIndex) {
//                        msg.setText("Little Prize");
                    } else {
//                        msg.setText("You lose");
                    }

                    btn.setText("Start");
                    isStarted = false;

                } else {

                    wheel1 = new Wheel(new Wheel.WheelListener() {
                        @Override
                        public void newImage(final int img) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    img1.setImageResource(img);
                                }
                            });
                        }
                    }, 200, randomLong(0, 200));

                    wheel1.start();

                    wheel2 = new Wheel(new Wheel.WheelListener() {
                        @Override
                        public void newImage(final int img) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    img2.setImageResource(img);
                                }
                            });
                        }
                    }, 200, randomLong(150, 400));

                    wheel2.start();

                    wheel3 = new Wheel(new Wheel.WheelListener() {
                        @Override
                        public void newImage(final int img) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    img3.setImageResource(img);
                                }
                            });
                        }
                    }, 200, randomLong(150, 400));

                    wheel3.start();

                    btn.setText("Stop");
//                    msg.setText("");
                    isStarted = true;
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mpSound = MediaPlayer.create(this, R.raw.slot_music);
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