package com.example.virtualisoler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PackageReceiver extends BroadcastReceiver {
    private static final String TAG = "PackageReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String packageName = intent.getData() != null ? intent.getData().getSchemeSpecificPart() : null;

        if (packageName == null) return;

        switch (action) {
            case Intent.ACTION_PACKAGE_ADDED:
                Log.d(TAG, "App installed: " + packageName);
                // تحديث قائمة التطبيقات
                break;
                
            case Intent.ACTION_PACKAGE_REMOVED:
                Log.d(TAG, "App removed: " + packageName);
                // إزالة من القائمة المعزولة إذا كانت موجودة
                break;
                
            case Intent.ACTION_PACKAGE_REPLACED:
                Log.d(TAG, "App updated: " + packageName);
                // تحديث معلومات التطبيق
                break;
        }
    }
}
