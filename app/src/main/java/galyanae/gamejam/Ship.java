package galyanae.gamejam;

import android.animation.Animator;
import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Owner on 20/01/2017.
 */

public class Ship extends GameObject
{

    int stepHeight = 0;
    boolean isAnimative;
    boolean mIsSliding;
    private int mCurrentRowIndex;

    public Ship(Context context, int stepHeight)
    {
        super(context, R.drawable.pirates_ship330x330);
        this.stepHeight = stepHeight;
    }

    public void shipMoove(final boolean isUp)
    {
        if ((isUp == false && mCurrentRowIndex < GameManager.NUMBER_OF_ROWS - 1) || (isUp == true && mCurrentRowIndex > 0))
        {
            AnimationUtil.animateView(this, isUp, stepHeight, new Animator.AnimatorListener()
            {
                @Override
                public void onAnimationStart(Animator animation)
                {
                    isAnimative = true;
                }

                @Override
                public void onAnimationEnd(Animator animation)
                {
                    isAnimative = false;
                    if (isUp == true)
                    {
                        mCurrentRowIndex--;
                    }
                    else
                    {
                        mCurrentRowIndex++;
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation)
                {

                }

                @Override
                public void onAnimationRepeat(Animator animation)
                {

                }
            });
        }
    }

    public void reset()
    {
        setX(mPadding);
        setY(mPadding + (mRowHeight * NUMBER_OF_ROWS) - getLayoutParams().height);
        mCurrentRowIndex = 5;
    }

    public void slide(int slideDirection, int speed)
    {
        if (mIsSliding == false)
        {
            AnimationUtil.animateViewSlide(this, slideDirection, speed, new Animator.AnimatorListener()
            {
                @Override
                public void onAnimationStart(Animator animator)
                {
                    mIsSliding = true;
                }

                @Override
                public void onAnimationEnd(Animator animator)
                {
                    mIsSliding = false;
                }

                @Override
                public void onAnimationCancel(Animator animator)
                {

                }

                @Override
                public void onAnimationRepeat(Animator animator)
                {

                }
            });
        }
    }

    public int getCurrentRowIndex()
    {
        return mCurrentRowIndex;
    }
}
