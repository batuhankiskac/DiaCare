package com.example.diacare;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BloodSugarActivity extends AppCompatActivity {

    private EditText editTextBloodSugar;
    private LinearLayout layoutRecordHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_sugar);

        editTextBloodSugar = findViewById(R.id.editTextBloodSugar);
        Button buttonSaveRecord = findViewById(R.id.buttonSaveRecord);
        layoutRecordHistory = findViewById(R.id.layoutRecordHistory);

        buttonSaveRecord.setOnClickListener(v -> saveBloodSugar());
    }

    private void saveBloodSugar() {
        String input = editTextBloodSugar.getText().toString();

        if (input.isEmpty()) {
            Toast.makeText(this, R.string.error_enter_value, Toast.LENGTH_SHORT).show();
            return;
        }

        int sugarLevel = Integer.parseInt(input);

        String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());

        BloodSugar record = new BloodSugar(sugarLevel, currentDate, "General Measurement");

        com.google.android.material.card.MaterialCardView cardView = new com.google.android.material.card.MaterialCardView(this);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardParams.setMargins(0, 0, 0, 24);
        cardView.setLayoutParams(cardParams);
        cardView.setRadius(24f);
        cardView.setCardElevation(4f);
        cardView.setCardBackgroundColor(android.graphics.Color.WHITE);

        TextView recordView = new TextView(this);
        String recordText = getString(R.string.blood_sugar_record_format,
                record.getDate(), record.getSugarLevel(), record.getMealType());
        recordView.setText(recordText);
        recordView.setTextSize(16f);
        recordView.setTextColor(getResources().getColor(R.color.text_primary, getTheme()));
        recordView.setPadding(48, 48, 48, 48);

        cardView.addView(recordView);
        layoutRecordHistory.addView(cardView);

        editTextBloodSugar.setText("");
    }
}