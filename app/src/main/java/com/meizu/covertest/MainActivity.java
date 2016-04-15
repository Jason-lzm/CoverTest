package com.meizu.covertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import at.technikum.mti.fancycoverflow.FancyCoverFlow;


public class MainActivity extends AppCompatActivity {

    GalleryFlow fancyCoverFlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fancyCoverFlow = (GalleryFlow) findViewById(R.id.fancyCoverFlow);

        this.fancyCoverFlow.setAdapter(new FancyCoverFlowSampleAdapter());
        this.fancyCoverFlow.setUnselectedAlpha(1.0f);
        this.fancyCoverFlow.setSpacing(10);
    }
}
