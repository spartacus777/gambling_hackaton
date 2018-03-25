package app.gluten.free.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import app.gluten.free.gamblinghackaton.App;
import app.gluten.free.services.NotificationIntentService;

/**
 * Created by K-Android 001 on 3/25/2018.
 */


public class NotificationReciever extends BroadcastReceiver {
    public NotificationReciever() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
            popNotification(context);
    }
    private void popNotification(Context context){

        final SharedPreferences storage = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        if(storage.getBoolean("isNotificationsOn", true)) {
            Intent intent1 = new Intent(context, NotificationIntentService.class);
            context.startService(intent1);
        }
    }
}
