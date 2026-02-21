package com.example.virtualisoler;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationCompat;

public class VirtualService extends Service {
    private static final String TAG = "VirtualService";
    private static final String CHANNEL_ID = "virtual_channel";
    private static final int NOTIFICATION_ID = 1001;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        startForeground(NOTIFICATION_ID, createNotification());
        Log.d(TAG, "Virtual Service Started");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case "START_VIRTUAL_APP":
                        String packageName = intent.getStringExtra("package");
                        startVirtualApp(packageName);
                        break;
                    case "STOP_VIRTUAL_APP":
                        stopVirtualApp(intent.getStringExtra("package"));
                        break;
                }
            }
        }
        return START_STICKY;
    }

    private void startVirtualApp(String packageName) {
        Log.d(TAG, "Starting virtual app: " + packageName);
        try {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageName);
            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(launchIntent);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error starting app: " + e.getMessage());
        }
    }

    private void stopVirtualApp(String packageName) {
        Log.d(TAG, "Stopping virtual app: " + packageName);
        // سيتم إضافة كود إيقاف التطبيق هنا
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Virtual Service",
                NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private Notification createNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Virtual Isoler")
            .setContentText("Virtual environment is running")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
