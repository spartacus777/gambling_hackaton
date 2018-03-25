package app.gluten.free.gamblinghackaton;


import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.animation.OvershootInterpolator;

public abstract class BaseDialogHelper {

    private Context context;

    private AlertDialog alertDialog;

    protected BaseDialogHelper(Context context){
        this.context = context;

        View view = onCreateView();
        initDialog(view);
    }

    protected abstract View onCreateView();

    protected void initDialog(View view){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setView(view);

        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    public void dismiss(){
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    protected void initAnimation(View view, int delay){
        view.setAlpha(0f);
        view.setScaleX(0.5f);
        view.setScaleY(0.5f);

        view.animate().
                setStartDelay(delay).
                setDuration(200).
                setInterpolator(new OvershootInterpolator()).
                alpha(1.0f).
                scaleX(1.0f).
                scaleY(1.0f);
    }

    protected Context getContext(){
        return context;
    }

}
