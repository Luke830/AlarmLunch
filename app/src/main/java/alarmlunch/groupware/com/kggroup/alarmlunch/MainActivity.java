package alarmlunch.groupware.com.kggroup.alarmlunch;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import java.util.Calendar;


public class MainActivity extends Activity {

    public static int HH = 19;
    public static int MM = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Log.d("HHH", "compare = " + compare());

        if (compare() < 1) {
            Calendar cal1 = Calendar.getInstance();

            int year = cal1.get(Calendar.YEAR);
            int month = cal1.get(Calendar.MONTH);
            int date = cal1.get(Calendar.DATE);
            cal1.set(year, month, date, HH, MM);

            startAlram(this, cal1.getTimeInMillis());
        } else {
            Calendar cal2 = Calendar.getInstance();
            int year = cal2.get(Calendar.YEAR);
            int month = cal2.get(Calendar.MONTH);
            int date = cal2.get(Calendar.DATE);
            cal2.set(year, month, date, HH, MM);
            cal2.add(Calendar.DATE, 1);

            MainActivity.startAlram(this, cal2.getTimeInMillis());
        }

    }

    public int compare() {

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        int year = cal2.get(Calendar.YEAR);
        int month = cal2.get(Calendar.MONTH);
        int date = cal2.get(Calendar.DATE);
//        int hour = cal2.get(Calendar.HOUR_OF_DAY);
//        int min = cal2.get(Calendar.MINUTE);
        cal2.set(year, month, date, HH, MM);

        return cal1.compareTo(cal2);

    }


    public static void startAlram(Context context, long setTime) {

        Intent alarmIntent = new Intent(context, AlarmRecievier.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

        // AlarmManager 호출
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // 1분뒤에 AlarmOneMinuteBroadcastReceiver 호출 한다.
        if (Build.VERSION.SDK_INT >= 23) {
//            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, setTime, pendingIntent);
            manager.setExact(AlarmManager.RTC_WAKEUP, setTime, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            manager.setExact(AlarmManager.RTC_WAKEUP, setTime, pendingIntent);
        } else {
            manager.set(AlarmManager.RTC_WAKEUP, setTime, pendingIntent);
        }
    }


}
