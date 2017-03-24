package alarmlunch.groupware.com.kggroup.alarmlunch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by itsm02 on 2016. 10. 12..
 */

public class AlarmRecievier extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("HHHHH", "dddd");

        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
        if (day_of_week > 1 && day_of_week < 7) { // 토요일, 일요일 제외

            Toast.makeText(context, "AAAAAAA", Toast.LENGTH_LONG).show();

            Intent startIntent = new Intent(context, ShowActivity.class);
            startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(startIntent);

        }

        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.MINUTE, 1);
        MainActivity.startAlram(context, cal2.getTimeInMillis());

    }


}
