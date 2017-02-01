package galyanae.gamejam;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Owner on 20/01/2017.
 */

public class GameObject extends ImageView
{

    protected final int NUMBER_OF_COLUMNS = 10;
    protected final int NUMBER_OF_ROWS = 6;
    protected float mScreenWidth;
    protected float mScreenHeight;
    protected float mGameViewWidth;
    protected float mGameViewHeight;
    protected float mRowHeight;
    protected float mColumnWidth;
    protected float mPadding;
    protected int mResourceID;

    public GameObject(Context context, int imageResourceID)
    {
        super(context);
        mResourceID = imageResourceID;
        setImageResource(imageResourceID);
        initView(imageResourceID);
    }

    protected void initView(int imageResourceID)
    {
        setScaleType(ScaleType.FIT_CENTER);
        initGameScreenSize();
        initViewSize(imageResourceID);
    }

    private void initGameScreenSize()
    {
        mPadding = Utils.dpTOpx(getContext(), 16);
        mScreenWidth = Utils.getScreenSize(getContext(), true);
        mScreenHeight = Utils.getScreenSize(getContext(), false);
        mGameViewWidth = mScreenWidth - 2 * mPadding;
        mGameViewHeight = mScreenHeight - 2 * mPadding;
        mRowHeight = mGameViewHeight / NUMBER_OF_ROWS;
        mColumnWidth = mGameViewWidth / NUMBER_OF_COLUMNS;
    }

    private void initViewSize(int imageResourceID)
    {
        //Rum: 143 X 154
        //Coin: 114 X 104
        //Stone: 271 X 286
        float gameObjectHeight = 0f;
        float gameObjectWidth = 0f;
        if (imageResourceID == R.drawable.pirates_rum330x330)
        {
            gameObjectHeight = mScreenHeight * 154f / 1080f;
            gameObjectWidth = gameObjectHeight * 143f / 154f;
        }
        else if (imageResourceID == R.drawable.pirates_coin330x330)
        {
            gameObjectHeight = mScreenHeight * 104f / 1080f;
            gameObjectWidth = gameObjectHeight * 104f / 104f;
        }
        else if (imageResourceID == R.drawable.pirates_rocks330x330)
        {
            gameObjectHeight = mScreenHeight * 286f / 1080f;
            gameObjectWidth = gameObjectHeight * 271f / 286f;
        }
        else if (imageResourceID == R.drawable.pirates_kraken330x330)
        {
            gameObjectHeight = mScreenHeight * 213f / 1080f;
            gameObjectWidth = gameObjectHeight * 323f / 213f;
        }
        else if (imageResourceID == R.drawable.pirates_treasure330x330)
        {
            gameObjectHeight = mScreenHeight * 274f / 1080f;
            gameObjectWidth = gameObjectHeight * 324f / 274f;
        }
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams((int) gameObjectWidth, (int) gameObjectHeight);
        setLayoutParams(params);
    }
}
