package com.likelion.likelioncrud.common.exception;


import com.likelion.likelioncrud.common.error.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final ErrorCode errorCode;
    private final String customMessage;

    public BusinessException(ErrorCode errorCode, String customMessage) {
        super(customMessage); //RuntimeException의 생성자에 메시지 전달
        this.errorCode = errorCode;
        this.customMessage = customMessage;
    }
}
