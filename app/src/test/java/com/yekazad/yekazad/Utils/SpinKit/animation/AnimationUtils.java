// Bismillahirrahmannirrahim
// Elhamdulillahirabbulalemin
// Esselatu ve sselamu alâ Resulillah

package com.yekazad.yekazad.Utils.SpinKit.animation;
import android.animation.Animator;
import android.animation.ValueAnimator;


import com.rovas.forgram.yekazad.Utils.SpinKit.sprite.Sprite;

/**
 * Created by Mohamed El Sayed.
 */
public class AnimationUtils {

    public static void start(Animator animator) {
        if (animator != null && !animator.isStarted()) {
            animator.start();
        }
    }

    public static void stop(Animator animator) {
        if (animator != null && !animator.isRunning()) {
            animator.end();
        }
    }

    public static void start(Sprite... sprites) {
        for (Sprite sprite : sprites) {
            sprite.start();
        }
    }

    public static void stop(Sprite... sprites) {
        for (Sprite sprite : sprites) {
            sprite.stop();
        }
    }

    public static boolean isRunning(Sprite... sprites) {
        for (Sprite sprite : sprites) {
            if (sprite.isRunning()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRunning(ValueAnimator animator) {
        return animator != null && animator.isRunning();
    }

    public static boolean isStarted(ValueAnimator animator) {
        return animator != null && animator.isStarted();
    }
}
