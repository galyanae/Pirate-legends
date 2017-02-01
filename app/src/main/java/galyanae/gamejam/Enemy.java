package galyanae.gamejam;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by RonyBrosh on 1/20/2017.
 */

public class Enemy extends GameObject
{
    public Enemy(Context context, int resourceID)
    {
        super(context, resourceID);
    }

    public static Enemy createEnemy(Context context, int imageResourceID, float locationX, float locationY)
    {
        Enemy result = new Enemy(context, imageResourceID);
        result.setX(locationX);
        result.setY(locationY);
        return result;
    }
}
