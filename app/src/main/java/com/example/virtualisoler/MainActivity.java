package com.example.virtualisoler;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView appsList;
    private EditText searchBox;
    private AppListAdapter adapter;
    private List<AppInfo> allApps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appsList = findViewById(R.id.appsList);
        searchBox = findViewById(R.id.searchBox);

        loadInstalledApps();

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterApps(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadInstalledApps() {
        PackageManager pm = getPackageManager();
        List<ResolveInfo> apps = pm.queryIntentActivities(
            pm.getLaunchIntentForPackage("android.intent.category.LAUNCHER"), 0);

        for (ResolveInfo app : apps) {
            String packageName = app.activityInfo.packageName;
            String appName = app.loadLabel(pm).toString();
            Drawable icon = app.loadIcon(pm);
            
            allApps.add(new AppInfo(appName, packageName, icon));
        }

        adapter = new AppListAdapter(this, allApps);
        appsList.setAdapter(adapter);
    }

    private void filterApps(String query) {
        List<AppInfo> filteredList = new ArrayList<>();
        for (AppInfo app : allApps) {
            if (app.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(app);
            }
        }
        adapter.updateList(filteredList);
    }

    public void isolateApp(String packageName) {
        Toast.makeText(this, "Isolating: " + packageName, Toast.LENGTH_SHORT).show();
        // هنا سيتم إضافة كود العزل لاحقاً
    }
}
