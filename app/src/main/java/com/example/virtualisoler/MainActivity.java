package com.example.virtualisoler;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.appsList);
        Button btnTest = findViewById(R.id.btn_isolate);

        ArrayList<String> items = new ArrayList<>();
        items.add("App 1");
        items.add("App 2");
        items.add("App 3");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        btnTest.setOnClickListener(v -> 
            Toast.makeText(this, "App is working!", Toast.LENGTH_SHORT).show()
        );
    }
}
