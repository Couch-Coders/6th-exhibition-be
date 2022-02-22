package couch.exhibition.dto;

import couch.exhibition.entity.Exhibition;
import couch.exhibition.entity.Likes;
import couch.exhibition.entity.Member;
import lombok.Data;

@Data
public class LikesDTO {

    private Long id;
    private String memberId;
    private Long exhibitionId;

    public LikesDTO(Likes like) {
        this.id = like.getId();
        this.memberId = like.getMember().getId();
        this.exhibitionId = like.getExhibition().getId();
    }

}
