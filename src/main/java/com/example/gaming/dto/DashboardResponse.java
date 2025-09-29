package com.example.gaming.dto;

public class DashboardResponse {

    private int totalMembers;
    private int activeToday;
    private double dailyIncome;
    private int totalActiveSessions;
    private double totalRechargeToday;

    public DashboardResponse() {}

    public DashboardResponse(int totalMembers, int activeToday, double dailyIncome, int totalActiveSessions, double totalRechargeToday) {
        this.totalMembers = totalMembers;
        this.activeToday = activeToday;
        this.dailyIncome = dailyIncome;
        this.totalActiveSessions = totalActiveSessions;
        this.totalRechargeToday = totalRechargeToday;
    }

    public int getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(int totalMembers) {
        this.totalMembers = totalMembers;
    }

    public int getActiveToday() {
        return activeToday;
    }

    public void setActiveToday(int activeToday) {
        this.activeToday = activeToday;
    }

    public double getDailyIncome() {
        return dailyIncome;
    }

    public void setDailyIncome(double dailyIncome) {
        this.dailyIncome = dailyIncome;
    }

    public int getTotalActiveSessions() {
        return totalActiveSessions;
    }

    public void setTotalActiveSessions(int totalActiveSessions) {
        this.totalActiveSessions = totalActiveSessions;
    }

    public double getTotalRechargeToday() {
        return totalRechargeToday;
    }

    public void setTotalRechargeToday(double totalRechargeToday) {
        this.totalRechargeToday = totalRechargeToday;
    }
}