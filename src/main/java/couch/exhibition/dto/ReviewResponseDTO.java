package couch.exhibition.dto;

import couch.exhibition.entity.Review;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponseDTO {

    private Long reviewId;
    private String content;
    private LocalDateTime registeredDateTime;
    private LocalDateTime modifiedDateTime;
    private String memberId;
    private Long exhibitionId;

    public ReviewResponseDTO(Review review) {
        this.reviewId = review.getId();
        this.content = review.getContent();
        this.registeredDateTime = review.getRegisteredDateTime();
        this.modifiedDateTime = review.getModifiedDateTime();
        this.memberId = review.getMember().getNickname();
        this.exhibitionId = review.getExhibition().getId();
    }
}

