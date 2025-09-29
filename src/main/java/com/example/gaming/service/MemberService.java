package com.example.gaming.service;

import com.example.gaming.dto.*;
import com.example.gaming.entity.Member;
import com.example.gaming.entity.Transaction;
import com.example.gaming.entity.Transaction.TransactionType;
import com.example.gaming.repository.MemberRepository;
import com.example.gaming.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public MemberResponse createMember(MemberRequest request) {
        if (memberRepository.existsByPhone(request.getPhone())) {
            throw new com.example.gaming.exception.DuplicateResourceException("Phone number already exists");
        }

        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new com.example.gaming.exception.DuplicateResourceException("Email already exists");
        }

        Member member = new Member();
        member.setName(request.getName());
        member.setPhone(request.getPhone());
        member.setEmail(request.getEmail());

        member = memberRepository.save(member);

        return convertToResponse(member);
    }

    public List<MemberResponse> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public MemberResponse getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new com.example.gaming.exception.ResourceNotFoundException("Member not found with id: " + id));
        return convertToResponse(member);
    }

    @Transactional
    public BalanceResponse rechargeMember(Long memberId, RechargeRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new com.example.gaming.exception.ResourceNotFoundException("Member not found with id: " + memberId));

        BigDecimal previousBalance = member.getBalance();
        BigDecimal rechargeAmount = BigDecimal.valueOf(request.getAmount());
        BigDecimal newBalance = previousBalance.add(rechargeAmount);

        member.setBalance(newBalance);
        memberRepository.save(member);

        Transaction transaction = new Transaction();
        transaction.setMember(member);
        transaction.setType(TransactionType.RECHARGE);
        transaction.setAmount(rechargeAmount);
        transaction.setBalanceAfter(newBalance);
        transaction.setDescription(request.getDescription() != null ? request.getDescription() : "Balance recharge");
        transactionRepository.save(transaction);

        BalanceResponse response = new BalanceResponse();
        response.setMemberId(memberId);
        response.setPreviousBalance(previousBalance.doubleValue());
        response.setAmount(request.getAmount());
        response.setNewBalance(newBalance.doubleValue());
        response.setMessage("Recharge successful");
        response.setTransactionType("RECHARGE");

        return response;
    }

    @Transactional
    public BalanceResponse deductFromMember(Long memberId, RechargeRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new com.example.gaming.exception.ResourceNotFoundException("Member not found with id: " + memberId));

        BigDecimal currentBalance = member.getBalance();
        BigDecimal deductionAmount = BigDecimal.valueOf(request.getAmount());

        if (currentBalance.compareTo(deductionAmount) < 0) {
            throw new com.example.gaming.exception.InsufficientBalanceException("Insufficient balance");
        }

        BigDecimal newBalance = currentBalance.subtract(deductionAmount);
        member.setBalance(newBalance);
        memberRepository.save(member);

        Transaction transaction = new Transaction();
        transaction.setMember(member);
        transaction.setType(TransactionType.DEDUCTION);
        transaction.setAmount(deductionAmount);
        transaction.setBalanceAfter(newBalance);
        transaction.setDescription(request.getDescription() != null ? request.getDescription() : "Game payment");
        transactionRepository.save(transaction);

        BalanceResponse response = new BalanceResponse();
        response.setMemberId(memberId);
        response.setPreviousBalance(currentBalance.doubleValue());
        response.setAmount(request.getAmount());
        response.setNewBalance(newBalance.doubleValue());
        response.setMessage("Amount deducted successfully");
        response.setTransactionType("DEDUCTION");

        return response;
    }

    public BalanceResponse getMemberBalance(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new com.example.gaming.exception.ResourceNotFoundException("Member not found with id: " + memberId));

        BalanceResponse response = new BalanceResponse();
        response.setMemberId(memberId);
        response.setNewBalance(member.getBalance().doubleValue());
        response.setMessage("Current balance retrieved");

        return response;
    }

    public void deleteMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new com.example.gaming.exception.ResourceNotFoundException("Member not found with id: " + memberId);
        }
        memberRepository.deleteById(memberId);
    }

    private MemberResponse convertToResponse(Member member) {
        MemberResponse response = new MemberResponse();
        response.setId(member.getId());
        response.setName(member.getName());
        response.setPhone(member.getPhone());
        response.setEmail(member.getEmail());
        response.setCardId(member.getCardId());
        response.setBalance(member.getBalance().doubleValue());
        response.setCreatedAt(member.getCreatedAt());
        response.setUpdatedAt(member.getUpdatedAt());
        return response;
    }
}