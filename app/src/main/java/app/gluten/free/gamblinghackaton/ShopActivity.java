package app.gluten.free.gamblinghackaton;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import app.gluten.free.gamblinghackaton.shop.Product;
import app.gluten.free.gamblinghackaton.shop.ShopAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by K-Android 001 on 3/25/2018.
 */

public class ShopActivity extends AppCompatActivity {

    @BindView(R.id.rvItems)
    public RecyclerView rvItems;

    private ShopAdapter adapter;

    @BindView(R.id.text_switcher)
    protected TextSwitcher textSwitcher;

    MediaPlayer mpSound;
    @BindView(R.id.dol_switcher)
    protected TextSwitcher dol_switcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity);
        getSupportActionBar().hide();

        ButterKnife.bind(this);

        initTextSwitcher();
        init();
        updateBalance();
    }

    private void initTextSwitcher(){
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView () {
                TextView tv = new TextView(ShopActivity.this);
                tv.setTextColor(Color.WHITE);
                tv.setMaxLines(1);
                tv.setTextSize(20);
                return tv;
            }
        });
        textSwitcher.setInAnimation(this, R.anim.text_in);
        textSwitcher.setOutAnimation(this, R.anim.text_out);

        dol_switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView () {
                TextView tv = new TextView(ShopActivity.this);
                tv.setTextColor(Color.WHITE);
                tv.setMaxLines(1);
                tv.setTextSize(20);
                return tv;
            }
        });
        dol_switcher.setInAnimation(this, R.anim.text_in);
        dol_switcher.setOutAnimation(this, R.anim.text_out);

    }

    private void updateBalance(){
        textSwitcher.setText(""+App.getCurrentUser().balanceSpins);
        dol_switcher.setText(""+App.getCurrentUser().dollars);
    }

    private void init(){
        LinearLayoutManager quickLinkLayoutManager = new LinearLayoutManager(rvItems.getContext(), LinearLayoutManager.VERTICAL, false);
        rvItems.setLayoutManager(quickLinkLayoutManager);
        rvItems.setHasFixedSize(true);

        adapter = new ShopAdapter(create());
        rvItems.setAdapter(adapter);
    }

    private List<Product> create(){
        List<Product> products = new ArrayList<>();
        products.add(createProd1());
        products.add(createProd2());
        products.add(createProd3());
        products.add(createProd4());
        products.add(createProd5());
        products.add(createProd6());

        return products;
    }

    private Product createProd1(){
        Product p1 = new Product();
        p1.title = "Buy 1 spin";
        p1.descr = "buy one spin for 1 dollar";
        p1.listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getCurrentUser().increaseBalance(1);
                App.getCurrentUser().dollars += 1;
                App.setCurrentUser(App.getCurrentUser());
                updateBalance();
            }
        };

        return p1;
    }

    @OnClick(R.id.back)
    public void onclickback(){
        onBackPressed();
    }

    private Product createProd2(){
        Product p1 = new Product();
        p1.title = "Buy 5 spin";
        p1.descr = "buy one spin for 4 dollar";
        p1.listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getCurrentUser().increaseBalance(5);
                App.getCurrentUser().dollars += 4;
                App.setCurrentUser(App.getCurrentUser());
                updateBalance();
            }
        };

        return p1;
    }

    private Product createProd3(){
        Product p1 = new Product();
        p1.title = "Buy 13 spin";
        p1.descr = "buy one spin for 10 dollar";
        p1.listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getCurrentUser().increaseBalance(13);
                App.getCurrentUser().dollars += 10;
                App.setCurrentUser(App.getCurrentUser());
                updateBalance();
            }
        };

        return p1;
    }

    private Product createProd4(){
        Product p1 = new Product();
        p1.title = "Random 1-5 spins";
        p1.descr = "buy gift box for 2 dollar";
        p1.iconRes  = R.drawable.gift1;
        p1.listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getCurrentUser().increaseBalance(new Random().nextInt(5) + 1);
                App.getCurrentUser().dollars += 2;
                App.setCurrentUser(App.getCurrentUser());
                updateBalance();
            }
        };

        return p1;
    }

    private Product createProd5(){
        Product p1 = new Product();
        p1.title = "Random 5 - 10 spins";
        p1.descr = "buy gift box for 5 dollar";
        p1.iconRes  = R.drawable.gift2;
        p1.listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getCurrentUser().increaseBalance(new Random().nextInt(5) + 5);
                App.getCurrentUser().dollars += 5;
                App.setCurrentUser(App.getCurrentUser());
                updateBalance();
            }
        };

        return p1;
    }

    private Product createProd6(){
        Product p1 = new Product();
        p1.title = "Random 10 - 15 spins";
        p1.descr = "buy gift box for 7 dollar";
        p1.iconRes  = R.drawable.gift3;
        p1.listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getCurrentUser().increaseBalance(new Random().nextInt(5) + 10);
                App.getCurrentUser().dollars += 7;
                App.setCurrentUser(App.getCurrentUser());
                updateBalance();
            }
        };

        return p1;
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
