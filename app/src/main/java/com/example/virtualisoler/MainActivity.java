package com.example.virtualisoler;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button btnIsolate = findViewById(R.id.btn_isolate);
        btnIsolate.setOnClickListener(v -> 
            Toast.makeText(this, "Isolation feature coming soon!", Toast.LENGTH_SHORT).show()
        );
    }
}
