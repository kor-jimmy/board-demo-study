package com.study.demo.repository;

import com.study.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    public Member findByAccount(String account);

    public Member findByEmail(String email);

    @Query("SELECT COUNT(*) FROM Member m WHERE m.account = :account AND m.password = :password")
    public Long findByAccountAndPassword(@Param("account") String account, @Param("password") String password);


}
