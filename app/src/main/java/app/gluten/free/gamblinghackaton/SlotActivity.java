package app.gluten.free.gamblinghackaton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.util.Random;

import app.gluten.free.gamblinghackaton.spinner.RouletteActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by K-Android 001 on 3/25/2018.
 */

public class SlotActivity extends AppCompatActivity {

    private ImageView img1, img2, img3, btnBack;
    private Wheel wheel1, wheel2, wheel3;
    private Button btn, btnSound, btnShop;
    private boolean isStarted;
    MediaPlayer mpSound;
    private boolean isSoundOn=true;
    protected TextSwitcher textSwitcher;

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
        btnShop = (Button) findViewById(R.id.btnShop);
        btnBack = (ImageView) findViewById(R.id.back);
        textSwitcher = (TextSwitcher)findViewById(R.id.text_switcher);

        final SharedPreferences storage = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        if(storage.getBoolean("isSoundOn", true))
            btnSound.setBackgroundResource(R.drawable.sound_on);
        else
            btnSound.setBackgroundResource(R.drawable.sound_off);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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

        btnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shop = new Intent(SlotActivity.this, ShopActivity.class);
                SlotActivity.this.startActivity(shop);
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
                        App.getCurrentUser().increaseBalance(10);
                        updateBalanceAndSpins(false);
                    } else if (wheel1.currentIndex == wheel2.currentIndex || wheel2.currentIndex == wheel3.currentIndex
                            || wheel1.currentIndex == wheel3.currentIndex) {
//                        msg.setText("Little Prize");
                        App.getCurrentUser().increaseBalance(2);
                        updateBalanceAndSpins(false);
                    } else {
//                        msg.setText("You lose");
                    }

                    btn.setText("Start");
                    isStarted = false;
                    btnShop.setEnabled(true);

                } else {


                    if (App.getCurrentUser().getBalance() > 0) {
                        App.getCurrentUser().increaseBalance(-1);
                        updateBalanceAndSpins(false);

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
                        btnShop.setEnabled(false);
                    }
                    else{
                        NoSpinDialogHelper.NoSpinClickListener listener = new NoSpinDialogHelper.NoSpinClickListener() {
                            @Override
                            public void onBuyClicked() {
                                Intent shop = new Intent(SlotActivity.this, ShopActivity.class);
                                SlotActivity.this.startActivity(shop);
                            }
                        };
                        NoSpinDialogHelper.show(SlotActivity.this, listener);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                            }
                        }, 2000);
                    }
                }
            }
        });

        initTextSwitcher();
        updateBalanceAndSpins(true);
    }

    private void initTextSwitcher(){
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView () {
                TextView tv = new TextView(SlotActivity.this);
                tv.setTextColor(Color.WHITE);
                tv.setMaxLines(1);
                tv.setTextSize(20);
                return tv;
            }
        });
        textSwitcher.setInAnimation(this, R.anim.text_in);
        textSwitcher.setOutAnimation(this, R.anim.text_out);

    }

    private void updateBalanceAndSpins(boolean fromInit){
        int balance = App.getCurrentUser().getBalance();

        if (((TextView) textSwitcher.getCurrentView()).getText() != null &&
                !String.format("%d", balance).equalsIgnoreCase(((TextView) textSwitcher.getCurrentView()).getText().toString())) {

            textSwitcher.setText("" + String.format("%d", balance) + " Spin");

        } else {
            textSwitcher.setText("" + String.format("%d", balance) + " Spin");
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mpSound = MediaPlayer.create(this, R.raw.slot_music);
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