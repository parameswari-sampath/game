package com.example.gaming.service;

import com.example.gaming.dto.*;
import com.example.gaming.entity.GameSession;
import com.example.gaming.entity.Member;
import com.example.gaming.entity.GameSession.SessionStatus;
import com.example.gaming.repository.GameSessionRepository;
import com.example.gaming.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameSessionService {

    @Autowired
    private GameSessionRepository gameSessionRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Transactional
    public GameSessionResponse createGameSession(GameSessionRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + request.getMemberId()));

        if (member.getBalance().compareTo(BigDecimal.valueOf(request.getAmount())) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        RechargeRequest deductRequest = new RechargeRequest();
        deductRequest.setAmount(request.getAmount());
        deductRequest.setDescription("Game session - " + request.getGameType() + " on " + request.getMachineId());
        memberService.deductFromMember(request.getMemberId(), deductRequest);

        GameSession gameSession = new GameSession();
        gameSession.setMember(member);
        gameSession.setMachineId(request.getMachineId());
        gameSession.setGameType(request.getGameType());
        gameSession.setAmount(BigDecimal.valueOf(request.getAmount()));
        gameSession.setDurationMinutes(request.getDurationMinutes());
        gameSession.setStatus(SessionStatus.ACTIVE);

        gameSession = gameSessionRepository.save(gameSession);

        return convertToResponse(gameSession);
    }

    public List<GameSessionResponse> getGameSessions(LocalDate date, Long memberId) {
        List<GameSession> sessions;

        if (date != null) {
            LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endOfDay = LocalDateTime.of(date, LocalTime.MAX);
            sessions = gameSessionRepository.findSessionsByDateRange(startOfDay, endOfDay);
        } else if (memberId != null) {
            sessions = gameSessionRepository.findByMemberIdOrderByCreatedAtDesc(memberId);
        } else {
            sessions = gameSessionRepository.findAll();
        }

        return sessions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public GameSessionResponse endGameSession(Long sessionId) {
        GameSession gameSession = gameSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Game session not found with id: " + sessionId));

        if (gameSession.getStatus() != SessionStatus.ACTIVE) {
            throw new RuntimeException("Game session is not active");
        }

        gameSession.setStatus(SessionStatus.COMPLETED);
        gameSession.setEndTime(LocalDateTime.now());

        if (gameSession.getStartTime() != null && gameSession.getEndTime() != null) {
            long duration = java.time.Duration.between(gameSession.getStartTime(), gameSession.getEndTime()).toMinutes();
            gameSession.setDurationMinutes((int) duration);
        }

        gameSession = gameSessionRepository.save(gameSession);

        GameSessionResponse response = convertToResponse(gameSession);
        response.setMessage("Game session ended successfully");
        return response;
    }

    public GameSessionResponse getGameSession(Long sessionId) {
        GameSession gameSession = gameSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Game session not found with id: " + sessionId));

        return convertToResponse(gameSession);
    }

    public List<String> getAvailableMachines() {
        return List.of("MACHINE_001", "MACHINE_002", "MACHINE_003", "MACHINE_004", "MACHINE_005");
    }

    public String getMachineStatus(String machineId) {
        List<GameSession> activeSessions = gameSessionRepository.findByMachineIdAndStatus(machineId, SessionStatus.ACTIVE);
        return activeSessions.isEmpty() ? "AVAILABLE" : "OCCUPIED";
    }

    private GameSessionResponse convertToResponse(GameSession gameSession) {
        GameSessionResponse response = new GameSessionResponse();
        response.setId(gameSession.getId());
        response.setMemberId(gameSession.getMember().getId());
        response.setMachineId(gameSession.getMachineId());
        response.setGameType(gameSession.getGameType());
        response.setAmount(gameSession.getAmount().doubleValue());
        response.setDurationMinutes(gameSession.getDurationMinutes());
        response.setStatus(gameSession.getStatus().toString());
        response.setStartTime(gameSession.getStartTime());
        response.setEndTime(gameSession.getEndTime());
        response.setMessage("Game session details retrieved");
        return response;
    }
}