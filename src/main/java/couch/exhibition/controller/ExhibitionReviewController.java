package couch.exhibition.controller;

import couch.exhibition.dto.ReviewRequestDTO;
import couch.exhibition.dto.ReviewResponseDTO;
import couch.exhibition.entity.Exhibition;
import couch.exhibition.entity.Member;
import couch.exhibition.repository.ExhibitionRepository;
import couch.exhibition.service.ExhibitionReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/exhibitions/{exhibitionId}/reviews")
public class ExhibitionReviewController {

    private final ExhibitionRepository exhibitionRepository;
    private final ExhibitionReviewService exhibitionReviewService;

    public ExhibitionReviewController(ExhibitionRepository exhibitionRepository,
                                      ExhibitionReviewService exhibitionReviewService) {
        this.exhibitionRepository = exhibitionRepository;
        this.exhibitionReviewService = exhibitionReviewService;
    }

    @GetMapping("") // exhibition id에 해당하는 리뷰 조회
    public Page<ReviewResponseDTO> viewExhibitionReviews(@PathVariable("exhibitionId") Long exhibitionId,
                                                         @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Exhibition exhibition = exhibitionRepository.getById(exhibitionId);
        return exhibitionReviewService.getExhibitionReviewList(exhibition, pageable).map(review -> new ReviewResponseDTO(review));
    }

    @PostMapping("") // 리뷰 작성
    public void createExhibitionReview(@PathVariable("exhibitionId") Long exhibitionId,
                                       @RequestBody ReviewRequestDTO createExhibitionReviewDTO,
                                       Authentication authentication) {
        log.info(String.valueOf(authentication));
        Member member = (Member) authentication.getPrincipal();
        log.info(String.valueOf(member));
        exhibitionReviewService.postReview(member, exhibitionId, createExhibitionReviewDTO);
    }

    @PatchMapping("/{reviewId}") // 리뷰 수정
    public void updateExhibitionReview(@PathVariable("exhibitionId") Long exhibitionId,
                                       @PathVariable("reviewId") Long reviewId,
                                       @RequestBody ReviewRequestDTO updateExhibitionReviewDTO,
                                       Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        exhibitionReviewService.updateReview(member, exhibitionId, reviewId, updateExhibitionReviewDTO);
    }

    @DeleteMapping("/{reviewId}") // 리뷰 삭제
    public void deleteExhibitionReview(@PathVariable("exhibitionId") Long exhibitionId,
                                       @PathVariable("reviewId") Long reviewId,
                                       Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        exhibitionReviewService.deleteReview(member, exhibitionId, reviewId);
    }
}



