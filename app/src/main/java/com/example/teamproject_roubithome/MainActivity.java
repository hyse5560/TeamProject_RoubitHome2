package com.example.teamproject_roubithome;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements EnergyUpdateListener {

    private HomeFragment homeFragment;
    private SharedPreferences prefs;

    private static final String PREFS_NAME = "CheckInPrefs"; // 모든 퀘스트 상태를 저장할 SharedPreferences 파일 이름
    public static final String KEY_WISE_SAYING_VIEWED_TODAY = "wise_saying_viewed_today";
    public static final String KEY_WISE_SAYING_REWARD_CLAIMED_TODAY = "wise_saying_reward_claimed_today";
    public static final String KEY_LAST_RESET_DATE = "last_reset_date";

    // 일기쓰기 퀘스트 관련 새로운 키 추가
    public static final String KEY_DIARY_WRITTEN_TODAY = "diary_written_today"; // 오늘 일기를 썼는지 여부
    public static final String KEY_DIARY_REWARD_CLAIMED_TODAY = "diary_reward_claimed_today"; // 오늘 일기쓰기 보상을 받았는지 여부
    public static final String KEY_TODO_RECOMMENDED_TODAY = "todo_recommended_today";
    public static final String KEY_TODO_REWARD_CLAIMED_TODAY = "todo_reward_claimed_today";

    // 루틴 달성 퀘스트를 위한 새로운 키 추가
    public static final String KEY_ROUTINE_COMPLETED_TODAY = "routine_completed_today"; // 루틴 완료 상태
    public static final String KEY_ROUTINE_REWARD_CLAIMED_TODAY = "routine_reward_claimed_today"; // 루틴 보상 획득 상태


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SharedPreferences 초기화
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // 매일 퀘스트 상태 초기화 로직 호출 (가장 먼저 실행되도록)
        checkAndResetDailyQuests();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                selectedFragment = homeFragment;
            } else if (itemId == R.id.nav_quest) {
                // QuestFragment가 새로 생성될 때 SharedPreferences의 최신 상태를 읽도록 합니다.
                selectedFragment = new QuestFragment();
            } else if (itemId == R.id.nav_calendar) {
                selectedFragment = new CalendarFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, selectedFragment)
                        .commit();
            }
            return true;
        });

        // 초기 화면 설정
        if (savedInstanceState == null) {
            homeFragment = new HomeFragment();
            bottomNav.setSelectedItemId(R.id.nav_home);
        }
    }

    // 일일 퀘스트 상태를 확인하고 필요시 초기화하는 메서드
    private void checkAndResetDailyQuests() {
        String lastResetDate = prefs.getString(KEY_LAST_RESET_DATE, "");

        // 현재 날짜를 "YYYYMMDD" 형식으로 가져오기
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String currentDate = sdf.format(Calendar.getInstance().getTime());

        if (!currentDate.equals(lastResetDate)) {
            // 새로운 날이므로 일일 퀘스트 상태를 초기화합니다.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(KEY_WISE_SAYING_VIEWED_TODAY, false);
            editor.putBoolean(KEY_WISE_SAYING_REWARD_CLAIMED_TODAY, false);

            // 일기쓰기 퀘스트 상태도 초기화
            editor.putBoolean(KEY_DIARY_WRITTEN_TODAY, false);
            editor.putBoolean(KEY_DIARY_REWARD_CLAIMED_TODAY, false);

            editor.putString(KEY_LAST_RESET_DATE, currentDate); // 마지막 초기화 날짜를 오늘로 업데이트
            editor.apply(); // 변경 사항 적용
            Log.d("DailyReset", "일일 퀘스트 상태가 " + currentDate + "로 초기화되었습니다.");
        } else {
            Log.d("DailyReset", "오늘은 " + currentDate + "이므로 퀘스트 초기화가 필요 없습니다.");
        }
    }

    public void navigateToHomeForWiseSaying() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);
        Toast.makeText(this, "홈 화면으로 이동했어요! 토끼를 눌러 명언을 확인해보세요.", Toast.LENGTH_LONG).show();
    }

    public void showCalendarFragment() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_calendar);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new CalendarFragment())
                .addToBackStack(null) // 이전 프래그먼트로 돌아갈 수 있도록 스택에 추가
                .commit();
    }

    public void showTodoRecommendFragment() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);
    }

    @Override
    public void onEnergyUpdated(int amount) {
        if (homeFragment != null && homeFragment.isAdded()) {
            homeFragment.addEnergy(amount);
            Toast.makeText(this, "에너지 " + amount + "% 획득!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "에너지 획득! (홈 화면으로 가면 반영됩니다.)", Toast.LENGTH_SHORT).show();
        }
    }
}