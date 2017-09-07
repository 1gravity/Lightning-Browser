package com.mgensuite.airfox.browser;

import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

/**
 * Animates a progress bar to go from one to another value
 */
public class ProgressBarAnimation extends Animation {
    final private RoundCornerProgressBar mProgressBar;
    final private float mFrom;
    final private float mTo;

    public ProgressBarAnimation(RoundCornerProgressBar progressBar, float from, float to) {
        super();

        mProgressBar = progressBar;
        mFrom = from;
        mTo = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = mFrom + (mTo - mFrom) * interpolatedTime;
        mProgressBar.setProgress((int) value);
    }

}