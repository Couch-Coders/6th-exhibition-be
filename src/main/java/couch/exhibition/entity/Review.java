package couch.exhibition.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
public class Review {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @Column(length = 1000)
    private String content;

    @Column(name = "registered_date")
    private LocalDate registeredDate;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibition_id")
    private Exhibition exhibition;

    @Builder
    private Review(String content, LocalDate registeredDate, LocalDate modifiedDate,
                   Member member, Exhibition exhibition) {
        this.content = content;
        this.registeredDate = registeredDate;
        this.modifiedDate = modifiedDate;
        this.member = member;
        this.exhibition = exhibition;
    }
}