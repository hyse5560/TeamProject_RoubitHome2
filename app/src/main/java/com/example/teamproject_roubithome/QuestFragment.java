package com.example.teamproject_roubithome;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamproject_roubithome.model.QuestAdapter;
import com.example.teamproject_roubithome.model.QuestItem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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

        adapter = new QuestAdapter(getContext(), questList, new QuestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(QuestItem item) {
                if ("오늘의 명언 보기".equals(item.getTitle())) {
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).showWiseSayingFragment();
                    }
                } else if ("일기쓰기".equals(item.getTitle())) {
                    // DiaryFragment로 이동
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).showCalendarFragment();
                    }
                } else if ("루빗에게 TODO 추천받기".equals(item.getTitle())) {
                    showTodoRecommendationDialog();
                } else if ("루틴 달성".equals(item.getTitle())) {
                    // HomeFragment로 이동 (루틴 달성 화면이 HomeFragment라고 가정)
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.layout_goal_container, new HomeFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void showTodoRecommendationDialog() {
        // TODO: 실제 TODO 항목 리스트로 교체
        // 좀더 루틴에 알맞는 리스트로 변경
        List<String> todoList = new ArrayList<>();
        todoList.add("아침 스트레칭 10분 하기");
        todoList.add("건강한 아침 식사 챙겨 먹기");
        todoList.add("출근/등교길에 책 1챕터 읽기");
        todoList.add("점심 식사 후 가벼운 산책 15분");
        todoList.add("오늘 하루 감사한 일 3가지 적기");
        todoList.add("저녁 식사 후 설거지 바로 하기");
        todoList.add("잠들기 전 명상 5분 하기");
        todoList.add("내일 할 일 계획 세우기");
        todoList.add("하루 동안 마신 물의 양 기록하기");
        todoList.add("새로운 단어 5개 외우기");

        Random random = new Random();
        String recommendedTodo = todoList.get(random.nextInt(todoList.size()));

        new AlertDialog.Builder(getContext())
                .setTitle("루빗의 TODO 추천")
                .setMessage("오늘 \"" + recommendedTodo + "\" 어때요?")
                .setPositiveButton("좋아요!", null)
                .show();
    }

}