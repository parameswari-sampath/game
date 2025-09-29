package com.example.gaming.controller;

import com.example.gaming.dto.GameSessionRequest;
import com.example.gaming.dto.GameSessionResponse;
import com.example.gaming.service.GameSessionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private GameSessionService gameSessionService;

    @PostMapping("/sessions")
    public ResponseEntity<GameSessionResponse> createGameSession(@Valid @RequestBody GameSessionRequest request) {
        GameSessionResponse response = gameSessionService.createGameSession(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<GameSessionResponse>> getGameSessions(
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) Long memberId) {
        List<GameSessionResponse> sessions = gameSessionService.getGameSessions(date, memberId);
        return ResponseEntity.ok(sessions);
    }

    @PostMapping("/sessions/{sessionId}/end")
    public ResponseEntity<GameSessionResponse> endGameSession(@PathVariable Long sessionId) {
        GameSessionResponse response = gameSessionService.endGameSession(sessionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sessions/{sessionId}")
    public ResponseEntity<GameSessionResponse> getGameSession(@PathVariable Long sessionId) {
        GameSessionResponse response = gameSessionService.getGameSession(sessionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/machines")
    public ResponseEntity<List<String>> getAvailableMachines() {
        List<String> machines = gameSessionService.getAvailableMachines();
        return ResponseEntity.ok(machines);
    }

    @GetMapping("/machines/{machineId}/status")
    public ResponseEntity<String> getMachineStatus(@PathVariable String machineId) {
        String status = gameSessionService.getMachineStatus(machineId);
        return ResponseEntity.ok(status);
    }
}