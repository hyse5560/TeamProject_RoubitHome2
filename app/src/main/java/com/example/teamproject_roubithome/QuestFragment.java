package com.example.teamproject_roubithome;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import static android.content.Context.MODE_PRIVATE;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Calendar;


public class QuestFragment extends Fragment {

    private RecyclerView recyclerView;
    private QuestAdapter adapter;
    private SharedPreferences prefs;
    private TextView tvQuestSummary;

    private static final String PREFS_NAME = "CheckInPrefs";

    // 퀘스트 목록 내 인덱스
    private static final int WISE_SAYING_QUEST_INDEX = 0;
    private static final int DIARY_QUEST_INDEX = 1;
    private static final int TODO_RECOMMEND_QUEST_INDEX = 2;
    private static final int ROUTINE_QUEST_INDEX = 3;
    // 총 일일 퀘스트 수
    private static final int TOTAL_DAILY_QUESTS = 4;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quest, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_quests);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        tvQuestSummary = view.findViewById(R.id.tv_quest_summary);

        // onCreateView에서는 어댑터만 초기화하고 데이터 로딩은 onResume에서 처리
        adapter = new QuestAdapter(getContext(), new ArrayList<>(), new QuestAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(QuestItem item) {
                if ("오늘의 명언 보기".equals(item.getTitle())) {
                    if (item.isClaimed()) {
                        Toast.makeText(getContext(), "이미 보상을 받았습니다.", Toast.LENGTH_SHORT).show();
                    } else if (item.isCompleted()) {
                        if (getActivity() instanceof MainActivity) {
                            MainActivity mainActivity = (MainActivity) getActivity();
                            EnergyUpdateListener listener = mainActivity;
                            listener.onEnergyUpdated(item.getReward());

                            prefs.edit().putBoolean(MainActivity.KEY_WISE_SAYING_REWARD_CLAIMED_TODAY, true).apply();
                            item.setClaimed(true);
                            adapter.updateQuestItem(WISE_SAYING_QUEST_INDEX, item);
                            updateQuestSummary();

                            Toast.makeText(getContext(), "'오늘의 명언 보기' 퀘스트 완료! 에너지 +" + item.getReward() + "% 획득!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (getActivity() instanceof MainActivity) {
                            ((MainActivity) getActivity()).navigateToHomeForWiseSaying();
                        }
                    }
                } else if ("일기쓰기".equals(item.getTitle())) {
                    if (item.isClaimed()) {
                        Toast.makeText(getContext(), "이미 보상을 받았습니다.", Toast.LENGTH_SHORT).show();
                    } else if (item.isCompleted()) {
                        if (getActivity() instanceof MainActivity) {
                            EnergyUpdateListener listener = (EnergyUpdateListener) getActivity();
                            listener.onEnergyUpdated(item.getReward());

                            prefs.edit().putBoolean(MainActivity.KEY_DIARY_REWARD_CLAIMED_TODAY, true).apply();
                            item.setClaimed(true);
                            adapter.updateQuestItem(DIARY_QUEST_INDEX, item);
                            updateQuestSummary();

                            Toast.makeText(getContext(), "'일기쓰기' 퀘스트 완료! 에너지 +" + item.getReward() + "% 획득!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (getActivity() instanceof MainActivity) {
                            ((MainActivity) getActivity()).showCalendarFragment();
                        }
                    }
                } else if ("루빗에게 TODO 추천받기".equals(item.getTitle())) {
                    if (item.isClaimed()) {
                        Toast.makeText(getContext(), "이미 보상을 받았습니다.", Toast.LENGTH_SHORT).show();
                    } else if (item.isCompleted()) {
                        if (getActivity() instanceof MainActivity) {
                            MainActivity mainActivity = (MainActivity) getActivity();
                            EnergyUpdateListener listener = mainActivity;
                            listener.onEnergyUpdated(item.getReward());

                            prefs.edit().putBoolean(MainActivity.KEY_TODO_REWARD_CLAIMED_TODAY, true).apply();
                            item.setClaimed(true);
                            adapter.updateQuestItem(TODO_RECOMMEND_QUEST_INDEX, item);
                            updateQuestSummary();

                            Toast.makeText(getContext(), "'루빗에게 TODO 추천받기' 퀘스트 완료! 에너지 +" + item.getReward() + "% 획득!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        showTodoRecommendationDialog(item);
                    }
                } else if ("루틴 달성".equals(item.getTitle())) {
                    if (item.isClaimed()) {
                        Toast.makeText(getContext(), "이미 보상을 받았습니다.", Toast.LENGTH_SHORT).show();
                    } else if (item.isCompleted()) {
                        if (getActivity() instanceof MainActivity) {
                            MainActivity mainActivity = (MainActivity) getActivity();
                            EnergyUpdateListener listener = mainActivity;
                            listener.onEnergyUpdated(item.getReward());

                            prefs.edit().putBoolean(MainActivity.KEY_ROUTINE_REWARD_CLAIMED_TODAY, true).apply();
                            item.setClaimed(true);
                            adapter.updateQuestItem(ROUTINE_QUEST_INDEX, item);
                            updateQuestSummary();

                            Toast.makeText(getContext(), "'루틴 달성' 퀘스트 완료! 에너지 +" + item.getReward() + "% 획득!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // HomeFragment로 이동하여 루틴을 달성하도록 유도
                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.container, new HomeFragment())
                                .addToBackStack(null) // 이전 프래그먼트로 돌아갈 수 있도록 스택에 추가
                                .commit();
                        Toast.makeText(getContext(), "홈 화면으로 이동했어요! 루틴을 달성하고 돌아오세요.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        recyclerView.setAdapter(adapter);

        // 초기 로드 시 퀘스트 진행 상황 업데이트는 onResume에서 처리되도록 합니다.
        // updateQuestSummary(); // 여기서는 제거

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 프래그먼트가 다시 활성화될 때마다 퀘스트 목록과 요약을 갱신합니다.
        loadQuestsAndBindAdapter(); // 퀘스트 목록 데이터 갱신
        updateQuestSummary(); // 퀘스트 요약 텍스트 갱신
    }

    // 퀘스트 목록을 로드하고 어댑터를 바인딩하는 새로운 메서드
    private void loadQuestsAndBindAdapter() {
        // 액티비티에 붙어있지 않거나 prefs가 초기화되지 않았으면 실행하지 않음
        if (getActivity() == null || prefs == null) {
            return;
        }

        List<QuestItem> questList = new ArrayList<>();

        // 1. "오늘의 명언 보기" 퀘스트 상태 로드 및 추가
        boolean hasViewedWiseSayingToday = prefs.getBoolean(MainActivity.KEY_WISE_SAYING_VIEWED_TODAY, false);
        boolean hasClaimedWiseSayingRewardToday = prefs.getBoolean(MainActivity.KEY_WISE_SAYING_REWARD_CLAIMED_TODAY, false);
        QuestItem wiseSayingQuest = new QuestItem("오늘의 명언 보기", 33, hasViewedWiseSayingToday, hasClaimedWiseSayingRewardToday);
        questList.add(wiseSayingQuest);

        // 2. "일기쓰기" 퀘스트 상태 로드 및 추가
        boolean hasWrittenDiaryToday = prefs.getBoolean(MainActivity.KEY_DIARY_WRITTEN_TODAY, false);
        boolean hasClaimedDiaryRewardToday = prefs.getBoolean(MainActivity.KEY_DIARY_REWARD_CLAIMED_TODAY, false);
        QuestItem diaryQuest = new QuestItem("일기쓰기", 15, hasWrittenDiaryToday, hasClaimedDiaryRewardToday);
        questList.add(diaryQuest);

        // 3. "루빗에게 TODO 추천받기" 퀘스트 상태 로드 및 추가
        boolean hasTodoRecommendedToday = prefs.getBoolean(MainActivity.KEY_TODO_RECOMMENDED_TODAY, false);
        boolean hasClaimedTodoRewardToday = prefs.getBoolean(MainActivity.KEY_TODO_REWARD_CLAIMED_TODAY, false);
        QuestItem todoRecommendQuest = new QuestItem("루빗에게 TODO 추천받기", 20, hasTodoRecommendedToday, hasClaimedTodoRewardToday);
        questList.add(todoRecommendQuest);

        // 4. "루틴 달성" 퀘스트 상태 로드 및 추가
        boolean hasCompletedRoutineToday = prefs.getBoolean(MainActivity.KEY_ROUTINE_COMPLETED_TODAY, false);
        boolean hasClaimedRoutineRewardToday = prefs.getBoolean(MainActivity.KEY_ROUTINE_REWARD_CLAIMED_TODAY, false);
        QuestItem routineQuest = new QuestItem("루틴 달성", 25, hasCompletedRoutineToday, hasClaimedRoutineRewardToday);
        questList.add(routineQuest);

        // 어댑터 데이터 셋을 업데이트하고 알립니다.
        if (adapter != null) { // adapter가 null이 아닐 때만 호출
            adapter.setQuestList(questList);
            adapter.notifyDataSetChanged();
        }
    } // <-- 이 중괄호가 이전 코드에서 누락되어 오류가 발생했습니다.


    // 퀘스트 진행 상황 계산 및 업데이트 메서드
    private void updateQuestSummary() {
        // getActivity()가 null이 아닌지 확인하여 안정성 강화
        if (getActivity() == null || tvQuestSummary == null || prefs == null) {
            return; // 액티비티에 붙어있지 않거나 UI 요소/prefs가 초기화되지 않았으면 아무것도 하지 않음
        }

        int completedCount = 0;

        // "오늘의 명언 보기" 퀘스트 완료 여부 확인
        if (prefs.getBoolean(MainActivity.KEY_WISE_SAYING_VIEWED_TODAY, false)) {
            completedCount++;
        }

        // "일기쓰기" 퀘스트 완료 여부 확인
        if (prefs.getBoolean(MainActivity.KEY_DIARY_WRITTEN_TODAY, false)) {
            completedCount++;
        }

        // "루빗에게 TODO 추천받기" 퀘스트 완료 여부 확인
        if (prefs.getBoolean(MainActivity.KEY_TODO_RECOMMENDED_TODAY, false)) {
            completedCount++;
        }

        // "루틴 달성" 퀘스트 완료 여부 확인
        // HomeFragment에서 루틴 달성 시 MainActivity.KEY_ROUTINE_COMPLETED_TODAY를 true로 설정해야 합니다.
        if (prefs.getBoolean(MainActivity.KEY_ROUTINE_COMPLETED_TODAY, false)) {
            completedCount++;
        }

        // tvQuestSummary 텍스트 업데이트
        tvQuestSummary.setText(completedCount + "/" + TOTAL_DAILY_QUESTS + " 완료");
    }

    // showTodoRecommendationDialog 메서드 시그니처 변경 (QuestItem 파라미터 추가)
    private void showTodoRecommendationDialog(final QuestItem todoQuestItem) {
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
                .setPositiveButton("좋아요!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        prefs.edit().putBoolean(MainActivity.KEY_TODO_RECOMMENDED_TODAY, true).apply();
                        todoQuestItem.setCompleted(true);
                        adapter.updateQuestItem(TODO_RECOMMEND_QUEST_INDEX, todoQuestItem);
                        updateQuestSummary(); // 퀘스트 진행 상황 업데이트

                        Toast.makeText(getContext(), "'루빗에게 TODO 추천받기' 퀘스트 완료! 이제 보상을 받을 수 있습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("다음에...", null)
                .show();
    }
}