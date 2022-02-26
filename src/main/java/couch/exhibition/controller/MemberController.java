package couch.exhibition.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import couch.exhibition.dto.CreateMemberDTO;
import couch.exhibition.dto.LikesDTO;
import couch.exhibition.dto.MemberDto;
import couch.exhibition.dto.ReviewResponseDTO;
import couch.exhibition.dto.UpdatedMemberDTO;
import couch.exhibition.entity.Member;
import couch.exhibition.repository.MemberRepository;
import couch.exhibition.service.ExhibitionReviewService;
import couch.exhibition.service.LikesService;
import couch.exhibition.service.MemberService;
import couch.exhibition.util.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    private final MemberRepository memberRepository;
    private final ExhibitionReviewService exhibitionReviewService;
    private final LikesService likesService;

    public MemberController(FirebaseAuth firebaseAuth, MemberService memberService, MemberRepository memberRepository, ExhibitionReviewService exhibitionReviewService, LikesService likesService) {
        this.firebaseAuth = firebaseAuth;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.exhibitionReviewService = exhibitionReviewService;
        this.likesService = likesService;
    }

    //회원가입
    @PostMapping("")
    public MemberDto register(@RequestHeader("Authorization") String authorization,
                              @RequestBody CreateMemberDTO createMemberDTO) {

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
                decodedToken.getName(), createMemberDTO.getNickname(), decodedToken.getUid());

        return new MemberDto(registeredMember);
    }

    @GetMapping("/me")
    public MemberDto login(Authentication authentication) {
        Member member = ((Member) authentication.getPrincipal());
        return new MemberDto(member);
    }

    //닉네임 수정
    @PatchMapping("/me")
    public void editNickname(Authentication authentication, @RequestBody UpdatedMemberDTO updatedMemberDTO) {
        Member member = ((Member) authentication.getPrincipal());

        memberService.editNickname(member.getId(), updatedMemberDTO);
    }

    //회원 탈퇴
    @DeleteMapping("/me")
    public void deleteRegisteredMember(Authentication authentication) {
        Member member = ((Member) authentication.getPrincipal());
        memberService.deleteMember(member.getId());
    }

    //나의 리뷰 조회
    @GetMapping("/me/reviews")
    public Page<ReviewResponseDTO> viewMyExhibitionReviews(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                                           Authentication authentication) {
        Member member = ((Member) authentication.getPrincipal());
        return exhibitionReviewService.getMyExhibitionReviewList(member, pageable).map(review -> new ReviewResponseDTO(review));
    }

    //내가 좋아한 전시 조회(더보기)
    @GetMapping("me/likes")
    public Page<LikesDTO> listLikeExhibition(@PageableDefault(sort = "exhibition.endDate",direction = Sort.Direction.ASC) Pageable pageable, Authentication authentication) {
            Member member = ((Member) authentication.getPrincipal());
            return likesService.listLikeExhibition(member, pageable).map( likes-> new LikesDTO(likes));
    }

    //내가 좋아한 전시 3개 미리보기
    @GetMapping("me/likes3")
    public Page<LikesDTO> listLike3Exhibition(@PageableDefault(sort = "exhibition.endDate", direction = Sort.Direction.ASC, size = 3) Pageable pageable, Authentication authentication) {
        Member member = ((Member) authentication.getPrincipal());
        return likesService.listLikeExhibition(member, pageable).map( likes-> new LikesDTO(likes));
    }
}
