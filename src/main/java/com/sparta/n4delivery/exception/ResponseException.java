package com.sparta.n4delivery.exception;

import com.sparta.n4delivery.enums.ResponseCode;
import lombok.Getter;

/**
 * API 응답 시 발생하는 예외를 나타내는 class
 *
 * @since 2024-10-03
 */
@Getter
public class ResponseException extends RuntimeException {
    private final ResponseCode responseCode;

    /**
     * ResponseException 클래스의 생성자
     * ResponseCode 객체를 기반으로 예외를 생성합니다.
     *
     * @param responseCode 응답 코드 정보를 담은 객체
     * @since 2024-11-05
     */
    public ResponseException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    /**
     * ResponseException 클래스의 생성자
     * ResponseCode 객체와 상세 메시지를 기반으로 예외를 생성합니다.
     *
     * @param responseCode 응답 코드 정보를 담은 객체
     * @param detailMsg    상세 메시지
     * @since 2024-11-05
     */
    public ResponseException(ResponseCode responseCode, String detailMsg) {
        super(responseCode.getMessage() + " [" + detailMsg + "]");
        this.responseCode = responseCode;
    }
}
