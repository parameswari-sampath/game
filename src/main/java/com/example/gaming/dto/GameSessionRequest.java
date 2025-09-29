package com.example.gaming.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GameSessionRequest {

    @NotNull(message = "Member ID is required")
    private Long memberId;

    @NotBlank(message = "Machine ID is required")
    private String machineId;

    @NotBlank(message = "Game type is required")
    private String gameType;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private double amount;

    private Integer durationMinutes;

    public GameSessionRequest() {}

    public GameSessionRequest(Long memberId, String machineId, String gameType, double amount, Integer durationMinutes) {
        this.memberId = memberId;
        this.machineId = machineId;
        this.gameType = gameType;
        this.amount = amount;
        this.durationMinutes = durationMinutes;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}