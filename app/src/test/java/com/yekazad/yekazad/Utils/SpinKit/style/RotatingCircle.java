package com.rovas.forgram.yekazad.Utils.SpinKit.style;

import android.animation.ValueAnimator;


import com.rovas.forgram.yekazad.Utils.SpinKit.animation.SpriteAnimatorBuilder;
import com.rovas.forgram.yekazad.Utils.SpinKit.sprite.CircleSprite;

public class RotatingCircle extends CircleSprite {

    @Override
    public ValueAnimator onCreateAnimation() {
        float fractions[] = new float[]{0f, 0.5f, 1f};
        return new SpriteAnimatorBuilder(this).
                rotateX(fractions, 0, -180, -180).
                rotateY(fractions, 0, 0, -180).
                duration(1200).
                easeInOut(fractions)
                .build();
    }
}
