package com.example.smartpantry;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "PantryPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        CheckBox cbExpiry = findViewById(R.id.cbExpiryAlerts);
        CheckBox cbMetric = findViewById(R.id.cbMetricUnits);
        Button btnSave = findViewById(R.id.btnSaveSettings);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        cbExpiry.setChecked(prefs.getBoolean("EXPIRY_ALERTS", true));
        cbMetric.setChecked(prefs.getBoolean("METRIC_UNITS", true));

        btnSave.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("EXPIRY_ALERTS", cbExpiry.isChecked());
            editor.putBoolean("METRIC_UNITS", cbMetric.isChecked());
            editor.apply();
            Toast.makeText(this, "Settings saved!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}