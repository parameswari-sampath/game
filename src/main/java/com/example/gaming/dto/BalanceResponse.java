package com.example.gaming.dto;

public class BalanceResponse {

    private Long memberId;
    private double previousBalance;
    private double amount;
    private double newBalance;
    private String message;
    private String transactionType;

    public BalanceResponse() {}

    public BalanceResponse(Long memberId, double previousBalance, double amount, double newBalance, String message, String transactionType) {
        this.memberId = memberId;
        this.previousBalance = previousBalance;
        this.amount = amount;
        this.newBalance = newBalance;
        this.message = message;
        this.transactionType = transactionType;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public double getPreviousBalance() {
        return previousBalance;
    }

    public void setPreviousBalance(double previousBalance) {
        this.previousBalance = previousBalance;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getNewBalance() {
        return newBalance;
    }

    public void setNewBalance(double newBalance) {
        this.newBalance = newBalance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}