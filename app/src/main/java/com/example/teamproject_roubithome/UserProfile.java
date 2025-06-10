package com.example.teamproject_roubithome;

public class UserProfile {
    private static int carrots = 0;

    public static int getCarrots() {
        return carrots;
    }

    public static void setCarrots(int newCarrots) {
        carrots = newCarrots;
    }
}