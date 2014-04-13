package com.example.bunkmonitor;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * Created by stejasvin on 3/16/14.
 */
public class GalleryImageAdapter extends BaseAdapter
{
    private final int screenWidthInPix;
    private final int screenheightInPix;
    private Context mContext;

    ImageView selectedImage;
    private Integer[] mImageIds = {
            R.drawable.tut1,
            R.drawable.tut2,
            R.drawable.tut3,
            R.drawable.tut4,
            R.drawable.tut5,
            R.drawable.tut6,
            R.drawable.tut7,
            R.drawable.tut8,
            R.drawable.tut9,
            R.drawable.tut10,
            R.drawable.tut11,
            R.drawable.tut12,
            R.drawable.tut13,
            R.drawable.tut14,
            R.drawable.tut15,
            R.drawable.tut16,
            R.drawable.tut17
    };

    public GalleryImageAdapter(Context context)
    {
        mContext = context;
        DisplayMetrics displayMetrics = context.getResources()
                .getDisplayMetrics();
        screenWidthInPix = displayMetrics.widthPixels;
        screenheightInPix = displayMetrics.heightPixels;
    }

    public int getCount() {
        return mImageIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    // Override this method according to your need
    public View getView(int index, View view, ViewGroup viewGroup)
    {

        // TODO Auto-generated method stub
        ImageView i = new ImageView(mContext);

        i.setImageResource(mImageIds[index]);
        i.setLayoutParams(new Gallery.LayoutParams((int) (screenWidthInPix * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT));
        i.setAdjustViewBounds(true);
        i.setPadding(2,2,2,2);
        i.setScaleType(ImageView.ScaleType.FIT_CENTER);

        return i;
    }
}

