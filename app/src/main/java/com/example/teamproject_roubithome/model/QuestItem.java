package com.example.teamproject_roubithome.model;

public class QuestItem {
    private String title;
    private String description;
    private int reward;
    private boolean isCompleted;
    private boolean isCoinReward; // 코인 보상 여부 추가
    private int iconResId; // 아이콘 리소스 ID 추가
    private String buttonText; // 버튼 텍스트 추가

    // 기본 생성자
    public QuestItem(String title, int reward, boolean isCompleted) {
        this.title = title;
        this.description = description;
        this.reward = reward;
        this.isCompleted = isCompleted;
        this.isCoinReward = false; // 명시적으로 false 처리
        this.iconResId = iconResId; // 아이콘 리소스 ID 초기화
        this.buttonText = buttonText; // 버튼 텍스트 초기화
    }

    // 코인 보상 여부가 있는 생성자
    public QuestItem(String title, int reward, boolean isCompleted, boolean isCoinReward, int iconResId, String buttonText) {
        this.title = title;
        this.reward = reward;
        this.isCompleted = isCompleted;
        this.isCoinReward = isCoinReward;
        this.iconResId = iconResId; // 아이콘 리소스 ID 초기화
        this.buttonText = buttonText; // 버튼 텍스트 초기화
        this.description = null; // 필요시 null
    }


    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getReward() { return reward; }
    public boolean isCompleted() { return isCompleted; }
    public boolean isCoinReward() { return isCoinReward; }
    public int getIconResId() { return iconResId; } // 아이콘 리소스 ID getter 추가
    public String getButtonText() { return buttonText; } // 버튼 텍스트 getter 추가

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}