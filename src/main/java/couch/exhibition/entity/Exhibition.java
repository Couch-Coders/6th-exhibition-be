package couch.exhibition.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Exhibition {

    @Id @GeneratedValue
    @Column(name = "exhibition_id")
    private Long id;

    @Column(length = 100)
    private String title;

    @Column(length = 100)
    private String place;

    @Column(length = 50)
    private String city;

    @Column(length = 50)
    private String district;

    private BigDecimal latitude;
    private BigDecimal longitude;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "contact_link", length = 100)
    private String contactLink;

    @Column(name = "ticket_price", length = 50)
    private String ticketPrice;

    @Column(name = "reservation_link", length = 100)
    private String reservationLink;

    @Column(name = "poster_url", length = 100)
    private String posterUrl;

    private boolean progress;

    @Column(name = "like_count")
    private Integer likeCnt;

    @OneToMany(mappedBy = "exhibition", cascade = CascadeType.ALL)
    private List<Like> exhibitionLikes = new ArrayList<>();

    @OneToMany(mappedBy = "exhibition", cascade = CascadeType.ALL)
    private List<Review> exhibitionReviews = new ArrayList<>();

    @Builder  // 빌더 패턴
    private Exhibition(String title, String place, String city, String district,
                       BigDecimal latitude, BigDecimal longitude,
                       LocalDate startDate, LocalDate endDate,
                       String contactLink, String ticketPrice, String reservationLink,
                       String posterUrl, boolean progress, Integer likeCnt) {
        this.title = title;
        this.place = place;
        this.city = city;
        this.district = district;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contactLink = contactLink;
        this.ticketPrice = ticketPrice;
        this.reservationLink = reservationLink;
        this.posterUrl = posterUrl;
        this.progress = progress;
        this.likeCnt = likeCnt;
    }
}