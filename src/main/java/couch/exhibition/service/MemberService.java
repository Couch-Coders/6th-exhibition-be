package couch.exhibition.service;

import couch.exhibition.dto.UpdatedMemberDTO;
import couch.exhibition.entity.Member;
import couch.exhibition.exception.CustomException;
import couch.exhibition.exception.ErrorCode;
import couch.exhibition.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return memberRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
    }

    //회원 등록
    @Transactional
    public Member register(String memberName, String nickname, String id) {

        if (judgeIsDuplicatedNickname(nickname)) {
            throw new CustomException(ErrorCode.EXIST_NICKNAME);
        } else {
            Member registeredMember = Member.builder()
                    .memberName(memberName)
                    .nickname(nickname)
                    .id(id)
                    .build();

            memberRepository.save(registeredMember);

            return registeredMember;
        }
    }

    //닉네임 수정
    @Transactional
    public void editNickname(String id, UpdatedMemberDTO updatedMemberDTO) {
        Optional<Member> member = memberRepository.findById(id);

        if(memberRepository.findByNickname(updatedMemberDTO.getNickname()).isEmpty()) {


            Member updatedMember = Member.builder()
                    .memberName(updatedMemberDTO.getMemberName())
                    .nickname(updatedMemberDTO.getNickname())
                    .id(updatedMemberDTO.getId())
                    .build();

            // Optional의 .get() function을 이용, memberRepository에 있는 member 객체 가져옴.
            member.get().updateMember(updatedMember);

        } else {

            throw new CustomException(ErrorCode.EXIST_NICKNAME);
        }
    }

    //회원 탈퇴
    @Transactional
    public void deleteMember(String id) {
        memberRepository.delete(memberRepository.getById(id));
    }
    private boolean judgeIsDuplicatedNickname(String nickname) {
        return memberRepository.findByNickname(nickname).isPresent();
    }

}
