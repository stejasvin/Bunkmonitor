package com.example.bunkmonitor;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by stejasvin on 3/2/14.
 */
public class CustomFontsLoader {

    public static final int FONT_NAME_1 =   0;
//    public static final int FONT_NAME_2 =   1;
//    public static final int FONT_NAME_3 =   2;

    private static final int NUM_OF_CUSTOM_FONTS = 1;

    private static boolean fontsLoaded = false;

    private static Typeface[] fonts = new Typeface[NUM_OF_CUSTOM_FONTS];

    private static String[] fontPath = {
            "rudiment.ttf"

    };


    /**
     * Returns a loaded custom font based on it's identifier.
     *
     * @param context - the current context
     *
     *
     * @return Typeface object of the requested font.
     */
    public static Typeface getTypeface(Context context) {
        int fontIdentifier = 0;
        if (!fontsLoaded) {
            loadFonts(context);
        }
        return fonts[fontIdentifier];
    }


    private static void loadFonts(Context context) {
        for (int i = 0; i < NUM_OF_CUSTOM_FONTS; i++) {
            fonts[i] = Typeface.createFromAsset(context.getAssets(), fontPath[i]);
        }
        fontsLoaded = true;

    }
}