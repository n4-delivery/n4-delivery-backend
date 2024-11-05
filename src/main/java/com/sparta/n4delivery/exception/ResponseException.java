package com.sparta.n4delivery.exception;

import com.sparta.n4delivery.enums.ResponseCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * API 응답 시 발생하는 예외를 나타내는 class
 *
 * @since 2024-10-03
 */
@Getter
public class ResponseException extends RuntimeException {
    private final ResponseCode responseCode;

    public ResponseException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }
}
