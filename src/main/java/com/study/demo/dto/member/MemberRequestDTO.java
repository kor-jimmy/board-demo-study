package com.study.demo.dto.member;

import com.study.demo.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDTO {
    private String name;
    private LocalDate birthDate;
    private Gender gender;
    private String account;
    private String password;
    private String email;
}
