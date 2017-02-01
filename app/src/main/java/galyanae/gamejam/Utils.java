package galyanae.gamejam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.PictureDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;

public class Utils
{
    public static int dpTOpx(Context context, float dp)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    @SuppressLint("NewApi")
    public static int getScreenSize(Context context, boolean isWidth)
    {
        int result = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        if (isWidth)
        {
            if (Build.VERSION.SDK_INT >= 19)
            {
                Point point = new Point();
                display.getRealSize(point);
                result = point.x;
            }
            else
            {
                result = display.getWidth();
            }
        }
        else
        {
            if (Build.VERSION.SDK_INT >= 19)
            {
                Point point = new Point();
                display.getRealSize(point);
                result = point.y;
            }
            else
            {
                result = display.getHeight();
            }
        }
        return result;
    }
}
