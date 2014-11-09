package mycompany.iorder;

/**
 * Created by iOrder on 9/11/2014.
 */
import android.util.Log;

public class Logger {

    public static void d(String message) {
        if (Config.LOG_DEBUG_ENABLED)
            Log.d(Config.TAG,message);
    }

    public static void i(String message) {
        Log.i(Config.TAG,message);
    }

    public static void e(final String message, Throwable e) {
        if (e!=null)
            Log.e(Config.TAG, message, e);
        else
            Log.e(Config.TAG, message);
    }

}
