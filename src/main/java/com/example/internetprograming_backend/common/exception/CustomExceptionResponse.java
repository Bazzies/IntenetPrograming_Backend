package com.example.internetprograming_backend.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomExceptionResponse {

    // 200 error
    SUCCESS(HttpStatus.OK, "성공"),
    SEND_EMAIL_COMPLETE(HttpStatus.OK, "이메일이 전송되었습니다. 이메일을 확인해주세요."),
    CHECK_EMAIL_VERIFICATION(HttpStatus.OK, "이메일 인증이 완료되었습니다."),

    // 400 error - member
    MEMBER_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "이미 등록된 회원입니다."),
    NOT_FOUND_MEMBER(HttpStatus.BAD_REQUEST, "회원이 존재하지 않습니다."),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    // 400 error - email
    EMAIL_ALREADY_USED(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일 입니다."),
    NOT_FOUND_EMAIL_VERIFICATION(HttpStatus.BAD_REQUEST, "이메일 인증 기록을 찾을 수 없습니다."),
    EMAIL_ALREADY_VERIFICATION(HttpStatus.BAD_REQUEST, "이미 인증된 이메일 입니다."),
    INVALID_EMAIL_VERIFICATION(HttpStatus.BAD_REQUEST, "이메일 인증이 유효하지 않습니다."),
    EXPIRED_EMAIL_AUTH_CODE(HttpStatus.BAD_REQUEST, "이메일 인증 유효기간이 만료되었습니다."),


    // 500 error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "일시적인 서버 오류입니다. 잠시후 다시 시도해주세요.");

    private final HttpStatus httpStatus;
    private final String message;
}
