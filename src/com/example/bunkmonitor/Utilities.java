package com.example.bunkmonitor;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by stejasvin on 12/13/13.
 */
public class Utilities {

    public static final int WRITE = 0;
    public static final int READ = 1;
    public static final int SINGLE = 2;
    public static final int BATCH = 3;
    private static final String TAG = "Utilities";
    public final int NOC_ENTRY_SHEET = 5;

    //Entry status
    public static final int ATTENDED = 10;
    public static final int BUNKED = 20;
    public static final int CANCELLED = 30;
    public static final int EXTRA = 40;

    public class MyInteger{
        int i;
        public MyInteger(int j){ i = j;}
    }

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

    public static void toggleActiveCourses(Context context,int dayOfWeek){

        SharedPreferences mPrefs = context.getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);
        //Calendar cal = Calendar.getInstance();
        //int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        String slots = "";
        switch (dayOfWeek){
            case Calendar.MONDAY:
                slots = mPrefs.getString("MONDAY","");
                break;
            case Calendar.TUESDAY:
                slots = mPrefs.getString("TUESDAY","");
                break;
            case Calendar.WEDNESDAY:
                slots = mPrefs.getString("WEDNESDAY","");
                break;
            case Calendar.THURSDAY:
                slots = mPrefs.getString("THURSDAY","");
                break;
            case Calendar.FRIDAY:
                slots = mPrefs.getString("FRIDAY","");
                break;
            case Calendar.SATURDAY:
                slots = mPrefs.getString("SATURDAY","");
                break;
            case Calendar.SUNDAY:
                slots = mPrefs.getString("SUNDAY","");
                break;
        }
        CourseDatabaseHandler courseDatabaseHandler = new CourseDatabaseHandler(context);
        List<Course> cList = courseDatabaseHandler.getAllCourses();
        slots = slots.toLowerCase();
        for(Course c:cList){
            for(int i=0;i<slots.length();i++){
                if(c.getSlot().toLowerCase().equals(""+slots.charAt(i))){
                    c.setActive(1);
                    break;
                }else
                    c.setActive(0);

                //setCourseActive(slots.charAt(i))
            }
            if(slots.length() == 0)
                c.setActive(0);
            courseDatabaseHandler.updateCourse(c);
        }
    }

    public static void processDateArray(String[] s){
        if(s[0].charAt(0)=='0')
            s[0]=s[0].substring(1);
        if(s[1].charAt(0)=='0')
            s[1]=s[1].substring(1);
        if(s[2].charAt(0)=='0')
            s[2]=s[2].substring(1);

    }

    public static void setRecurringAlarm(Context context,int snooze) {
        Log.i(TAG, "setting Alarm");
        Calendar updateTime = Calendar.getInstance();

        SharedPreferences mPrefs = context.getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);
        int time = mPrefs.getInt("NOTIF_TIME",1700);

        if(snooze == 0){
            updateTime.set(Calendar.HOUR_OF_DAY, time/100);
            updateTime.set(Calendar.MINUTE, time%100);
        }else{
            //snooze for 1 hour
            updateTime.add(Calendar.HOUR_OF_DAY,1);
        }
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("ALARM", true);

        PendingIntent recurringDownload = PendingIntent.getBroadcast(context,
                0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarms = (AlarmManager) context.getSystemService(
                Context.ALARM_SERVICE);

        alarms.setRepeating(AlarmManager.RTC_WAKEUP,
                updateTime.getTimeInMillis(),
                AlarmManager.INTERVAL_HOUR, recurringDownload);  //Need to use INTERVAL_DAY instead of 10000
    }

    public static String[] getSlotsPerDay(Context context){

         String[] days = new String[6];

         SharedPreferences mPrefs = context.getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);

         days[0] = mPrefs.getString("MONDAY","");
         days[1] = mPrefs.getString("TUESDAY","");
         days[2] = mPrefs.getString("WEDNESDAY","");
         days[3] = mPrefs.getString("THURSDAY","");
         days[4] = mPrefs.getString("FRIDAY","");
         days[5] = mPrefs.getString("SATURDAY","");

        return days;

    }

    public static void setSlotsPerDay(Context context,String[] days){

        SharedPreferences mPrefs = context.getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("MONDAY",days[0]).commit();
        mEditor.putString("TUESDAY",days[1]).commit();
        mEditor.putString("WEDNESDAY",days[2]).commit();
        mEditor.putString("THURSDAY",days[3]).commit();
        mEditor.putString("FRIDAY",days[4]).commit();
        mEditor.putString("SATURDAY",days[5]).commit();

    }


    public static void cancelAlarm(Context context){
        Intent downloader = new Intent(context, AlarmReceiver.class);
        PendingIntent recurringDownload = PendingIntent.getBroadcast(context,
                0, downloader, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarms = (AlarmManager) context.getSystemService(
                Context.ALARM_SERVICE);
        alarms.cancel(recurringDownload);
    }
}

