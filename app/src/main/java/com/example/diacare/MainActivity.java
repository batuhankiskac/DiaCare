package com.example.diacare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonBloodSugar = findViewById(R.id.buttonBloodSugar);
        Button buttonCarbCalc = findViewById(R.id.buttonCarbCalc);

        buttonBloodSugar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BloodSugarActivity.class);
            startActivity(intent);
        });

        buttonCarbCalc.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CarbCalculatorActivity.class);
            startActivity(intent);
        });
    }
}