package com.example.teamproject_roubithome;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class RoutineListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RoutineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_list);

        Toolbar toolbar = findViewById(R.id.toolbarRoutineList);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recyclerViewRoutines);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> routines = Arrays.asList(
                "기상",
                "집중해서 공부",
                "독서",
                "운동",
                "영단어 암기",
                "방 청소"
        );

        adapter = new RoutineAdapter(routines);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}