package com.example.gaming.service;

import com.example.gaming.dto.*;
import com.example.gaming.entity.Admin;
import com.example.gaming.repository.AdminRepository;
import com.example.gaming.repository.MemberRepository;
import com.example.gaming.repository.TransactionRepository;
import com.example.gaming.repository.GameSessionRepository;
import com.example.gaming.entity.Transaction.TransactionType;
import com.example.gaming.entity.GameSession.SessionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private GameSessionRepository gameSessionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public AdminLoginResponse login(AdminLoginRequest request) {
        Optional<Admin> adminOpt = adminRepository.findByUsername(request.getUsername());

        if (adminOpt.isPresent() && passwordEncoder.matches(request.getPassword(), adminOpt.get().getPasswordHash())) {
            Admin admin = adminOpt.get();
            String token = jwtService.generateToken(admin.getUsername());

            return new AdminLoginResponse(token, "Login successful", admin.getUsername());
        }

        throw new com.example.gaming.exception.InvalidCredentialsException("Invalid username or password");
    }

    public DashboardResponse getDashboard() {
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        int totalMembers = (int) memberRepository.count();
        int activeToday = memberRepository.countActiveMembersToday(startOfDay, endOfDay);

        BigDecimal dailyIncomeAmount = gameSessionRepository.sumAmountByDateRange(startOfDay, endOfDay);
        double dailyIncome = dailyIncomeAmount != null ? dailyIncomeAmount.doubleValue() : 0.0;

        int totalActiveSessions = gameSessionRepository.countByStatus(SessionStatus.ACTIVE);

        BigDecimal rechargeAmountToday = transactionRepository.sumAmountByTypeAndDateRange(
            TransactionType.RECHARGE, startOfDay, endOfDay);
        double totalRechargeToday = rechargeAmountToday != null ? rechargeAmountToday.doubleValue() : 0.0;

        return new DashboardResponse(totalMembers, activeToday, dailyIncome, totalActiveSessions, totalRechargeToday);
    }

    public DailyIncomeResponse getDailyIncome(LocalDate date) {
        LocalDateTime startOfDay = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(date, LocalTime.MAX);

        BigDecimal totalIncomeAmount = gameSessionRepository.sumAmountByDateRange(startOfDay, endOfDay);
        double totalIncome = totalIncomeAmount != null ? totalIncomeAmount.doubleValue() : 0.0;

        int sessionCount = gameSessionRepository.countSessionsByDateRange(startOfDay, endOfDay);

        BigDecimal rechargeAmountBD = transactionRepository.sumAmountByTypeAndDateRange(
            TransactionType.RECHARGE, startOfDay, endOfDay);
        double rechargeAmount = rechargeAmountBD != null ? rechargeAmountBD.doubleValue() : 0.0;

        BigDecimal deductionAmountBD = transactionRepository.sumAmountByTypeAndDateRange(
            TransactionType.DEDUCTION, startOfDay, endOfDay);
        double totalDeductions = deductionAmountBD != null ? deductionAmountBD.doubleValue() : 0.0;

        return new DailyIncomeResponse(date, totalIncome, sessionCount, rechargeAmount, totalDeductions);
    }

    public void createDefaultAdmin() {
        if (!adminRepository.existsByUsername("admin")) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPasswordHash(passwordEncoder.encode("admin123"));
            adminRepository.save(admin);
        }
    }
}