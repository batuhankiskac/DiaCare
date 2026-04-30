package com.example.diacare;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_PERMISSION_CODE = 101;

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

        requestNotificationPermission();
        scheduleReminder();
        fetchDailyTip();
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        NOTIFICATION_PERMISSION_CODE);
            }
        }
    }

    private void scheduleReminder() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        long triggerTime = System.currentTimeMillis() + (10 * 1000);

        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
        }
    }

    private void fetchDailyTip() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            try {
                URL url = new URL("https://raw.githubusercontent.com/batuhankiskac/DiaCare/main/dailytip.txt");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();

                String tipOfTheDay = result.toString();

                runOnUiThread(() -> {
                    TextView textViewDailyTip = findViewById(R.id.textViewDailyTip);
                    textViewDailyTip.setText(tipOfTheDay);
                });

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    TextView textViewDailyTip = findViewById(R.id.textViewDailyTip);
                    textViewDailyTip.setText(R.string.error_no_internet_tip);
                });
            } finally {
                executor.shutdown();
            }
        });
    }
}