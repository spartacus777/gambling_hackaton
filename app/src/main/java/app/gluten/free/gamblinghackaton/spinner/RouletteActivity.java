package app.gluten.free.gamblinghackaton.spinner;

import android.animation.Animator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import app.gluten.free.gamblinghackaton.App;
import app.gluten.free.gamblinghackaton.BaseActivity;
import app.gluten.free.gamblinghackaton.NoSpinDialogHelper;
import app.gluten.free.gamblinghackaton.R;
import app.gluten.free.gamblinghackaton.ShopActivity;
import app.gluten.free.gamblinghackaton.helper.UIHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RouletteActivity extends BaseActivity implements IndicatorView.OnIndicatorInterection,
        NoSpinDialogHelper.NoSpinClickListener {

    @BindView(R.id.btnBuy)
    protected Button btnBuy;

    @BindView(R.id.rouletteView)
    protected ImageView rouletteView;

    @BindView(R.id.rlContent)
    protected RelativeLayout rlContent;

    @BindView(R.id.arrow)
    protected ImageView arrow;

    @BindView(R.id.btnSpin)
    protected Button btnSpin;

    @BindView(R.id.indicatorView)
    public IndicatorView indicatorView;

    @BindView(R.id.text_switcher)
    protected TextSwitcher textSwitcher;

    @BindView(R.id.parent)
    protected LinearLayout parent;

    private SpinLogic spinLogic;

    private double reward = 1;
    MediaPlayer mpSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rulette_activity);
        getSupportActionBar().hide();

        ButterKnife.bind(this);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateBalanceAndSpins(true);
    }

    @OnClick(R.id.btnBuy)
    public void onkjsdfbjknfrfj(){
        Intent shop = new Intent(RouletteActivity.this, ShopActivity.class);
        RouletteActivity.this.startActivity(shop);
    }


    @OnClick(R.id.back)
    public void onclickback(){
        onBackPressed();
    }

    private void setReward(){
    }

    private void init(){
        setReward();

        spinLogic = new SpinLogic();

        indicatorView.setOnInterectionListener(this);

        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {Color.parseColor("#ED66FF"), Color.parseColor("#717BFF")});
        gd.setCornerRadius(0f);
        parent.setBackground(gd);

        rlContent.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                int size = UIHelper.getW() - UIHelper.getPixel(30) * 2;
                int sizeH = rlContent.getHeight() - rlContent.getPaddingBottom() - rlContent.getPaddingTop();
                size = Math.min(size, sizeH);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
                params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                rouletteView.setLayoutParams(params);
                rouletteView.setVisibility(View.VISIBLE);

                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) arrow.getLayoutParams();
                params1.topMargin = Math.max(0, (rlContent.getHeight() - rlContent.getPaddingBottom() - rlContent.getPaddingTop() - size)/2 - UIHelper.getPixel(5));
                arrow.setLayoutParams(params1);
                arrow.setVisibility(View.VISIBLE);

                rlContent.removeOnLayoutChangeListener(this);
            }
        });

        btnSpin.setOnTouchListener(new View.OnTouchListener() {

            private boolean isClickInProgress = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.v("UIIOOO", "Event: " + getActionStr(event));

                int action = event.getAction();

                if (event.getX() > 0 && event.getX() < btnSpin.getWidth() &&
                        event.getY() > 0 && event.getY() < btnSpin.getHeight()){
                    //ok
                } else {
                    btnSpin.setPressed(false);
                    indicatorView.endTouch(false);
                    return false;
                }

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        isClickInProgress = true;
                        indicatorView.startTouch();
                        btnSpin.setPressed(true);

                        break;
                    case MotionEvent.ACTION_MOVE:

                        if (!isClickInProgress) {
                            return false;
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        if (!isClickInProgress) {
                            return false;
                        }
                        btnSpin.setPressed(false);

                        isClickInProgress = false;
                        indicatorView.endTouch(true);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        if (!isClickInProgress) {
                            return false;
                        }

                        btnSpin.setPressed(false);

                        isClickInProgress = false;
                        indicatorView.endTouch(false);
                        break;
                }

                return true;
            }
        });

        initTextSwitcher();
        updateBalanceAndSpins(true);
    }

    private void initTextSwitcher(){
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView () {
                TextView tv = new TextView(RouletteActivity.this);
                tv.setTextColor(Color.WHITE);
                tv.setMaxLines(1);
                tv.setTextSize(20);
                return tv;
            }
        });
        textSwitcher.setInAnimation(this, R.anim.text_in);
        textSwitcher.setOutAnimation(this, R.anim.text_out);

    }

    private String getActionStr(MotionEvent e){
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return "ACTION_DOWN";
            case MotionEvent.ACTION_MOVE:
                return "ACTION_MOVE";
            case MotionEvent.ACTION_UP:
                return "ACTION_UP";
            case MotionEvent.ACTION_CANCEL:
                return "ACTION_CANCEL";
        }

        return "ANOTHER action!!!!!!!!!!!!!!!!";
    }

    @Override
    public void onIndicatorClicked(float percent) {
        if (App.getCurrentUser().getBalance() <= 0){
            showNoSpinsDialog();
            return;
        }

        final SpinLogic.SpinLogicModel spinLogicModel = spinLogic.generate(percent);

        btnSpin.setEnabled(false);
        rouletteView.clearAnimation();
        rouletteView.animate().rotation(spinLogicModel.rotation)
                .setDuration(spinLogicModel.duration)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        btnSpin.setEnabled(true);

                        User user = App.getCurrentUser();
                        user.increaseBalance((int)(spinLogicModel.multiplier * reward) - 1);

                        if (spinLogicModel.isWin){
                            ++user.rouletteWinCount;
                            user.rouletteRollsNoWinsCount = 0;
                        } else {
                            ++user.rouletteRollsNoWinsCount;
                        }
                        App.setCurrentUser(user);

                        updateBalanceAndSpins(false);

                        setReward();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        btnSpin.setEnabled(true);
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
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

    private void showNoSpinsDialog(){
        NoSpinDialogHelper.show(this, this);
    }

    @Override
    public void onBuyClicked() {
        Intent shop = new Intent(RouletteActivity.this, ShopActivity.class);
        RouletteActivity.this.startActivity(shop);
    }




    @Override
    protected void onStart() {
        super.onStart();
        mpSound = MediaPlayer.create(this, R.raw.roulette_music);
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

