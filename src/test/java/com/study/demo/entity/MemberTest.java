package com.study.demo.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @Autowired
    EntityManager em;

    @BeforeEach
    public void before() {
        Member member = Member.createMember("test", LocalDate.now(), Gender.MALE, "jintak.yoo",
                "12341234", "jintak.yoo@gmail.com");
        em.persist(member);
    }

    @Test
    public void createMember() throws Exception {
        //given
        Member member = Member.createMember("test", LocalDate.now(), Gender.MALE, "jintak.yoo",
                "12341234", "jintak.yoo@gmail.com");
        //when
        em.persist(member);
        //then
        Member findMember = em.createQuery("select m from Member m where m.id = :id", Member.class).
                setParameter("id", member.getId()).getSingleResult();
        assertThat(member).isEqualTo(findMember);
    }


}