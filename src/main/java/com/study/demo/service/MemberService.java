package com.study.demo.service;

import com.study.demo.dto.auth.MemberLoginRequest;
import com.study.demo.dto.member.MemberInfoDTO;
import com.study.demo.dto.member.MemberRequestDTO;
import com.study.demo.dto.member.MemberResponseDTO;
import com.study.demo.dto.member.UpdateMemberRequestDTO;
import com.study.demo.entity.Member;
import com.study.demo.error.exception.DuplicateAccountException;
import com.study.demo.error.exception.DuplicateEmailException;
import com.study.demo.error.exception.UnauthorizedException;
import com.study.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    public Long memberSignin(MemberLoginRequest request) throws UnauthorizedException {
        Long result = memberRepository.findByAccountAndPassword(request.getAccount(), request.getPassword());
        if(result != 1){
            memberLoginException();
        }
        return result;
    }

    public MemberResponseDTO saveMember(MemberRequestDTO requestDTO) throws Exception {
        memberSignUpValidation(requestDTO);
        Member member = Member.createMember(
                requestDTO.getName(),
                requestDTO.getBirthDate(),
                requestDTO.getGender(),
                requestDTO.getAccount(),
                requestDTO.getPassword(),
                requestDTO.getEmail()
        );
        Member saveMember = memberRepository.save(member);
        MemberResponseDTO responseDTO =
                new MemberResponseDTO(saveMember.getAccount(), saveMember.getEmail(), "회원가입에 성공하였습니다.");
        return responseDTO;
    }

    public Member findByAccount(String account) {
        return memberRepository.findByAccount(account);
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
    public MemberResponseDTO updateMember(UpdateMemberRequestDTO requestDTO) {
        return null;
    }

    public MemberInfoDTO getMember(String account) {
        return null;
    }

    public void memberSignUpValidation(MemberRequestDTO requestDTO) throws Exception {
        checkMemberSaveInvalidParameter(requestDTO);
        accountDuplicateCheck(requestDTO.getAccount());
        passwordValidateCheck(requestDTO.getPassword());
        emailDuplicateCheck(requestDTO.getEmail());
    }

    public void checkMemberSaveInvalidParameter(MemberRequestDTO requestDTO) throws ValidationException {
        if (requestDTO.getAccount() == null || requestDTO.getAccount().isEmpty()) {
            throw new ValidationException("필수 가입 항목 [account] 입력되지 않았습니다");
        }
        if (requestDTO.getPassword() == null || requestDTO.getPassword().isEmpty()) {
            throw new ValidationException("필수 가입 항목 [password] 입력되지 않았습니다");
        }
        if (requestDTO.getName() == null || requestDTO.getName().isEmpty()) {
            throw new ValidationException("필수 가입 항목 [name] 입력되지 않았습니다");
        }
        if (requestDTO.getBirthDate() == null) {
            throw new ValidationException("필수 가입 항목 [birthdate] 입력되지 않았습니다");
        }
        if (requestDTO.getEmail() == null || requestDTO.getEmail().isEmpty()) {
            throw new ValidationException("필수 가입 항목 [email] 입력되지 않았습니다");
        }
        if (requestDTO.getGender() == null) {
            throw new ValidationException("필수 가입 항목 [gender] 입력되지 않았습니다");
        }
    }

    public void passwordValidateCheck(String password) throws ValidationException {
        String pattern = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{7,}$";
        if (!password.matches(pattern)) {
            throw new ValidationException("비밀번호는 [문자+숫자+특수문자, 7자리 이상]으로 입력해주세요.");
        }
    }

    public void accountDuplicateCheck(String account) throws DuplicateAccountException {
        Member member = memberRepository.findByAccount(account);
        if (member != null) {
            throw new DuplicateAccountException("중복된 계정 입니다.");
        }
    }

    public void emailDuplicateCheck(String email) throws DuplicateEmailException {
        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            throw new DuplicateEmailException("중복된 이메일 입니다.");
        }
    }

    public void memberLoginException() throws UnauthorizedException {
        throw new UnauthorizedException("로그인에 실패하였습니다. 아이디와 비밀번호를 확인해주세요.");
    }

}
