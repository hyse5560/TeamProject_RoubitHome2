package com.example.teamproject_roubithome;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class QuestActivity extends AppCompatActivity {

    private LinearLayout questListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);


        // 예시 퀘스트들 추가
        addQuest("오늘의 명언 보기", "🥕 당근 2개", "완료");
        addQuest("일기쓰기", "🥕 당근 3개", "하러가기");
        addQuest("루빗에게 TODO 추천받기", "🥕 당근 2개", "하러가기");
        addQuest("루틴 또는 TODO 달성", "🥕 당근 3개", "하러가기");
        addQuest("광고 보기", "🪙 코인 100개", "하러가기");
    }

    private void addQuest(String title, String reward, String buttonText) {
        LinearLayout questItem = new LinearLayout(this);
        questItem.setOrientation(LinearLayout.HORIZONTAL);
        questItem.setPadding(16, 16, 16, 16);
        questItem.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);
        questItem.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        questItem.setGravity(android.view.Gravity.CENTER_VERTICAL);

        TextView questText = new TextView(this);
        questText.setText(title + "\n" + reward);
        questText.setTextSize(14);
        questText.setLayoutParams(new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        Button actionButton = new Button(this);
        actionButton.setText(buttonText);
        actionButton.setTextSize(12);

        questItem.addView(questText);
        questItem.addView(actionButton);

        questListContainer.addView(questItem);
    }
}