package couch.exhibition.entity;

import couch.exhibition.dto.ReviewRequestDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(
        name = "REVIEW_SEQ_GENERATOR",
        sequenceName = "REVIEW_SEQ",
        initialValue = 1, allocationSize = 1)
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REVIEW_SEQ_GENERATOR")
    @Column(name = "review_id")
    private Long id;

    @Column(length = 1000)
    private String content;

    @CreationTimestamp
    @Column(name = "registered_date_time")
    private LocalDateTime registeredDateTime = LocalDateTime.now();

    @UpdateTimestamp
    @Column(name = "modified_date_time")
    private LocalDateTime modifiedDateTime = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibition_id")
    private Exhibition exhibition;

    @Builder
    public Review(String content, Member member, Exhibition exhibition) {
        this.content = content;
        this.member = member;
        this.exhibition = exhibition;
    }

    public void updateReview(ReviewRequestDTO updateExhibitionReviewDTO) {
        this.content = updateExhibitionReviewDTO.getContent();
    }

}