package com.example.gaming.controller;

import com.example.gaming.dto.MemberRequest;
import com.example.gaming.dto.MemberResponse;
import com.example.gaming.dto.RechargeRequest;
import com.example.gaming.dto.BalanceResponse;
import com.example.gaming.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@Valid @RequestBody MemberRequest request) {
        MemberResponse response = memberService.createMember(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getAllMembers() {
        List<MemberResponse> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable Long memberId) {
        MemberResponse response = memberService.getMemberById(memberId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{memberId}/recharge")
    public ResponseEntity<BalanceResponse> rechargeMember(@PathVariable Long memberId, @Valid @RequestBody RechargeRequest request) {
        BalanceResponse response = memberService.rechargeMember(memberId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{memberId}/deduct")
    public ResponseEntity<BalanceResponse> deductFromMember(@PathVariable Long memberId, @Valid @RequestBody RechargeRequest request) {
        BalanceResponse response = memberService.deductFromMember(memberId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{memberId}/balance")
    public ResponseEntity<BalanceResponse> getMemberBalance(@PathVariable Long memberId) {
        BalanceResponse response = memberService.getMemberBalance(memberId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<String> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.ok("Member deleted successfully");
    }
}