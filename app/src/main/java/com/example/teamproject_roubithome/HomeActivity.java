package com.example.teamproject_roubithome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button btnPopularRoutines, btnRoutineBundles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnPopularRoutines = findViewById(R.id.btnPopularRoutines);
        btnRoutineBundles = findViewById(R.id.btnRoutineBundles);

        // 인기 루틴 보기 화면으로 이동
        btnPopularRoutines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RoutineListActivity.class);
                startActivity(intent);
            }
        });

        // 루틴 묶음 화면으로 이동
        btnRoutineBundles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RoutineBundleActivity.class);
                startActivity(intent);
            }
        });
    }
}
