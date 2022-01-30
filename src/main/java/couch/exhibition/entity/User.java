package couch.exhibition.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 50)
    private String nickname;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Like> userLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> userReviews = new ArrayList<>();

    @Builder
    private User(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }
}
