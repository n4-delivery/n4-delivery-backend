package com.sparta.n4delivery.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * API 응답 시 사용되는 상태 코드와 메시지를 정의하는 enum
 *
 * @see <a href="https://ko.wikipedia.org/wiki/HTTP_%EC%83%81%ED%83%9C_%EC%BD%94%EB%93%9C">HTTP 상태 코드</a>
 * @since 2024-10-03
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {
    // 200
    SUCCESS_LOGIN(HttpStatus.OK, "로그인 성공"),
    SUCCESS_LOGOUT(HttpStatus.OK, "로그 아웃 성공"),
    SUCCESS_SEARCH_USER(HttpStatus.NO_CONTENT, "유저 조회 성공"),
    SUCCESS_SEARCH_STORE(HttpStatus.NO_CONTENT, "가게 조회 성공"),
    SUCCESS_SEARCH_MENU(HttpStatus.NO_CONTENT, "메뉴 조회 성공"),
    SUCCESS_SEARCH_ORDER(HttpStatus.NO_CONTENT, "주문 조회 성공"),
    SUCCESS_SEARCH_REVIEW(HttpStatus.NO_CONTENT, "리뷰 조회 성공"),
    SUCCESS_UPDATE_USER(HttpStatus.NO_CONTENT, "유저 수정 성공"),
    SUCCESS_UPDATE_STORE(HttpStatus.NO_CONTENT, "가게 수정 성공"),
    SUCCESS_UPDATE_MENU(HttpStatus.NO_CONTENT, "메뉴 수정 성공"),
    SUCCESS_UPDATE_ORDER(HttpStatus.NO_CONTENT, "주문 수정 성공"),
    SUCCESS_UPDATE_REVIEW(HttpStatus.NO_CONTENT, "리뷰 수정 성공"),

    // 201
    SUCCESS_CREATE_USER(HttpStatus.CREATED, "회원 가입 성공"),
    SUCCESS_CREATE_STORE(HttpStatus.CREATED, "가게 추가 성공"),
    SUCCESS_CREATE_MENU(HttpStatus.NO_CONTENT, "메뉴 추가 성공"),
    SUCCESS_CREATE_ORDER(HttpStatus.NO_CONTENT, "주문 추가 성공"),
    SUCCESS_CREATE_REVIEW(HttpStatus.NO_CONTENT, "리뷰 추가 성공"),

    // 204
    SUCCESS_DELETE_USER(HttpStatus.NO_CONTENT, "유저 삭제 성공"),
    SUCCESS_DELETE_STORE(HttpStatus.NO_CONTENT, "가게 삭제 성공"),
    SUCCESS_DELETE_MENU(HttpStatus.NO_CONTENT, "메뉴 삭제 성공"),
    SUCCESS_DELETE_ORDER(HttpStatus.NO_CONTENT, "주문 삭제 성공"),
    SUCCESS_DELETE_REVIEW(HttpStatus.NO_CONTENT, "리뷰 삭제 성공"),

    // 400
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "잘못된 JWT 토큰 입니다."),
    INVALID_JSON(HttpStatus.BAD_REQUEST, "잘못된 JSON 형식 전송"),
    BAD_INPUT(HttpStatus.BAD_REQUEST, "잘못된 값 입력"),
    UNSIGNED_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않는 JWT 서명 입니다."),
    FAILED_ENCODING_TOKEN(HttpStatus.BAD_REQUEST, "잘못된 인코딩을 사용하였습니다."),

    // 401
    TIMEOUT_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 JWT token 입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "지원되지 않는 JWT 토큰 입니다."),
    NOT_MATCH_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다."),

    // 403
    INVALID_PERMISSION(HttpStatus.FORBIDDEN, "권한이 없습니다"),

    // 404
    NOT_FOUND_TOKEN(HttpStatus.NOT_FOUND, "JWT 토큰이 없습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    NOT_FOUND_STORE(HttpStatus.NOT_FOUND, "존재하지 않는 가게"),
    NOT_FOUND_MENU(HttpStatus.NOT_FOUND, "존재하지 않는 메뉴"),
    INACTIVE_STORE(HttpStatus.NOT_FOUND, "폐업한 가게"),

    // 409
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "중복된 이메일"),
    ALREADY_MENU(HttpStatus.CONFLICT, "이미 등록된 메뉴"),

    // 500
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류"),

    // === MH ===

    // === DH ===

    // === MG ===

    // === HJ ===
    CLOSED_STORE(HttpStatus.BAD_REQUEST, "가게가 오픈 상태가 아닙니다."),
    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."),
    ALREADY_ACCEPT_ORDER(HttpStatus.BAD_REQUEST, "이미 주문이 접수되었습니다.");
    // ===

    private final HttpStatus httpStatus;
    private final String message;
}
