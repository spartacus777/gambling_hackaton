package app.gluten.free.gamblinghackaton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by K-Android 001 on 3/25/2018.
 */

public class ShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity);
        getSupportActionBar().hide();
    }


}
