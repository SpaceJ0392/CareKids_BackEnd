package com.aivle.carekids.global.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {
    TOKEN_IS_NOT_SAME(404, "토큰 불일치");

    private int status;
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}

