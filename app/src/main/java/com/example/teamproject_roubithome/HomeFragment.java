package com.example.teamproject_roubithome;

import static android.content.Context.MODE_PRIVATE;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private TextView tvUserLevel;
    private ImageView ivRabbit;
    private Button btnRoutineList, btnRoutineBundle;

    private SharedPreferences prefs;
    private static final String PREFS_NAME = "CheckInPrefs";
    private static final String KEY_TOTAL_DAYS = "total_checkin_days";
    private static final int MAX_GOALS = 5;
    private static final String KEY_GOAL_PREFIX = "goal_";
    private static final String KEY_GOAL_CHECKED_PREFIX = "goal_checked_";

    private List<RabbitDialogContent> rabbitDialogContents;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefs = getActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        ProgressBar energyBar = view.findViewById(R.id.progress_energy);
        tvUserLevel = view.findViewById(R.id.tv_user_level);
        ivRabbit = view.findViewById(R.id.iv_rabbit);
        TextView tvTodayDate = view.findViewById(R.id.tv_today_date);
        LinearLayout layoutGoalContainer = view.findViewById(R.id.layout_goal_container);
        btnRoutineList = view.findViewById(R.id.btn_routine_list);
        btnRoutineBundle = view.findViewById(R.id.btn_routine_bundle);

        String today = new SimpleDateFormat("M월 d일 E요일", Locale.KOREAN).format(Calendar.getInstance().getTime());
        tvTodayDate.setText(today);

        initializeRabbitDialogContents();

        for (int i = 0; i < MAX_GOALS; i++) {
            String goalText = prefs.getString(KEY_GOAL_PREFIX + i, "");
            boolean isChecked = prefs.getBoolean(KEY_GOAL_CHECKED_PREFIX + i, false);

            EditText goalEditText = new EditText(getContext());
            goalEditText.setText(goalText);
            goalEditText.setHint("목표 입력");
            goalEditText.setTextSize(15);

            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setChecked(isChecked);
            checkBox.setText("완료");

            int goalIndex = i;
            checkBox.setOnCheckedChangeListener((buttonView, isCheckedNow) -> {
                prefs.edit().putBoolean(KEY_GOAL_CHECKED_PREFIX + goalIndex, isCheckedNow).apply();
            });

            layoutGoalContainer.addView(goalEditText);
            layoutGoalContainer.addView(checkBox);
        }

        int totalDays = prefs.getInt(KEY_TOTAL_DAYS, 1);
        int level = Math.min(10, 1 + totalDays / 3); // 3일마다 레벨 업
        int progress = (int) ((totalDays % 3) / 3.0f * 100);
        energyBar.setProgress(progress);
        tvUserLevel.setText("Lv." + level);

        String[] goals = {
                "🧘‍♀️ 스트레칭 10분 하기",
                "📖 책 10쪽 이상 읽기",
                "🥕 채소 한 번 이상 먹기"
        };

        for (String goal : goals) {
            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(goal);
            checkBox.setTextSize(15);
            checkBox.setPadding(4, 8, 4, 8);
            layoutGoalContainer.addView(checkBox);
        }

        if (level <= 3) {
            ivRabbit.setImageResource(R.drawable.babyrabbit);
        } else if (level <= 6) {
            ivRabbit.setImageResource(R.drawable.teenrabbit);
        } else {
            ivRabbit.setImageResource(R.drawable.adultrabbit);
        }

        ivRabbit.setOnClickListener(v -> {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(ivRabbit, "scaleX", 1f, 1.1f, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(ivRabbit, "scaleY", 1f, 1.1f, 1f);
            AnimatorSet scaleSet = new AnimatorSet();
            scaleSet.playTogether(scaleX, scaleY);
            scaleSet.setDuration(200);
            scaleSet.start();

            showRabbitDialog();
        });
        btnRoutineList.setOnClickListener(v -> startActivity(new Intent(getContext(), RoutineListActivity.class)));
        btnRoutineBundle.setOnClickListener(v -> startActivity(new Intent(getContext(), RoutineBundleActivity.class)));
    }

    private void showRabbitDialog() {
        Random random = new Random();
        RabbitDialogContent content = rabbitDialogContents.get(random.nextInt(rabbitDialogContents.size()));

        new AlertDialog.Builder(getContext())
                .setTitle(content.title)
                .setMessage(content.message)
                .setPositiveButton("확인", null)
                .show();
    }

    private void initializeRabbitDialogContents() {
        rabbitDialogContents = new ArrayList<>();
        rabbitDialogContents.add(new RabbitDialogContent("오늘의 루틴 제안 🐰", "📖 오늘의 명언:\n“성공은 습관에서 시작된다.”\n\n💡 자기개발: 작은 목표부터 실천해보세요!"));
        rabbitDialogContents.add(new RabbitDialogContent("토끼의 지혜 🐰", "📖 오늘의 명언:\n“가장 큰 위험은 위험없는 삶이다.”\n\n💡 도전: 새로운 것을 배우거나 시도해보세요!"));
        rabbitDialogContents.add(new RabbitDialogContent("힘내세요! 🐰", "📖 오늘의 명언:\n“실패는 성공의 어머니이다.”\n\n💡 긍정: 오늘 하루 긍정적인 마음을 가져보세요!"));
        rabbitDialogContents.add(new RabbitDialogContent("루틴의 힘 🐰", "📖 오늘의 명언:\n“우리가 반복적으로 하는 행동이 바로 우리 자신이다. 그러므로 탁월함은 행동이 아닌 습관이다.”\n\n💡 꾸준함: 작은 루틴이라도 꾸준히 지켜나가세요!"));
        rabbitDialogContents.add(new RabbitDialogContent("성장의 비밀 🐰", "📖 오늘의 명언:\n“어제보다 나은 오늘, 오늘보다 나은 내일을 만들자.”\n\n💡 발전: 어제보다 조금 더 나아진 자신을 느껴보세요!"));
        rabbitDialogContents.add(new RabbitDialogContent("긍정 에너지 🐰", "📖 오늘의 명언:\n“웃음은 최고의 명약이다.”\n\n💡 행복: 오늘 하루 많이 웃으세요!"));
        rabbitDialogContents.add(new RabbitDialogContent("집중의 시간 🐰", "📖 오늘의 명언:\n“가장 중요한 일에 집중하라.”\n\n💡 효율: 오늘 가장 중요한 일 한 가지를 정하고 집중해보세요!"));
        rabbitDialogContents.add(new RabbitDialogContent("새로운 시작 🐰", "📖 오늘의 명언:\n“매일 아침은 새로운 시작이다.”\n\n💡 활기: 활기찬 하루를 시작해보세요!"));
        rabbitDialogContents.add(new RabbitDialogContent("감사하는 마음 🐰", "📖 오늘의 명언:\n“감사하는 마음은 행복의 씨앗이다.”\n\n💡 감사: 주변의 작은 것들에 감사함을 느껴보세요!"));
        rabbitDialogContents.add(new RabbitDialogContent("도전 정신 🐰", "📖 오늘의 명언:\n“불가능은 없다. 노력하면 무엇이든 가능하다.”\n\n💡 용기: 새로운 도전에 용기를 내보세요!"));

    }
    public static class RabbitDialogContent {
        String title;
        String message;

        public RabbitDialogContent(String title, String message) {
            this.title = title;
            this.message = message;
        }
    }

}

