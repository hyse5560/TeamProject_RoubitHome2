package com.example.teamproject_roubithome;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

public class RoutineBundleActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RoutineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_bundle);

        Toolbar toolbar = findViewById(R.id.toolbarRoutineBundle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recyclerViewRoutineBundles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> bundles = Arrays.asList(
                "완벽한 아침 만들기", "생산적 오후 만들기", "정돈된 저녁 만들기"
        );

        adapter = new RoutineAdapter(bundles);
        recyclerView.setAdapter(adapter);
    }
}