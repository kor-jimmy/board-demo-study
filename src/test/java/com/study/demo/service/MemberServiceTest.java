package com.study.demo.service;

import com.study.demo.dto.member.MemberRequestDTO;
import com.study.demo.dto.member.MemberResponseDTO;
import com.study.demo.entity.Gender;
import com.study.demo.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberService memberService;


    @BeforeEach
    public void before() {
        Member member = Member.createMember("test", LocalDate.now(), Gender.MALE, "jintak.yoo",
                "12341234", "jintak.yoo@gmail.com");

        em.persist(member);
        em.flush();
        em.clear();
    }

    @Test
    public void saveMember() throws Exception{
        //given
        MemberRequestDTO requestDTO = new MemberRequestDTO();
        requestDTO.setName("태양인");
        requestDTO.setBirthDate(LocalDate.now());
        requestDTO.setGender(Gender.MALE);
        requestDTO.setAccount("test");
        requestDTO.setPassword("sun_in01234");
        requestDTO.setEmail("sun_in@gmail.com");
        //when
        MemberResponseDTO responseDTO = memberService.saveMember(requestDTO);
        //then
        Member findMember = memberService.findByAccount(requestDTO.getAccount());
        assertThat(requestDTO.getName()).isEqualTo(findMember.getName());
    }


    @Test
    public void updateMember() throws Exception{

    }

}