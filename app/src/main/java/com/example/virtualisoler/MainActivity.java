package com.example.virtualisoler;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        TextView textView = new TextView(this);
        textView.setText("التطبيق يعمل!");
        textView.setTextSize(24);
        textView.setGravity(17); // CENTER
        
        setContentView(textView);
    }
}
