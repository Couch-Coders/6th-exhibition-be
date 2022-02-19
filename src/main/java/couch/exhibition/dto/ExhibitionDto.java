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

}