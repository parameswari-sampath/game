package com.example.gaming.repository;

import com.example.gaming.entity.GameSession;
import com.example.gaming.entity.GameSession.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
    List<GameSession> findByMemberIdOrderByCreatedAtDesc(Long memberId);

    List<GameSession> findByMachineIdAndStatus(String machineId, SessionStatus status);

    @Query("SELECT gs FROM GameSession gs WHERE gs.createdAt >= :startOfDay AND gs.createdAt < :endOfDay ORDER BY gs.createdAt DESC")
    List<GameSession> findSessionsByDateRange(@Param("startOfDay") LocalDateTime startOfDay,
                                            @Param("endOfDay") LocalDateTime endOfDay);

    @Query("SELECT SUM(gs.amount) FROM GameSession gs WHERE gs.createdAt >= :startOfDay AND gs.createdAt < :endOfDay")
    BigDecimal sumAmountByDateRange(@Param("startOfDay") LocalDateTime startOfDay,
                                   @Param("endOfDay") LocalDateTime endOfDay);

    @Query("SELECT COUNT(gs) FROM GameSession gs WHERE gs.createdAt >= :startOfDay AND gs.createdAt < :endOfDay")
    int countSessionsByDateRange(@Param("startOfDay") LocalDateTime startOfDay,
                                @Param("endOfDay") LocalDateTime endOfDay);

    @Query("SELECT COUNT(gs) FROM GameSession gs WHERE gs.status = :status")
    int countByStatus(@Param("status") SessionStatus status);
}