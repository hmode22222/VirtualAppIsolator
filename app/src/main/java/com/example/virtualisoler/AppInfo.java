package com.example.virtualisoler;

import android.graphics.drawable.Drawable;

public class AppInfo {
    private String name;
    private String packageName;
    private Drawable icon;
    private boolean isVirtual;
    private boolean hasModule;

    public AppInfo(String name, String packageName, Drawable icon) {
        this.name = name;
        this.packageName = packageName;
        this.icon = icon;
        this.isVirtual = false;
        this.hasModule = false;
    }

    public String getName() { return name; }
    public String getPackageName() { return packageName; }
    public Drawable getIcon() { return icon; }
    public boolean isVirtual() { return isVirtual; }
    public boolean hasModule() { return hasModule; }
    
    public void setVirtual(boolean virtual) { isVirtual = virtual; }
    public void setHasModule(boolean module) { hasModule = module; }
}
