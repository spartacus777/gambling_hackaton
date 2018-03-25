package app.gluten.free.gamblinghackaton.spinner;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import app.gluten.free.gamblinghackaton.App;
import app.gluten.free.gamblinghackaton.R;
import app.gluten.free.gamblinghackaton.helper.UIHelper;

public class IndicatorView  extends android.support.v7.widget.AppCompatImageView {

    private static final int DURATION = 1000;

    private static final int DP = UIHelper.getPixel(1);
    private static final int MNOZ = 6;

    private Paint backPaint, strokePaint, fillPaint;

    private int size;
    private float r;

    private RectF rectF;
    private RectF rectOuter, rectInner;

    private int angle = 0;
    private int lastAngle = 0;

    private ValueAnimator anim;

    private OnIndicatorInterection listener;

    public interface OnIndicatorInterection{
        void onIndicatorClicked(float percent);
    }

    public IndicatorView(Context context) {
        super(context);

        init();
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init(){
        setupPaint();

        size = (int) getResources().getDimension(R.dimen.spin_border_d);
        r = size / 2;

        rectF = new RectF(DP, DP, r + r - DP, r + r - DP);
        rectOuter = new RectF(0, 0, r + r, r + r);
        rectInner = new RectF(MNOZ*DP, MNOZ*DP, r + r - MNOZ*DP, r + r - MNOZ*DP);
    }

    private void setupPaint() {
        backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backPaint.setColor(App.getContext().getResources().getColor(R.color.colorFillRoulette));
        backPaint.setStyle(Paint.Style.FILL);
        backPaint.setDither(true);                    // set the dither to true
        backPaint.setStrokeJoin(Paint.Join.ROUND);    // set the join to round you want
        backPaint.setStrokeCap(Paint.Cap.ROUND);      // set the paint cap to round too
        backPaint.setPathEffect(new PathEffect());   // set the path effect when they join.

        strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokePaint.setColor(App.getContext().getResources().getColor(R.color.white));
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(DP);
        strokePaint.setDither(true);                    // set the dither to true
        strokePaint.setStrokeJoin(Paint.Join.ROUND);    // set the join to round you want
        strokePaint.setStrokeCap(Paint.Cap.ROUND);      // set the paint cap to round too
        strokePaint.setPathEffect(new PathEffect());   // set the path effect when they join.

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(App.getContext().getResources().getColor(R.color.white));
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setDither(true);                    // set the dither to true
        fillPaint.setStrokeJoin(Paint.Join.ROUND);    // set the join to round you want
        fillPaint.setStrokeCap(Paint.Cap.ROUND);      // set the paint cap to round too
        fillPaint.setPathEffect(new PathEffect());   // set the path effect when they join.
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
        invalidate();
    }

    public void startTouch(){
        anim = ValueAnimator.ofInt( // Instance of your class
                0, // The initial value of the property
                360); // The final value of the property
        anim.setDuration(DURATION);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                IndicatorView.this.setAngle((int) animation.getAnimatedValue());
                lastAngle = (int) animation.getAnimatedValue();
            }
        });
        anim.setInterpolator(new LinearInterpolator());
        anim.start();
    }

    public void endTouch(boolean isSuccess){
        if (anim != null) {
            anim.cancel();
        }

        if (isSuccess && listener != null){
            float percent = (float) lastAngle / 360f;
            listener.onIndicatorClicked(percent);
        }

        lastAngle = 0;
        setAngle(0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        rectF.set(DP, DP, r + r - DP, r + r - DP);

        canvas.drawOval(rectOuter, fillPaint);
        canvas.drawArc(rectF, -90, angle, true, backPaint);
        canvas.drawOval(rectInner, strokePaint);
    }

    public void setOnInterectionListener(OnIndicatorInterection listener) {
        this.listener = listener;
    }

}

