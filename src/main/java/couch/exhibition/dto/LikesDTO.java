package couch.exhibition.dto;

import couch.exhibition.entity.Exhibition;
import couch.exhibition.entity.Likes;
import couch.exhibition.entity.Member;
import lombok.Data;

@Data
public class LikesDTO {

    private Long id;
    private Member member;
    private Exhibition exhibition;

    public LikesDTO(Likes like) {
        this.id = like.getId();
        this.member = like.getMember();
        this.exhibition = like.getExhibition();
    }

}
