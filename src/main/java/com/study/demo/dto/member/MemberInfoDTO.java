package com.study.demo.dto.member;

import com.study.demo.entity.Gender;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class MemberInfoDTO {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private Gender gender;
    private String account;
    private String email;

    public MemberInfoDTO() {
    }

    @QueryProjection
    public MemberInfoDTO(Long id, String name, LocalDate birthDate, Gender gender,
                         String account, String email) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.account = account;
        this.email = email;
    }

}
