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
    ROLE_CHANGE_REQUEST_COMPLETE(HttpStatus.OK, "이메일 전송이 완료되었습니다. 관리자의 승인을 기다려주세요."),

    // 400 error - member
    NOT_FOUND_APPROVAL_REQUEST(HttpStatus.BAD_REQUEST, "승인 요청이 존재하지 않습니다."),
    INVALID_APPROVAL_REQUEST_TYPE(HttpStatus.BAD_REQUEST, "유효하지 않은 승인 요청 타입입니다."),

    MEMBER_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST, "이미 등록된 회원입니다."),
    NOT_FOUND_MEMBER(HttpStatus.BAD_REQUEST, "회원이 존재하지 않습니다."),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    SIGNUP_TIME_EXPIRED(HttpStatus.BAD_REQUEST, "회원가입 가능 시간이 만료되었습니다. 다시 시도해 주세요."),

    CANNOT_CHANGE_TO_SAME_ROLE(HttpStatus.BAD_REQUEST, "같은 권한으로는 변경할 수 없습니다."),
    CANNOT_CHANGE_ADMIN_ROLE(HttpStatus.BAD_REQUEST, "ADMIN 계정은 권한을 변경할 수 없습니다."),
    CANNOT_CHANGE_TO_ADMIN_ROLE(HttpStatus.BAD_REQUEST, "ADMIN 권한으로 변경할 수 없습니다."),

    // 400 error - email
    EMAIL_ALREADY_USED(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일 입니다."),
    EMAIL_ALREADY_VERIFICATION(HttpStatus.BAD_REQUEST, "이미 인증된 이메일 입니다."),
    INVALID_EMAIL_VERIFICATION(HttpStatus.BAD_REQUEST, "이메일 인증이 유효하지 않습니다."),
    EMAIL_VERIFICATION_REQUIRED(HttpStatus.BAD_REQUEST, "이메일 인증이 필요합니다."),
    EXPIRED_EMAIL_AUTH_CODE(HttpStatus.BAD_REQUEST, "이메일 인증 유효기간이 만료되었습니다."),


    // 500 error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "일시적인 서버 오류입니다. 잠시후 다시 시도해주세요.");

    private final HttpStatus httpStatus;
    private final String message;
}
