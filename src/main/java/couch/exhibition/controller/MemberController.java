package couch.exhibition.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import couch.exhibition.dto.MemberDto;
import couch.exhibition.dto.RegisteredMemberDto;
import couch.exhibition.entity.Member;
import couch.exhibition.service.MemberService;
import couch.exhibition.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/members")
public class MemberController {

    private final FirebaseAuth firebaseAuth;
    private final MemberService memberService;

    public MemberController(FirebaseAuth firebaseAuth, MemberService memberService) {
        this.firebaseAuth = firebaseAuth;
        this.memberService = memberService;
    }

    //회원가입
    @PostMapping("")
    public MemberDto register(@RequestHeader("Authorization") String authorization) {

        //Token 획득
        FirebaseToken decodedToken;
        try {
            String token = RequestUtil.getAuthorizationToken(authorization);
            decodedToken = firebaseAuth.verifyIdToken(token);
        } catch (IllegalArgumentException | FirebaseAuthException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "{\"code\":\"INVALID_TOKEN\", \"message\":\"" + e.getMessage() + "\"}");
        }

        //등록
        Member registeredMember = memberService.register(
                decodedToken.getName(),decodedToken.getName(),decodedToken.getUid());
        return new MemberDto(registeredMember);
    }

    //로그인
    @GetMapping("/me")
    public MemberDto login(Authentication authentication) {
        Member member = ((Member) authentication.getPrincipal());
        return new MemberDto(member);
    }

    //닉네임 수정
    @PatchMapping("/me")
    public void editNickname(Authentication authentication, @RequestBody RegisteredMemberDto registeredMemberDto) {
        Member member = ((Member) authentication.getPrincipal());
        memberService.editNickname(member, registeredMemberDto.getNickname());
    }

    //회원 탈퇴
    @DeleteMapping("/me")
    public void deleteRegisteredMember(Authentication authentication) {
        Member member = ((Member) authentication.getPrincipal());
        memberService.deleteMember(member.getMemberName()); // 엔티티 직접 제거?
    }
}
