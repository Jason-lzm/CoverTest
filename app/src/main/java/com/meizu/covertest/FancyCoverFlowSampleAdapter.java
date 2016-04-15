package com.meizu.covertest;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import at.technikum.mti.fancycoverflow.FancyCoverFlowAdapter;

/**
 * Created by lzm on 16-4-15.
 */
public class FancyCoverFlowSampleAdapter extends BaseAdapter {

    @Override
    public int getCount(){
        return 5;
    }

    @Override
    public Integer getItem(int position){
        return R.drawable.image1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View reuseableView, ViewGroup viewGroup) {
        ImageView imageView = null;

        if (reuseableView != null) {
            imageView = (ImageView) reuseableView;
        } else {
            imageView = new ImageView(viewGroup.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setLayoutParams(new FancyCoverFlow.LayoutParams(300, 800));

        }

        imageView.setImageResource(this.getItem(i));
        return imageView;
    }
}
