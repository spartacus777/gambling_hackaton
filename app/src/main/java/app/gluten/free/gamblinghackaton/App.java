package app.gluten.free.gamblinghackaton;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import app.gluten.free.gamblinghackaton.helper.UIHelper;
import app.gluten.free.gamblinghackaton.spinner.User;

public class App extends Application {

    private static Context context;
    private static Handler handler;
    private static User currentUser;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();

        UIHelper.init(context);
    }

    public static User getCurrentUser() {
        if (currentUser == null){
            currentUser = new User();
            currentUser.load();
        }

        return currentUser;
    }

    public static void setCurrentUser(User _currentUser) {
        currentUser = _currentUser;
        currentUser.save();
    }

    public static Context getContext(){
        return context;
    }

    public static Handler getUIHandler(){
        return handler;
    }
}
