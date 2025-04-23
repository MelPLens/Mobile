package com.example.mobile;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent serviceIntent = new Intent(this, BackgroundService.class);
        startService(serviceIntent);
    }

    public static class BackgroundService extends Service {
        @Override

        public IBinder onBind(Intent Intent)
        {
            return null;
        }

        private void showNotification() {
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);
            Notification notification = new NotificationCompat.Builder(this, "default")
                    .setContentTitle("Notificação de Evento")
                    .setContentText("Algo aconteceu!")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pendingIntent)
                    .build();
            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, notification);



        }



    }
}