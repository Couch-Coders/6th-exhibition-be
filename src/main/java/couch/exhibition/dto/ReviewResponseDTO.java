package couch.exhibition.dto;

import couch.exhibition.entity.Exhibition;
import couch.exhibition.entity.Review;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewResponseDTO {

    private Long reviewId;
    private String content;
    private LocalDate registeredDate;
    private LocalDate modifiedDate;
    private String memberId;
    private Long exhibitionId;

    public ReviewResponseDTO(Review review) {
        this.reviewId = review.getId();
        this.content = review.getContent();
        this.registeredDate = review.getRegisteredDate();
        this.modifiedDate = review.getModifiedDate();
        this.memberId = review.getMember().getId();
        this.exhibitionId = review.getExhibition().getId();
    }
}

