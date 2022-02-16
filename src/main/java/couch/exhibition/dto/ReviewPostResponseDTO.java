package couch.exhibition.dto;

import couch.exhibition.entity.Review;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewPostResponseDTO {

    private Long reviewId;
    private String content;
    private LocalDate registeredDate;
    private String memberId;
    private Long exhibitionId;

    public ReviewPostResponseDTO(Review review) {
        this.reviewId = review.getId();
        this.content = review.getContent();
        this.registeredDate = review.getRegisteredDate();
        this.memberId = review.getMember().getId();
        this.exhibitionId = review.getExhibition().getId();
    }
}


