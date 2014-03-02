package com.example.bunkmonitor;

/**
 * Created by stejasvin on 3/3/14.
 */
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView {

    Context context;
    String ttfName = "rudiment.ttf";

    String TAG = getClass().getName();

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();

    }

    private void init() {
        Typeface font = Typeface.createFromAsset(context.getAssets(), ttfName);
        setTypeface(font);
        setTextColor(getResources().getColor(android.R.color.white));
    }

    @Override
    public void setTypeface(Typeface tf) {

        // TODO Auto-generated method stub
        super.setTypeface(tf);
    }

}