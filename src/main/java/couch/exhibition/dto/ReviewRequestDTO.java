package couch.exhibition.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewPostRequestDTO {

    private String content;
    private LocalDateTime registeredDateTime = LocalDateTime.now();
    private LocalDateTime modifiedDateTime = LocalDateTime.now();
}



