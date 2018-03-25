package app.gluten.free.gamblinghackaton.shop;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.gluten.free.gamblinghackaton.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int NATIVE_AD = 1;

    private List<Product> products;

    public ShopAdapter(List<Product> products){
        this.products = products;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NATIVE_AD:
                return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);

        ((Holder) holder).bind(products.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return NATIVE_AD;
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivIcon)
        ImageView ivIcon;

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvDescr)
        TextView tvDescr;

        @BindView(R.id.btnBuy)
        Button btnBuy;


        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Product product){
            tvTitle.setText(product.title);
            tvDescr.setText(product.descr);
            if (product.iconRes != 0) {
                ivIcon.setImageResource(product.iconRes);
            }

            btnBuy.setOnClickListener(product.listener);
        }

    }
}
