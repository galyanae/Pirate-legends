package galyanae.gamejam;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by RonyBrosh on 12/28/2016.
 */

public class AnimationUtil
{
    public static AnimatorSet animateView(View view, boolean isUp, float stepHeight, final Animator.AnimatorListener animatorListener)
    {
        view.clearAnimation();
        AnimatorSet result = new AnimatorSet();
        float delta = view.getY() - stepHeight;
        if (isUp == false)
        {
            delta = view.getY() + stepHeight;
        }
        result.play(ObjectAnimator.ofFloat(view, "translationY", delta));
        result.setDuration(100);
        result.setInterpolator(new AccelerateDecelerateInterpolator());
        if (animatorListener != null)
        {
            result.addListener(animatorListener);
        }
        result.start();

        return result;
    }

    public static AnimatorSet animateViewSlide(View view, int direction, long speed, final Animator.AnimatorListener animatorListener)
    {
        view.clearAnimation();
        AnimatorSet result = new AnimatorSet();
        float delta = view.getX() + 100;
        if (direction == GameManager.ROW_DIRECTION_LEFT)
        {
            delta = view.getX() - 100;
        }
        result.play(ObjectAnimator.ofFloat(view, "translationX", delta));
        result.setDuration(speed);
        result.setInterpolator(new LinearInterpolator());
        if (animatorListener != null)
        {
            result.addListener(animatorListener);
        }
        result.start();

        return result;
    }

    public static AnimatorSet animateKraken(View view, float slideSize, final Animator.AnimatorListener animatorListener)
    {
        view.clearAnimation();
        AnimatorSet result = new AnimatorSet();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationX", slideSize);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        result.play(objectAnimator);
        result.setDuration(3000);
        result.setInterpolator(new LinearInterpolator());
        if (animatorListener != null)
        {
            result.addListener(animatorListener);
        }
        result.start();

        return result;
    }
}

