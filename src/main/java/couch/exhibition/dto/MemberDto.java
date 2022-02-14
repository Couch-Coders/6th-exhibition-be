package couch.exhibition.dto;

import couch.exhibition.entity.Member;
import lombok.Data;

@Data
public class MemberDto {

    private String id;
    private String memberName;
    private String nickname;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.memberName = member.getMemberName();
        this.nickname = member.getNickname();
    }
}
