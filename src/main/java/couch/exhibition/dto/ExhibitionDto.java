package couch.exhibition.dto;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExhibitionDto {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String place;

    private String city;

//    private String district;

    private BigDecimal latitude;
    private BigDecimal longitude;

    private LocalDate startDate;
    private LocalDate endDate;

    private String contactLink;

    private String ticketPrice;

    private String reservationLink;

    private String posterUrl;

    private boolean progress;

    private Integer likeCnt;


    public ExhibitionDto(String title, String place, String city,
                         BigDecimal latitude, BigDecimal longitude,
                         LocalDate startDate, LocalDate endDate,
                         String contactLink, String ticketPrice,
                         String reservationLink, String posterUrl
                         ) {
        this.title = title;
        this.place = place;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contactLink = contactLink;
        this.ticketPrice = ticketPrice;
        this.reservationLink = reservationLink;
        this.posterUrl = posterUrl;
    }
}