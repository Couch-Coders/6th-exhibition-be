package couch.exhibition.entity;

import couch.exhibition.dto.ReviewRequestDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(length = 1000)
    private String content;

    @Column(name = "registered_date")
    @CreatedDate
    private LocalDate registeredDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private LocalDate modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibition_id")
    private Exhibition exhibition;

    @Builder
    public Review(Long id, String content, LocalDate registeredDate, LocalDate modifiedDate,
                   Member member, Exhibition exhibition) {
        this.id = id;
        this.content = content;
        this.registeredDate = registeredDate;
        this.modifiedDate = modifiedDate;
        this.member = member;
        this.exhibition = exhibition;
    }

    public void updateReview(ReviewRequestDTO updateExhibitionReviewDTO) {
        this.content = updateExhibitionReviewDTO.getContent();
    }

}