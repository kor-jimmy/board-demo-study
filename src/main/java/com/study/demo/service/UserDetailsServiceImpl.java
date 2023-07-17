package com.study.demo.service;

import com.study.demo.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberService memberService;

    @Override
    public Member loadUserByUsername(String account) {
        return memberService.findByAccount(account);
    }
}
