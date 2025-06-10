package com.example.teamproject_roubithome;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamproject_roubithome.model.QuestAdapter;
import com.example.teamproject_roubithome.model.QuestItem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class QuestFragment extends Fragment {

    private RecyclerView recyclerView;
    private QuestAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quest, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_quests);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<QuestItem> questList = new ArrayList<>();
        questList.add(new QuestItem("오늘의 명언 보기", 10, false));
        questList.add(new QuestItem("일기쓰기", 15, false));
        questList.add(new QuestItem("루빗에게 TODO 추천받기", 20, false));
        questList.add(new QuestItem("루틴 달성", 25, false));

        adapter = new QuestAdapter(questList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}