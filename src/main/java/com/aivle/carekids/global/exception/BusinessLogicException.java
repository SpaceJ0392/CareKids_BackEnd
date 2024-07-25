package com.aivle.carekids.global.exception;

public class BusinessLogicException extends RuntimeException{

    public BusinessLogicException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
    }
}
