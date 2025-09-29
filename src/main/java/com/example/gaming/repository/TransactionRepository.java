package com.example.gaming.repository;

import com.example.gaming.entity.Transaction;
import com.example.gaming.entity.Transaction.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByMemberIdOrderByCreatedAtDesc(Long memberId);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = :type AND t.createdAt >= :startOfDay AND t.createdAt < :endOfDay")
    BigDecimal sumAmountByTypeAndDateRange(@Param("type") TransactionType type,
                                          @Param("startOfDay") LocalDateTime startOfDay,
                                          @Param("endOfDay") LocalDateTime endOfDay);

    @Query("SELECT t FROM Transaction t WHERE t.createdAt >= :startOfDay AND t.createdAt < :endOfDay ORDER BY t.createdAt DESC")
    List<Transaction> findTransactionsByDateRange(@Param("startOfDay") LocalDateTime startOfDay,
                                                 @Param("endOfDay") LocalDateTime endOfDay);
}