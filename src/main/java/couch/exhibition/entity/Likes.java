package couch.exhibition.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(
        name = "LIKE_SEQ_GENERATOR",
        sequenceName = "LIKE_SEQ",
        initialValue = 1, allocationSize = 1)
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIKE_SEQ_GENERATOR")
    @Column(name = "likes_id")
    private Long id;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exhibition_id")
    private Exhibition exhibition;

    @Builder
    public Likes(Member member, Exhibition exhibition) {
        this.member = member;
        this.exhibition = exhibition;
    }
}