package com.study.demo.controller;

import com.study.demo.config.JwtConfig;
import com.study.demo.dto.auth.MemberLoginRequest;
import com.study.demo.dto.member.MemberInfoDTO;
import com.study.demo.dto.member.MemberRequestDTO;
import com.study.demo.dto.member.MemberResponseDTO;
import com.study.demo.dto.member.UpdateMemberRequestDTO;
import com.study.demo.entity.Member;
import com.study.demo.error.exception.UnauthorizedException;
import com.study.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtConfig jwtConfig;

    @GetMapping("/api/v1/hello")
    public String hello() {
        return "hello!!";
    }

    @PostMapping("/api/v1/signup")
    public ResponseEntity<?> saveMember(
            @RequestBody MemberRequestDTO memberSaveRequestDTO) throws Exception {

        MemberResponseDTO responseDTO = memberService.saveMember(memberSaveRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/api/v1/signin")
    public ResponseEntity<?> memberSignIn(@RequestBody MemberLoginRequest request,
                                          HttpServletResponse response) throws UnauthorizedException {
        Long result = memberService.memberSignin(request);
        Member member = memberService.findByAccount(request.getAccount());
        String token = jwtConfig.createToken(member.getAccount(), member.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", token);
        return ResponseEntity.ok().headers(headers).build();
    }

    @PatchMapping("/api/v1/member/{account}")
    public ResponseEntity<?> updateMember(@RequestBody UpdateMemberRequestDTO requestDTO) {
        MemberResponseDTO responseDTO = memberService.updateMember(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/api/v1/member/{account}")
    public ResponseEntity<?> getMember(@PathVariable("account") String account) {
        MemberInfoDTO memberInfoDTO = memberService.getMember(account);
        return ResponseEntity.ok(memberInfoDTO);
    }



}
