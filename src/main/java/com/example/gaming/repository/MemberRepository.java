package com.example.gaming.repository;

import com.example.gaming.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByCardId(String cardId);
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);

    @Query("SELECT COUNT(m) FROM Member m WHERE m.createdAt >= :startOfDay AND m.createdAt < :endOfDay")
    int countMembersCreatedToday(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);

    @Query("SELECT COUNT(DISTINCT gs.member) FROM GameSession gs WHERE gs.createdAt >= :startOfDay AND gs.createdAt < :endOfDay")
    int countActiveMembersToday(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);
}