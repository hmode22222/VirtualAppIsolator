package com.example.virtualisoler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class AppListAdapter extends BaseAdapter {
    private MainActivity activity;
    private List<AppInfo> apps;
    private LayoutInflater inflater;

    public AppListAdapter(MainActivity activity, List<AppInfo> apps) {
        this.activity = activity;
        this.apps = apps;
        this.inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return apps.size();
    }

    @Override
    public Object getItem(int position) {
        return apps.get(position);
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

        AppInfo app = apps.get(position);

        ImageView iconView = view.findViewById(R.id.appIcon);
        TextView nameView = view.findViewById(R.id.appName);
        Button isolateBtn = view.findViewById(R.id.isolateBtn);

        iconView.setImageDrawable(app.getIcon());
        nameView.setText(app.getName());

        isolateBtn.setOnClickListener(v -> 
            activity.isolateApp(app.getPackageName())
        );

        return view;
    }

    public void updateList(List<AppInfo> newList) {
        apps = newList;
        notifyDataSetChanged();
    }
}
