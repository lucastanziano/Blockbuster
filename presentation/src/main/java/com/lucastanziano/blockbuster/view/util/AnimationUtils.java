package lucastanziano.blockbuster.view.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Created by Luca on 21/12/2015.
 */
public class AnimationUtils {

    public static void animateRevealShow(final Context ctx, final View view, final int startRadius,
                                         @ColorRes final int color, int x, int y, final Animator.AnimatorListener listener) {
        float finalRadius = (float) Math.hypot(view.getWidth(), view.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(view, x, y, startRadius, finalRadius);
        anim.setDuration(500);
        anim.setStartDelay(80);
        anim.setInterpolator(new FastOutLinearInInterpolator());
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d("ANIMATION", "onAnimationStart" + listener.toString() );
                view.setVisibility(View.VISIBLE);
                view.setBackgroundColor(ContextCompat.getColor(ctx, color));
                if(listener != null) {
                    listener.onAnimationStart(animation);
                }
                Log.d("ANIMATION", "onAnimationStart2" + listener.toString() );
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("ANIMATION", "onAnimationEnd" + listener.toString() );
                if(listener != null) {
                    listener.onAnimationEnd(animation);
                }
            }
        });
        anim.start();

    }
}
