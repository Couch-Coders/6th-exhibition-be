package couch.exhibition.controller;

import couch.exhibition.dto.LikesDTO;
import couch.exhibition.entity.Exhibition;
import couch.exhibition.entity.Member;
import couch.exhibition.exception.CustomException;
import couch.exhibition.exception.ErrorCode;
import couch.exhibition.service.ExhibitionService;
import couch.exhibition.service.LikesService;
import couch.exhibition.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/exhibitions/{exhibitionId}/likes")
public class LikesController {

    private final LikesService likesService;
    private final ExhibitionService exhibitionService;

    public LikesController(LikesService likesService, ExhibitionService exhibitionService) {
        this.likesService = likesService;
        this.exhibitionService = exhibitionService;
    }

    @PostMapping("")
    public void createLike(@PathVariable Long exhibitionId,
                           Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        likesService.createLike(member, exhibitionId);
    }

    @DeleteMapping("")
    public void deleteLike(@PathVariable Long exhibitionId,
                           Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        likesService.deleteLike(member, exhibitionId);
    }

    @GetMapping("")
    public int countLikes(@PathVariable Long exhibitionId){
        Exhibition exhibition = exhibitionService.findById(exhibitionId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EXHIBITION));
        //likesService.updateExhibitionLikeCnt(exhibition);
        return likesService.countLikes(exhibition);
    }

}
