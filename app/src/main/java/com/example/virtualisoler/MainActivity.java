package com.example.virtualisoler;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView appsList;
    private EditText searchBox;
    private TabLayout tabLayout;
    private AppListAdapter adapter;
    private List<AppInfo> allApps = new ArrayList<>();
    private List<AppInfo> virtualApps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // تهيئة العناصر
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        tabLayout = findViewById(R.id.tabLayout);
        appsList = findViewById(R.id.appsList);
        searchBox = findViewById(R.id.searchBox);
        Button searchBtn = findViewById(R.id.searchBtn);
        FloatingActionButton addFab = findViewById(R.id.addFab);

        loadInstalledApps();

        // إعداد البحث
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

        // أزرار البحث
        searchBtn.setOnClickListener(v -> filterApps(searchBox.getText().toString()));

        // علامات التبويب
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    adapter.updateList(allApps);
                } else if (tab.getPosition() == 1) {
                    adapter.updateList(virtualApps);
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // زر الإضافة
        addFab.setOnClickListener(v -> {
            Toast.makeText(this, "Select app to add to virtual space", Toast.LENGTH_SHORT).show();
            tabLayout.getTabAt(0).select();
        });
    }

    private void loadInstalledApps() {
        PackageManager pm = getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent, 0);
        
        for (ResolveInfo info : resolveInfos) {
            String appName = info.loadLabel(pm).toString();
            String packageName = info.activityInfo.packageName;
            Drawable icon = info.loadIcon(pm);
            
            AppInfo app = new AppInfo(appName, packageName, icon);
            allApps.add(app);
        }

        adapter = new AppListAdapter(this, allApps);
        appsList.setAdapter(adapter);
    }

    private void filterApps(String query) {
        List<AppInfo> filteredList = new ArrayList<>();
        List<AppInfo> sourceList = tabLayout.getSelectedTabPosition() == 0 ? allApps : virtualApps;
        
        for (AppInfo app : sourceList) {
            if (app.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(app);
            }
        }
        adapter.updateList(filteredList);
    }

    public void addToVirtual(AppInfo app) {
        app.setVirtual(true);
        virtualApps.add(app);
        Toast.makeText(this, "Added to virtual space: " + app.getName(), Toast.LENGTH_SHORT).show();
        
        // تحديث القائمة
        if (tabLayout.getSelectedTabPosition() == 1) {
            adapter.updateList(virtualApps);
        }
    }

    public void openVirtualApp(AppInfo app) {
        Toast.makeText(this, "Opening: " + app.getName(), Toast.LENGTH_SHORT).show();
        
        Intent serviceIntent = new Intent(this, VirtualService.class);
        serviceIntent.setAction("START_VIRTUAL_APP");
        serviceIntent.putExtra("package", app.getPackageName());
        startService(serviceIntent);
    }
}
