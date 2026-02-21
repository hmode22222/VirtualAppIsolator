package com.example.virtualisoler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class AppListAdapter extends BaseAdapter {
    private MainActivity activity;
    private List<AppInfo> allApps;
    private List<AppInfo> virtualApps;
    private List<AppInfo> currentList;
    private LayoutInflater inflater;
    private int currentTab = 0;
    private String filterQuery = "";

    public AppListAdapter(MainActivity activity, List<AppInfo> allApps, List<AppInfo> virtualApps) {
        this.activity = activity;
        this.allApps = allApps;
        this.virtualApps = virtualApps;
        this.inflater = LayoutInflater.from(activity);
        updateCurrentList();
    }

    @Override
    public int getCount() {
        return currentList.size();
    }

    @Override
    public Object getItem(int position) {
        return currentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.app_item, parent, false);
        }

        AppInfo app = currentList.get(position);

        ImageView iconView = view.findViewById(R.id.appIcon);
        TextView nameView = view.findViewById(R.id.appName);
        Button actionBtn = view.findViewById(R.id.actionBtn);
        ImageView moduleIndicator = view.findViewById(R.id.moduleIndicator);

        iconView.setImageDrawable(app.getIcon());
        nameView.setText(app.getName());

        // تحديث زر الإجراء حسب الحالة
        if (app.isVirtual()) {
            actionBtn.setText("Open");
            actionBtn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFF4CAF50));
        } else {
            actionBtn.setText("Add");
            actionBtn.setBackgroundTintList(android.content.res.ColorStateList.valueOf(0xFF6200EE));
        }

        // إظهار مؤشر الوحدة إذا وجدت
        moduleIndicator.setVisibility(app.hasModule() ? View.VISIBLE : View.GONE);

        // تعيين حدث النقر
        actionBtn.setOnClickListener(v -> {
            if (app.isVirtual()) {
                activity.openVirtualApp(app);
            } else {
                activity.addToVirtual(app);
            }
        });

        // النقر الطويل للإزالة
        view.setOnLongClickListener(v -> {
            if (app.isVirtual()) {
                activity.removeFromVirtual(app);
            }
            return true;
        });

        return view;
    }

    public void setCurrentTab(int tab) {
        this.currentTab = tab;
        updateCurrentList();
    }

    public void filter(String query) {
        this.filterQuery = query.toLowerCase();
        updateCurrentList();
    }

    private void updateCurrentList() {
        List<AppInfo> sourceList = (currentTab == 0) ? allApps : virtualApps;
        currentList = new ArrayList<>();

        for (AppInfo app : sourceList) {
            if (filterQuery.isEmpty() || app.getName().toLowerCase().contains(filterQuery)) {
                currentList.add(app);
            }
        }

        notifyDataSetChanged();
    }
}
