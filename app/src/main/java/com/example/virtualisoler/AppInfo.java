package com.example.virtualisoler;

import android.graphics.drawable.Drawable;

public class AppInfo {
    private String name;
    private String packageName;
    private Drawable icon;
    private boolean isVirtual;
    private long installTime;
    private long appSize;
    private boolean hasModule;

    public AppInfo(String name, String packageName, Drawable icon) {
        this.name = name;
        this.packageName = packageName;
        this.icon = icon;
        this.isVirtual = false;
        this.hasModule = false;
        this.installTime = System.currentTimeMillis();
    }

    // Getters
    public String getName() { return name; }
    public String getPackageName() { return packageName; }
    public Drawable getIcon() { return icon; }
    public boolean isVirtual() { return isVirtual; }
    public long getInstallTime() { return installTime; }
    public long getAppSize() { return appSize; }
    public boolean hasModule() { return hasModule; }

    // Setters
    public void setVirtual(boolean virtual) { isVirtual = virtual; }
    public void setAppSize(long size) { appSize = size; }
    public void setHasModule(boolean module) { hasModule = module; }
}
