package couch.exhibition.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"exhibitionReviews", "exhibitionLikes"})
//@ToString(exclude = {"exhibitionReviews", "exhibitionLikes"})
public class Exhibition {

    @Id @GeneratedValue
    @Column(name = "exhibition_id")
    private Long id;

    private String title;

    private String place;

    private String placeAddr;

    private BigDecimal latitude;

    private BigDecimal longitude;

    @Column(name = "start_date")
    private Integer startDate;

    @Column(name = "end_date")
    private Integer endDate;

    @Column(name = "contact_link",length = 450)
    private String contactLink;

    @Column(name = "ticket_price")
    private String ticketPrice;

    @Column(name = "reservation_link", length = 450)
    private String reservationLink;

    @Column(name = "poster_url", length = 450)
    private String posterUrl;

    @Column(name = "like_count")
    private Integer likeCnt;

    @OneToMany(mappedBy = "exhibition", cascade = CascadeType.ALL)
    private List<Likes> exhibitionLikes = new ArrayList<>();

    @OneToMany(mappedBy = "exhibition", cascade = CascadeType.ALL)
    private List<Review> exhibitionReviews = new ArrayList<>();

    @Builder
    public Exhibition(String title, String place, String placeAddr,
                      BigDecimal latitude, BigDecimal longitude,
                      Integer startDate, Integer endDate,
                      String contactLink, String ticketPrice, String reservationLink,
                      String posterUrl, Integer likeCnt) {
        this.title = title;
        this.place = place;
        this.placeAddr = placeAddr;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contactLink = contactLink;
        this.ticketPrice = ticketPrice;
        this.reservationLink = reservationLink;
        this.posterUrl = posterUrl;
        this.likeCnt = likeCnt;
    }




//    public Exhibition() {
//        throw new RuntimeException("Exhibition class는 기본 생성자를 지원하지 않습니다.");
//    }

    public void setLikeCnt(Integer likeCnt) {
        this.likeCnt = likeCnt;
    }
}