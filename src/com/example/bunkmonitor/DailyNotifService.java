package com.example.bunkmonitor;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by Tejas on 8/24/13.
 */
public class DailyNotifService extends IntentService {
    private static final String TAG = "DailyNotifService";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public DailyNotifService() {
        super("DailyNotifService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i(TAG, "Update Bp and Hr Service Started");

        SharedPreferences mPrefs = getSharedPreferences(
                "bunkmonitor.SHARED_PREF", 0);
        String lastEntryDate = mPrefs.getString("bunkmonitor.LAST_ENTRY_DATE", "0");
        String today = Utilities.getDate(Utilities.getCurrentTime());

        //TODO Need to add checks for long gaps and unsyncs


        if (!today.equals(lastEntryDate)) {
            //Check for holidays
            //TODO Need to input National Holidays
            String[] s = today.split("/");
            Calendar cal = Calendar.getInstance();
            cal.set(Integer.decode(s[2]), Integer.decode(s[1]) - 1, Integer.decode(s[0]));
            if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
                //sendBroadcast(new Intent(UpdatesListActivity.REFRESH_ACTION));
                Utilities.toggleActiveCourses(this, Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
                generateNotification(DailyNotifService.this, "Time to fill daily entry!");
            }

        }
        ;

    }

    public static void generateNotification(Context context, String message) {
//        Log.i(TAG, "Generating notification + count: " + count);

//        int icon = R.drawable.ic_launcher;
        int icon = R.drawable.ic_launcher;

        long when = System.currentTimeMillis();

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new Notification(icon, message, when);

        String title = context.getString(R.string.app_name);

        Intent notificationIntent = null;

        notificationIntent = new Intent(context, EntryActivity.class);

        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent intent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;

        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);
    }


}
