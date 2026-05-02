package com.example.diacare;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BloodSugarActivity extends AppCompatActivity {

    private static final String TAG = "BloodSugarActivity";

    private EditText editTextBloodSugar;
    private LinearLayout layoutRecordHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_blood_sugar);

        editTextBloodSugar = findViewById(R.id.editTextBloodSugar);
        Button buttonSaveRecord = findViewById(R.id.buttonSaveRecord);
        layoutRecordHistory = findViewById(R.id.layoutRecordHistory);

        buttonSaveRecord.setOnClickListener(v -> saveBloodSugar());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    private void saveBloodSugar() {
        String input = editTextBloodSugar.getText().toString();

        if (input.isEmpty()) {
            Toast.makeText(this, R.string.error_enter_value, Toast.LENGTH_SHORT).show();
            return;
        }

        int sugarLevel;
        try {
            sugarLevel = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid whole number!", Toast.LENGTH_SHORT).show();
            return;
        }

        String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());

        BloodSugar record = new BloodSugar(sugarLevel, currentDate, "General Measurement");

        TextView recordView = new TextView(this);
        String recordText = getString(R.string.blood_sugar_record_format,
                record.getDate(), record.getSugarLevel(), record.getMealType());
        recordView.setText(recordText);
        recordView.setTextSize(16f);
        recordView.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
        recordView.setPadding(24, 20, 24, 20);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 12);
        recordView.setLayoutParams(params);

        android.graphics.drawable.GradientDrawable bgDrawable = new android.graphics.drawable.GradientDrawable();
        bgDrawable.setCornerRadius(12);

        if (sugarLevel < 100) {
            bgDrawable.setColor(ContextCompat.getColor(this, R.color.status_normal));
        } else if (sugarLevel <= 180) {
            bgDrawable.setColor(ContextCompat.getColor(this, R.color.status_warning));
        } else {
            bgDrawable.setColor(ContextCompat.getColor(this, R.color.status_danger));
        }
        recordView.setBackground(bgDrawable);

        layoutRecordHistory.addView(recordView);

        editTextBloodSugar.setText("");
    }
}
