package com.example.bunkmonitor;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * Created by stejasvin on 3/16/14.
 */
public class tutorialActivity extends Activity {

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
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);

        Gallery gallery = (Gallery) findViewById(R.id.tut_gallery);
//        selectedImage=(ImageView)findViewById(R.id.tut_image);
        gallery.setSpacing(1);
        gallery.setAdapter(new GalleryImageAdapter(this));

        // clicklistener for Gallery
//        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                Toast.makeText(tutorialActivity.this, "Your selected position = " + position, Toast.LENGTH_SHORT).show();
//                // show the selected Image
//                selectedImage.setImageResource(mImageIds[position]);
//            }
//        });
    }


}
