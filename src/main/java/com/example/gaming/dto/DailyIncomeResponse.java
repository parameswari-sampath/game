package com.example.gaming.dto;

import java.time.LocalDate;

public class DailyIncomeResponse {

    private LocalDate date;
    private double totalIncome;
    private int sessionCount;
    private double rechargeAmount;
    private double totalDeductions;

    public DailyIncomeResponse() {}

    public DailyIncomeResponse(LocalDate date, double totalIncome, int sessionCount, double rechargeAmount, double totalDeductions) {
        this.date = date;
        this.totalIncome = totalIncome;
        this.sessionCount = sessionCount;
        this.rechargeAmount = rechargeAmount;
        this.totalDeductions = totalDeductions;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public int getSessionCount() {
        return sessionCount;
    }

    public void setSessionCount(int sessionCount) {
        this.sessionCount = sessionCount;
    }

    public double getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(double rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public double getTotalDeductions() {
        return totalDeductions;
    }

    public void setTotalDeductions(double totalDeductions) {
        this.totalDeductions = totalDeductions;
    }
}