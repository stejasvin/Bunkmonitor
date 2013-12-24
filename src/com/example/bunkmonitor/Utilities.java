package com.example.bunkmonitor;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by stejasvin on 12/13/13.
 */
public class Utilities {

    public final int NOC_ENTRY_SHEET = 5;

    //Entry status
    public static final int ATTENDED = 10;
    public static final int BUNKED = 20;
    public static final int CANCELLED = 30;
    public static final int EXTRA = 40;

    public static String getCurrentTime() {
        Calendar c = Calendar.getInstance();
        String currentTime = (c.getTime()).toString();
        return currentTime;
    }

    /**
     * Formats the given timestamp from a non-intuitive format to a nice format
     *
     * @param input full time n date
     * @return date
     */
    public static String getDate(String input) {
        if (input != null) {
            SimpleDateFormat dateFormat = null;
            Date date = null;

            SimpleDateFormat dateFormatOutput = new SimpleDateFormat(
                    "dd/MM/yyyy");

            try {

//				dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");

                date = dateFormat.parse(input);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                try {

                    dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

                    date = dateFormat.parse(input);
                } catch (ParseException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();

                    try {

                        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        date = dateFormat.parse(input);
                    } catch (ParseException e2) {
                        // TODO Auto-generated catch block
                        e2.printStackTrace();

                    }

                }
            }
            if (date != null) {
                Log.i("TIME-DATE", date.toString());

                String output = dateFormatOutput.format(date);

                Log.i("TIME-DATE", output);

                // output = output.replace(":", "-");
                return output;
            }
        }

        return null;
    }
}
