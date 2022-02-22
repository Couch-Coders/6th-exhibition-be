package couch.exhibition.dto;

import couch.exhibition.entity.Exhibition;
import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;

@Data
public class ExhibitionDto {

    private Long id;
    private String title;
    private String place;
    private String placeAddr;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer startDate;
    private Integer endDate;
    private String contactLink;
    private String ticketPrice;
    private String reservationLink;
    private String posterUrl;
    private Integer likeCnt;

    public ExhibitionDto(Exhibition exhibition) {
        this.id = exhibition.getId();
        this.title = exhibition.getTitle();
        this.place = exhibition.getPlace();
        this.placeAddr = exhibition.getPlaceAddr();
        this.latitude = exhibition.getLatitude();
        this.longitude = exhibition.getLongitude();
        this.startDate = exhibition.getStartDate();
        this.endDate = exhibition.getEndDate();
        this.contactLink = exhibition.getContactLink();
        this.ticketPrice = exhibition.getTicketPrice();
        this.reservationLink = exhibition.getReservationLink();
        this.posterUrl = exhibition.getPosterUrl();
        this.likeCnt = exhibition.getLikeCnt();
    }

}