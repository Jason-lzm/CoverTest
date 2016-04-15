package com.meizu.covertest;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;
/**
 * 扩展Gallery
 */
public class GalleryFlow extends Gallery {
    private static final String TAG = "GalleryFlow";
    private static final int LAYER_TOP = 0;
    private static final int LAYER_MIDDLE = 1;
    private static final int LAYER_BOTTOM = 2;
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private Camera mCamera = new Camera();
    private int mCoveflowCenter;
    private boolean mAlphaMode = true;
    public GalleryFlow(Context context) {
        super(context);
        this.setStaticTransformationsEnabled(true);
    }
    public GalleryFlow(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setStaticTransformationsEnabled(true);
    }
    public GalleryFlow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setStaticTransformationsEnabled(true);
    }
    public boolean getAlphaMode() {
        return mAlphaMode;
    }
    public void setAlphaMode(boolean isAlpha) {
        mAlphaMode = isAlpha;
    }
    private int getCenterOfCoverflow() {
        return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
    }
    private static int getCenterOfView(View view) {
        return view.getLeft() + view.getWidth() / 2;
    }
    //重写Garray方法 ，产生层叠和放大效果
    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        Log.d(TAG, "getChildStaticTransformation");

        // Since Jelly Bean childs won't get invalidated automatically, needs to be added for the smooth coverflow animation
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            child.invalidate();
        }

        final int childCenter = getCenterOfView(child);
        final int childWidth = child.getWidth();
        t.clear();
        t.setTransformationType(Transformation.TYPE_MATRIX);
        if (childCenter == mCoveflowCenter) {
//child = deckTopImgView(child);
//顶层图片
            transformImageBitmap((ImageView) child, t, LAYER_TOP, 0);
        } else if (Math.abs(mCoveflowCenter - childCenter) == childWidth) {
//child = deckMidImgView(child);
            if (mCoveflowCenter > childCenter) {
//左侧中间层图片
                transformImageBitmap((ImageView) child, t, LAYER_MIDDLE, LEFT);
            } else {
//左侧中间层图片
                transformImageBitmap((ImageView) child, t, LAYER_MIDDLE, RIGHT);
            }
        } else if (Math.abs(mCoveflowCenter - childCenter) == 2 * childWidth) {
            Log.d(TAG, "底层图片处理");
//child = deckBottomImgView(child);
            if (mCoveflowCenter > childCenter) {
//左侧底层图片
                transformImageBitmap((ImageView) child, t, LAYER_BOTTOM, LEFT);
            } else {
//左侧底层图片
                transformImageBitmap((ImageView) child, t, LAYER_BOTTOM, RIGHT);
            }
        } else {
        }
        return true;
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCoveflowCenter = getCenterOfCoverflow();
        super.onSizeChanged(w, h, oldw, oldh);
    }
    /**
     * 控制图片的缩放和透明
     * @param child
     * @param t
     * @param layer
     * @param rl
     */
    private void transformImageBitmap(ImageView child, Transformation t, int layer, int rl) {
        mCamera.save();
        final Matrix imageMatrix = t.getMatrix();
        final int imageHeight = child.getLayoutParams().height;
        final int imageWidth = child.getLayoutParams().width;
        switch (layer) {
            case LAYER_TOP:
                mCamera.translate(0.0f, 0.0f, -300.0f);
                deckTopImgView(child);
                break;
            case LAYER_MIDDLE:
                if (rl == LEFT) {
                    mCamera.translate(0.0f, 0.0f, -220.0f);
// mCamera.translate(0.0f, 0.0f, 220.0f);
                } else {
                    mCamera.translate(0.0f, 0.0f, -220.0f);
// mCamera.translate(0.0f, 0.0f, 220.0f);
                }
                deckMidImgView(child);
                break;
            case LAYER_BOTTOM:
                if (rl == LEFT) {
                    Log.d(TAG, "底层左侧图片处理");
                    mCamera.translate(156.0f, 0.0f, -50.0f);
// mCamera.translate(0.0f, 0.0f, 400.0f);
                } else {
                    Log.d(TAG, "底层右侧图片处理");
                    mCamera.translate(-156.0f, 0.0f, -50.0f);
// mCamera.translate(0.0f, 0.0f, 400.0f);
                }
                deckBottomImgView(child);
                break;
            default:
                break;
        }
        mCamera.getMatrix(imageMatrix);
        imageMatrix.preTranslate(-(imageWidth / 2), -(imageHeight / 2));
        imageMatrix.postTranslate((imageWidth / 2), (imageHeight / 2));
        mCamera.restore();
        Log.d(TAG, "transformImageBitmap");
    }
    /**
     * 装饰顶层图片
     * @param view
     * @return
     */
    private View deckTopImgView(View view) {
        ImageView topImg = (ImageView) view;
//topImg.setBackgroundResource(R.drawable.layer_top_bg_sel);
//topImg.setPadding(39, 39, 39, 51);
        topImg.setAlpha(255);
        return topImg;
    }
    /**
     * 装饰中间层图片
     * @param view
     * @return
     */
    private View deckMidImgView(View view) {
        ImageView midImg = (ImageView) view;
        midImg.setBackgroundResource(R.drawable.image1);
        midImg.setPadding(2, 2, 2, 2);
        midImg.setAlpha(230);
        return midImg;
    }
    /**
     * 装饰底层图片
     * @param view
     * @return
     */
    private View deckBottomImgView(View view) {
        ImageView bottomImg = (ImageView) view;
        bottomImg.setBackgroundResource(R.drawable.image1);
        bottomImg.setPadding(3, 3, 3, 3);
        bottomImg.setAlpha(128);
        return bottomImg;
    }
}

