package couch.exhibition.entity;

import com.google.firebase.database.annotations.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
public class Member implements UserDetails {

    @Id
    @Column(name = "member_id")
    private String id;

    @Column(name = "member_name")
    @NotNull
    private String memberName;

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

    public Member() {
        throw new RuntimeException("Member class는 기본 생성자를 지원하지 않습니다.");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void editNickname(String nickname) {
        this.nickname = nickname;
    }
}
