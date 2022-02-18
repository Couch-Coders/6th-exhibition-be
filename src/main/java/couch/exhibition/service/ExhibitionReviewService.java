package couch.exhibition.service;

import couch.exhibition.dto.ReviewRequestDTO;
import couch.exhibition.dto.ReviewPostResponseDTO;
import couch.exhibition.entity.Exhibition;
import couch.exhibition.entity.Member;
import couch.exhibition.entity.Review;
import couch.exhibition.exception.CustomException;
import couch.exhibition.exception.ErrorCode;
import couch.exhibition.repository.ExhibitionRepository;
import couch.exhibition.repository.ExhibitionReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExhibitionReviewService {

    private final ExhibitionRepository exhibitionRepository;
    private final ExhibitionReviewRepository exhibitionReviewRepository;

    public ExhibitionReviewService(ExhibitionRepository exhibitionRepository,
                                   ExhibitionReviewRepository exhibitionReviewRepository) {
        this.exhibitionRepository = exhibitionRepository;
        this.exhibitionReviewRepository = exhibitionReviewRepository;
    }

    @Transactional
    public ReviewPostResponseDTO postReview(Member member, Long exhibitionId, ReviewRequestDTO createExhibitionReviewDTO) {

        judgeNotFoundExhibition(exhibitionId);

        Review review = Review.builder()
                .content(createExhibitionReviewDTO.getContent())
                .member(member)
                .exhibition(exhibitionRepository.getById(exhibitionId))
                .build();

        exhibitionReviewRepository.save(review);

        return new ReviewPostResponseDTO(review);
    }

    @Transactional
    public void updateReview(Member member, Long exhibitionId, Long reviewId, ReviewRequestDTO updateExhibitionReviewDTO) {

        judgeNotFoundExhibition(exhibitionId);

        Review getReview = judgeNotFoundReview(reviewId);

        judgeForbiddenUser(member, getReview);

        getReview.updateReview(updateExhibitionReviewDTO);
    }

    @Transactional
    public void deleteReview(Member member, Long exhibitionId, Long reviewId) {

        judgeNotFoundExhibition(exhibitionId);

        Review getReview = judgeNotFoundReview(reviewId);

        judgeForbiddenUser(member, getReview);

        exhibitionReviewRepository.delete(getReview);
    }

    private void judgeForbiddenUser(Member member, Review getReview) {
        if (!getReview.getMember().getId().equals(member.getId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_USER);
        }
    }

    private Review judgeNotFoundReview(Long reviewId) {
        return exhibitionReviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REVIEW));
    }

    private void judgeNotFoundExhibition(Long exhibitionId) {
        if (exhibitionRepository.findById(exhibitionId).isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_EXHIBITION);
        }
    }

    public Page<Review> getExhibitionReviewList(Exhibition exhibition, Pageable pageable) {
        return exhibitionReviewRepository.findByExhibition(exhibition, pageable);
    }
}



