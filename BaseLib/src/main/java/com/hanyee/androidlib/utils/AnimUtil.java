package com.hanyee.androidlib.utils;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

public class AnimUtil {

    public static void applyRotation(final float degrees, final float depth, final View containerView, final long duration) {
        // 得到中心点(以中心翻转)
        final float centerX = containerView.getWidth() / 2.0f;
        final float centerY = containerView.getHeight() / 2.0f;
        // 根据参数创建一个新的三维动画,并且监听触发下一个动画
        final AbRotate3dAnimation rotation1 = new AbRotate3dAnimation(0, degrees, centerX, centerY, 0.0f, true, true);
        rotation1.setDuration(duration);//设置动画持续时间
        rotation1.setFillAfter(true);
        rotation1.setInterpolator(new DecelerateInterpolator());//设置动画变化速度
        rotation1.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation animation) {
                // 根据参数创建一个新的三维动画,并且监听触发下一个动画
                final AbRotate3dAnimation rotation2 = new AbRotate3dAnimation(degrees, 0, centerX, centerY, depth, true, true);
                rotation2.setDuration(duration);//设置动画持续时间
                rotation2.setFillAfter(true);
                rotation2.setInterpolator(new AccelerateInterpolator());//设置动画变化速度
                containerView.startAnimation(rotation2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });//显示下一个视图
        containerView.startAnimation(rotation1);
    }

    public static void restoreRotation(final float degrees, final float depth, final View containerView, final long duration) {
        // 得到中心点(以中心翻转)
        final float centerX = containerView.getWidth() / 2.0f;
        final float centerY = containerView.getHeight() / 2.0f;
        // 根据参数创建一个新的三维动画,并且监听触发下一个动画
        final AbRotate3dAnimation rotation1 = new AbRotate3dAnimation(0, degrees, centerX, centerY, depth, false, true);
        rotation1.setDuration(duration);//设置动画持续时间
        rotation1.setFillAfter(true);
        rotation1.setInterpolator(new DecelerateInterpolator());//设置动画变化速度
        rotation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                // 根据参数创建一个新的三维动画,并且监听触发下一个动画
                final AbRotate3dAnimation rotation2 = new AbRotate3dAnimation(degrees, 0, centerX, centerY, 0, false, true);
                rotation2.setDuration(duration);//设置动画持续时间
                rotation2.setFillAfter(true);
                rotation2.setInterpolator(new AccelerateInterpolator());//设置动画变化速度
                containerView.startAnimation(rotation2);
                rotation2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        containerView.clearAnimation();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });//显示下一个视图
        containerView.startAnimation(rotation1);
    }

    private static class AbRotate3dAnimation extends Animation {

        /**
         * The m from degrees.
         */
        private final float mFromDegrees;

        /**
         * The m to degrees.
         */
        private final float mToDegrees;

        /**
         * The m center x.
         */
        private final float mCenterX;

        /**
         * The m center y.
         */
        private final float mCenterY;

        /**
         * The m depth z.
         */
        private final float mDepthZ;

        /**
         * The m reverse.
         */
        private final boolean mReverse;
        /**
         * The m rotate.
         */
        private final boolean mRotateX;

        /**
         * The m camera.
         */
        private Camera mCamera;

        /**
         * Creates a new 3D rotation on the Y axis. The rotation is defined by its
         * start angle and its end angle. Both angles are in degrees. The rotation
         * is performed around a center point on the 2D space, definied by a pair
         * of X and Y coordinates, called centerX and centerY. When the animation
         * starts, a translation on the Z axis (depth) is performed. The length
         * of the translation can be specified, as well as whether the translation
         * should be reversed in time.
         *
         * @param fromDegrees the start angle of the 3D rotation
         * @param toDegrees   the end angle of the 3D rotation
         * @param centerX     the X center of the 3D rotation
         * @param centerY     the Y center of the 3D rotation
         * @param depthZ      the depth z
         * @param reverse     true if the translation should be reversed, false otherwise
         * @param rotateX     true if the translation should be rotate X, false rotate Y
         */
        public AbRotate3dAnimation(float fromDegrees, float toDegrees,
                                   float centerX, float centerY, float depthZ, boolean reverse, boolean rotateX) {
            mFromDegrees = fromDegrees;
            mToDegrees = toDegrees;
            mCenterX = centerX;
            mCenterY = centerY;
            mDepthZ = depthZ;
            mReverse = reverse;
            mRotateX = rotateX;
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCamera = new Camera();
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final float fromDegrees = mFromDegrees;
            float degrees = fromDegrees + ((mToDegrees - fromDegrees) * interpolatedTime);

            final float centerX = mCenterX;
            final float centerY = mCenterY;
            final Camera camera = mCamera;

            final Matrix matrix = t.getMatrix();

            camera.save();
            if (mReverse) {
                camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
            } else {
                camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
            }
            if (mRotateX)
                camera.rotateX(degrees);
            else
                camera.rotateY(degrees);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX, centerY);
        }
    }

}
