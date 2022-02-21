package couch.exhibition.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_REQUEST_PARAM(HttpStatus.BAD_REQUEST, "잘못된 파라미터 요청입니다."),

    EXIST_USER(HttpStatus.BAD_REQUEST, "이미 가입한 유저입니다."),
    DELETED_USER(HttpStatus.BAD_REQUEST, "삭제된 유저입니다."),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "로그인 인증이 필요합니다."),
    FORBIDDEN_USER(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),

    EXIST_LIKED_EXHIBITION(HttpStatus.BAD_REQUEST, "이미 좋아요를 누른 전시회입니다."),
    EXIST_LIKED_DELETE_EXHIBITION(HttpStatus.BAD_REQUEST, "이미 좋아요를 취소한 전시회입니다."),

    NOT_FOUND_EXHIBITION(HttpStatus.NOT_FOUND, "존재하지 않거나 마감된 전시회입니다."),
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND, "존재하지 않는 리뷰입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
