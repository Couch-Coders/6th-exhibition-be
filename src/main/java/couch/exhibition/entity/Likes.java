package couch.exhibition.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Likes {

    @Id @GeneratedValue
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibition_id")
    private Exhibition exhibition;

    @Builder
    public Likes(Member member, Exhibition exhibition) {
        this.member = member;
        this.exhibition = exhibition;
    }

//    public Likes() {
//        throw new RuntimeException("Likes class는 기본 생성자를 지원하지 않습니다.");
//    }
}