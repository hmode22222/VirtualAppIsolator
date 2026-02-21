package com.example.virtualisoler;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
    private int currentTab = 0;

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

        // تحميل التطبيقات
        loadInstalledApps();
        
        // إعداد المحول
        adapter = new AppListAdapter(this, allApps, virtualApps);
        appsList.setAdapter(adapter);

        // إعداد البحث
        setupSearch(searchBtn);
        
        // إعداد علامات التبويب
        setupTabs();
        
        // إعداد زر الإضافة
        setupAddButton(addFab);
        
        // بدء الخدمة
        startVirtualService();
    }

    private void setupSearch(Button searchBtn) {
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

        searchBtn.setOnClickListener(v -> filterApps(searchBox.getText().toString()));
    }

    private void setupTabs() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTab = tab.getPosition();
                updateList();
            }
            
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void setupAddButton(FloatingActionButton addFab) {
        addFab.setOnClickListener(v -> {
            tabLayout.getTabAt(0).select();
            Toast.makeText(this, "Select app to add to virtual space", Toast.LENGTH_SHORT).show();
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
    }

    private void filterApps(String query) {
        if (adapter != null) {
            adapter.filter(query);
        }
    }

    private void updateList() {
        if (adapter != null) {
            adapter.setCurrentTab(currentTab);
        }
    }

    public void addToVirtual(AppInfo app) {
        if (!virtualApps.contains(app)) {
            app.setVirtual(true);
            virtualApps.add(app);
            Toast.makeText(this, "Added: " + app.getName(), Toast.LENGTH_SHORT).show();
            updateList();
        }
    }

    public void removeFromVirtual(AppInfo app) {
        virtualApps.remove(app);
        app.setVirtual(false);
        Toast.makeText(this, "Removed: " + app.getName(), Toast.LENGTH_SHORT).show();
        updateList();
    }

    public void openVirtualApp(AppInfo app) {
        Intent serviceIntent = new Intent(this, VirtualService.class);
        serviceIntent.setAction("START_VIRTUAL_APP");
        serviceIntent.putExtra("package", app.getPackageName());
        startService(serviceIntent);
    }

    private void startVirtualService() {
        Intent serviceIntent = new Intent(this, VirtualService.class);
        startService(serviceIntent);
    }
}
