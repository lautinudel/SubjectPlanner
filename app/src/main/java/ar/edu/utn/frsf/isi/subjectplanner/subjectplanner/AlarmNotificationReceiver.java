package ar.edu.utn.frsf.isi.subjectplanner.subjectplanner;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

import ar.edu.utn.frsf.isi.subjectplanner.subjectplanner.Modelo.Tarea;

public class AlarmNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                String CHANNEL_ID = "alarma";
                CharSequence name = context.getResources().getString(R.string.app_name);
                int importance = NotificationManager.IMPORTANCE_HIGH;

                if (android.os.Build.VERSION.SDK_INT >= 26) {
                    NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                    notificationManager.createNotificationChannel(mChannel);
                    builder.setAutoCancel(true).
                    setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_error_black_24dp)
                    .setContentTitle(intent.getStringExtra("nombre"))
                    .setContentText("Tiene una actividad pendiente")
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                    .setChannelId(CHANNEL_ID);
        }
        else {
            builder.setAutoCancel(true).
                    setDefaults(Notification.DEFAULT_ALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_error_black_24dp)
                    .setContentTitle(intent.getStringExtra("nombre"))
                    .setContentText("Tiene una actividad pendiente")
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);

        }

        notificationManager.notify(1, builder.build());

    }

}
