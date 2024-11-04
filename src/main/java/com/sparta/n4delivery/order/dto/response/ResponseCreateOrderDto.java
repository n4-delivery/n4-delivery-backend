package com.sparta.n4delivery.order.dto.response;

import com.sparta.n4delivery.common.dto.ResponseStatusDto;
import com.sparta.n4delivery.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCreateOrderDto {
    private Long orderId;
    private ResponseStatusDto status;

    public static ResponseCreateOrderDto createResponseDto(Long orderId, String requestUrl) {
        ResponseCreateOrderDto responseDto = new ResponseCreateOrderDto();
        ResponseStatusDto status = new ResponseStatusDto(ResponseCode.SUCCESS_CREATE_ORDER, requestUrl);
        responseDto.setOrderId(orderId);
        responseDto.setStatus(status);
        return responseDto;
    }
}
