package couch.exhibition.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

import couch.exhibition.dto.LikesDTO;
import couch.exhibition.dto.MemberDto;
import couch.exhibition.dto.ReviewResponseDTO;
import couch.exhibition.dto.UpdatedMemberDTO;
import couch.exhibition.entity.Exhibition;
import couch.exhibition.entity.Likes;
import couch.exhibition.entity.Member;
import couch.exhibition.exception.CustomException;
import couch.exhibition.exception.ErrorCode;
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

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/members")
public class MemberController {

    private final FirebaseAuth firebaseAuth;
    private final MemberService memberService;
    private final ExhibitionReviewService exhibitionReviewService;
    private final LikesService likesService;

    public MemberController(FirebaseAuth firebaseAuth, MemberService memberService,
                            ExhibitionReviewService exhibitionReviewService, LikesService likesService) {
        this.firebaseAuth = firebaseAuth;
        this.memberService = memberService;
        this.exhibitionReviewService = exhibitionReviewService;
        this.likesService = likesService;
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
                decodedToken.getName(), decodedToken.getEmail(), decodedToken.getUid());
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
        memberService.deleteMember(member.getId()); // 엔티티 직접 제거?
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
    public Page<LikesDTO> listLikeExhibition(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, Authentication authentication) {
            Member member = ((Member) authentication.getPrincipal());
            return likesService.listLikeExhibition(member, pageable).map( likes-> new LikesDTO(likes));
    }

    //내가 좋아한 전시 3개 미리보기
    @GetMapping("me/likes3")
    public Page<LikesDTO> listLike3Exhibition(@PageableDefault(direction = Sort.Direction.DESC, size = 3) Pageable pageable, Authentication authentication) {
        Member member = ((Member) authentication.getPrincipal());
        return likesService.listLikeExhibition(member, pageable).map( likes-> new LikesDTO(likes));
    }


    //전시 좋아요 or 좋아요 취소
//    @DeleteMapping("me/likes/{likeId}")
//    public void deleteLike(@PathVariable Long likeId, Authentication authentication) {
//        Member member = ((Member) authentication.getPrincipal());
//        Likes likes = likesService.findById(likeId).orElseThrow(() -> new CustomException(ErrorCode.EXIST_LIKED_DELETE_EXHIBITION));
//        likesService.deleteLike(likes, member);
//    }

}
