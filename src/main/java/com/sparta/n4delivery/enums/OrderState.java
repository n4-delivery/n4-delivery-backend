package com.sparta.n4delivery.enums;

/**
 * 주문 상태를 나타내는 열거형입니다.
 *
 * @since 2024-11-04
 */
public enum OrderState {
    // 요청
    REQUEST,
    // 취소
    CANCEL,
    // 접수
    ACCEPT,
    // 조리 중
    COOKING,
    // 배달 중
    DELIVERING,
    // 배달 완료
    COMPLETE,
}
