package com.example.teamproject_roubithome;

public class Routine {
    private String title;
    private boolean isDone;

    public Routine(String title, boolean isDone) {
        this.title = title;
        this.isDone = isDone;
    }

    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}