package com.example.teamproject_roubithome;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;


public class CalendarFragment extends Fragment {

    public CalendarFragment() {}

    private CalendarView calendarView;
    private TextView ddayTextView;
    private Button writeMemoButton;
    private TextView appUsageTextView;
    private SharedPreferences prefs;

    private int selectedYear = -1;
    private int selectedMonth = -1;
    private int selectedDay = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calender, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarView = view.findViewById(R.id.calendarView);
        ddayTextView = view.findViewById(R.id.ddayTextView);
        writeMemoButton = view.findViewById(R.id.memoButton);
        appUsageTextView = view.findViewById(R.id.appUsageTextView);

        prefs = requireContext().getSharedPreferences("Memos", requireContext().MODE_PRIVATE);

        calendarView.setOnDateChangeListener((viewObj, year, month, dayOfMonth) -> {
            selectedYear = year;
            selectedMonth = month;
            selectedDay = dayOfMonth;
            updateDday(year, month, dayOfMonth);
        });

        writeMemoButton.setOnClickListener(v -> {
            if (selectedYear == -1) {
                Toast.makeText(requireContext(), "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            String dateKey = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
            showMemoDialog(dateKey);
        });

        showAppUsageDays();
    }

    private void showMemoDialog(String dateKey) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("메모: " + dateKey);

        final EditText input = new EditText(requireContext());
        String savedMemo = prefs.getString("memo_" + dateKey, "");
        String savedMemoDate = prefs.getString("memoDate_" + dateKey, "");

        if (!savedMemo.isEmpty() && !savedMemoDate.isEmpty()) {
            input.setText(savedMemo + "\n\n(작성일: " + savedMemoDate + ")");
        }

        builder.setView(input);

        builder.setPositiveButton("저장", (dialog, which) -> {
            String memo = input.getText().toString();
            String today = getTodayString();
            prefs.edit()
                    .putString("memo_" + dateKey, memo)
                    .putString("memoDate_" + dateKey, today)
                    .apply();
        });

        builder.setNegativeButton("취소", null);
        builder.show();
    }

    private void updateDday(int year, int month, int dayOfMonth) {
        // 현재 날짜
        java.util.Calendar today = java.util.Calendar.getInstance();
        today.set(java.util.Calendar.HOUR_OF_DAY, 0);
        today.set(java.util.Calendar.MINUTE, 0);
        today.set(java.util.Calendar.SECOND, 0);
        today.set(java.util.Calendar.MILLISECOND, 0);

        // 선택한 날짜
        java.util.Calendar target = java.util.Calendar.getInstance();
        target.set(year, month, dayOfMonth, 0, 0, 0);
        target.set(java.util.Calendar.MILLISECOND, 0);

        // 날짜 차이 계산
        long diffInMillis = target.getTimeInMillis() - today.getTimeInMillis();
        long dday = TimeUnit.MILLISECONDS.toDays(diffInMillis);

        String result = dday == 0 ? "D-Day" : (dday > 0 ? "D-" + dday : "D+" + Math.abs(dday));
        ddayTextView.setText("D-Day Count: " + result);
    }

    private void showAppUsageDays() {
        String firstLaunch = prefs.getString("firstLaunchDate", null);
        String today = getTodayString();

        if (firstLaunch == null) {
            prefs.edit().putString("firstLaunchDate", today).apply();
            firstLaunch = today;
        }

        long daysUsed = calculateDateDiffInDays(firstLaunch, today);
        appUsageTextView.setText("앱을 사용한 지: " + daysUsed + "일째");
    }

    private String getTodayString() {
        java.util.Calendar today = java.util.Calendar.getInstance();
        int y = today.get(java.util.Calendar.YEAR);
        int m = today.get(java.util.Calendar.MONTH) + 1;
        int d = today.get(java.util.Calendar.DAY_OF_MONTH);
        return y + "-" + m + "-" + d;
    }

    private long calculateDateDiffInDays(String fromDateStr, String toDateStr) {
        String[] fromParts = fromDateStr.split("-");
        String[] toParts = toDateStr.split("-");

        java.util.Calendar from = java.util.Calendar.getInstance();
        from.set(Integer.parseInt(fromParts[0]), Integer.parseInt(fromParts[1]) - 1, Integer.parseInt(fromParts[2]), 0, 0, 0);
        from.set(java.util.Calendar.MILLISECOND, 0);

        java.util.Calendar to = java.util.Calendar.getInstance();
        to.set(Integer.parseInt(toParts[0]), Integer.parseInt(toParts[1]) - 1, Integer.parseInt(toParts[2]), 0, 0, 0);
        to.set(java.util.Calendar.MILLISECOND, 0);

        long millisDiff = to.getTimeInMillis() - from.getTimeInMillis();
        return TimeUnit.MILLISECONDS.toDays(millisDiff) + 1;
    }

}