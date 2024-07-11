package com.aivle.carekids.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    TOKEN_IS_NOT_SAME(404, "토큰 불일치"),
    USER_NOT_AUTHENTICATED(404,"미인증 사용자"),
    USER_NOT_FOUND(404, "미가입 사용자");

    private int status;
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}

