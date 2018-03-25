package app.gluten.free.gamblinghackaton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoSpinDialogHelper extends BaseDialogHelper {

    @BindView(R.id.tvTitle)
    public TextView tvTitle;

    @BindView(R.id.tvDescr)
    public TextView tvDescr;

    @BindView(R.id.btnBuy)
    public Button btnBuy;

    private NoSpinClickListener listener;

    public interface NoSpinClickListener {
        void onBuyClicked();
    }

    public static void show(Context context, NoSpinClickListener listener) {
        new NoSpinDialogHelper(context, listener);
    }

    private NoSpinDialogHelper(Context context, NoSpinClickListener listener) {
        super(context);
        this.listener = listener;

        init();
    }

    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.no_spin_dialog, null);
        ButterKnife.bind(this, view);

        return view;
    }

    private void init() {
        initAnimation(btnBuy, 100);
    }

    @OnClick(R.id.btnBuy)
    public void onBtnBuy() {
        if (listener != null) {
            listener.onBuyClicked();
        }

        dismiss();
    }


}
