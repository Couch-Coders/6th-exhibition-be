package couch.exhibition.service;

import couch.exhibition.entity.Member;
import couch.exhibition.exception.CustomException;
import couch.exhibition.exception.ErrorCode;
import couch.exhibition.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER)); // optional
    }

    //회원 등록
    public Member register(String memberName, String nickname, String id) {
        Member registeredMember = Member.builder()
                .memberName(memberName)
                .nickname(nickname)
                .id(id)
                .build();

        memberRepository.save(registeredMember);

        return registeredMember;
    }

    //닉네임 수정
    public void editNickname(Member member, String nickname) {
        member.editNickname(nickname);
    }

    //회원 탈퇴
    public void deleteMember(String username) {
        Member member = memberRepository.findById(username)
                .orElseThrow(() -> new CustomException(ErrorCode.DELETED_USER));
        memberRepository.delete(member); //entity 직접 제거?
    }
}
