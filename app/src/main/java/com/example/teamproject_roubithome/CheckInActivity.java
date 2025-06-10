package com.example.teamproject_roubithome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CheckInActivity extends AppCompatActivity {

    private TextView tvCheckinMessage;
    private Button btnConfirm;

    private SharedPreferences prefs;
    private static final String PREFS_NAME = "CheckInPrefs";
    private static final String KEY_LAST_DATE = "last_checkin_date";
    private static final String KEY_TOTAL_DAYS = "total_checkin_days";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        tvCheckinMessage = findViewById(R.id.tv_checkin_message);
        btnConfirm = findViewById(R.id.btn_confirm);
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        handleCheckIn();

        btnConfirm.setOnClickListener(v -> {
            Intent intent = new Intent(CheckInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // 일일 출석 체크
    private void handleCheckIn() {
        String today = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
        String lastDate = prefs.getString(KEY_LAST_DATE, "");
        int totalDays = prefs.getInt(KEY_TOTAL_DAYS, 0);

        if (!today.equals(lastDate)) {
            totalDays++;
            prefs.edit()
                    .putString(KEY_LAST_DATE, today)
                    .putInt(KEY_TOTAL_DAYS, totalDays)
                    .apply();
        }

        tvCheckinMessage.setText("출석 체크: " + totalDays + "일차 🎉");
    }
}
