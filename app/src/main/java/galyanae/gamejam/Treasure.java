package galyanae.gamejam;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by Owner on 20/01/2017.
 */

public class Treasure extends GameObject
{
    public Treasure(Context context, int resourceID)
    {
        super(context, resourceID);
    }

    public static Treasure createTreasure(Context context, int imageResourceID, float locationX, float locationY)
    {
        Treasure result = new Treasure(context, imageResourceID);
        result.setX(locationX);
        result.setY(locationY);
        return result;
    }
}
