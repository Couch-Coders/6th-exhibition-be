package couch.exhibition.service;

import couch.exhibition.entity.Member;
import couch.exhibition.exception.CustomException;
import couch.exhibition.exception.ErrorCode;
import couch.exhibition.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findById(username); // optional
        return member.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    //회원 등록
    public Member register(String memberName, String nickname,String id) { // uid?
        Member registeredMember = Member.builder()
                .memberName(memberName)
                .nickname(nickname)
                .id(id)
                .build();

        memberRepository.save(registeredMember);

        return registeredMember;
    }

    //닉네임 수정
    public void editNickname(String username, String nickname) {
        Member member = memberRepository.findById(username)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        member.editNickname(nickname);
    }

    //회원 탈퇴
    public void deleteMember(String username) {
        Member member = memberRepository.findById(username)
                .orElseThrow(() -> new CustomException(ErrorCode.DELETED_USER));
        memberRepository.delete(member); //entity 직접 제거?
    }
}
