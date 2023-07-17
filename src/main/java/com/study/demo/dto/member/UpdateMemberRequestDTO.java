package com.study.demo.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMemberRequestDTO {
    private String account;
    private String password;
    private Integer minCareAge;
    private Integer maxCareAge;
    private String careRequestContent;
    private String selfIntroduction;
}
