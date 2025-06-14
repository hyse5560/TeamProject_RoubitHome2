package com.example.teamproject_roubithome.model;

public class QuestItem {
    private String title;
    private int reward;
    private boolean completed;
    private boolean claimed; // 보상을 받았는지 여부를 나타내는 필드

    // 초기 퀘스트용 생성자
    public QuestItem(String title, int reward, boolean completed) {
        this.title = title;
        this.reward = reward;
        this.completed = completed;
        this.claimed = false; // 기본값: 3개 인자로 만들 때는 보상받지 않은 상태로 초기화
    }

    // 상태 로드용 생성자
    public QuestItem(String title, int reward, boolean completed, boolean claimed) {
        this.title = title;
        this.reward = reward;
        this.completed = completed;
        this.claimed = claimed; // 처음에는 보상받지 않은 상태로 초기화
    }


    // 기존 getter 및 setter는 그대로 유지합니다.
    public String getTitle() {
        return title;
    }

    public int getReward() {
        return reward;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isClaimed() {
        return claimed;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }
}