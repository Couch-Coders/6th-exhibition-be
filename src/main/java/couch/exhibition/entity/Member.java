package couch.exhibition.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(length = 50, name = "member_name", nullable = false)
    private String memberName;

    @Column(length = 50)
    private String nickname;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Likes> memberLikes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> memberReviews = new ArrayList<>();

    @Builder
    public Member(String memberName, String nickname) {
        this.memberName = memberName;
        this.nickname = nickname;
    }
}
