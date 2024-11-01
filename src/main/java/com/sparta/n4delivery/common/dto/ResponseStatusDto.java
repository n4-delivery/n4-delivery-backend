package com.sparta.n4delivery.common.dto;

import com.sparta.n4delivery.enums.ResponseCode;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * API응답 상태에 대한 정보를 제공하는 DTO 클래스
 *
 * @since 2024-10-03
 */
@Data
public class ResponseStatusDto {
    private String date;
    private int state;
    private String message;
    private String url;

    public ResponseStatusDto(ResponseCode responseCode, String requestUrl) {
        date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        state = responseCode.getHttpStatus().value();
        message = responseCode.getMessage();
        url = requestUrl;
    }

    public ResponseStatusDto(ResponseCode responseCode, String requestUrl, String errorMsg) {
        date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        state = responseCode.getHttpStatus().value();
        message = responseCode.getMessage() + ": " + errorMsg;
        url = requestUrl;
    }
}