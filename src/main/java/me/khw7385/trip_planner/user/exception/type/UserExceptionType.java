package me.khw7385.trip_planner.user.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.khw7385.trip_planner.base.BaseExceptionType;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserExceptionType implements BaseExceptionType {
    NOT_DUPLICATE_CHECK_LOGIN_ID(HttpStatus.BAD_REQUEST, "U001", "로그인 아이디 중복 체크를 하지 않았습니다.");

    final HttpStatus httpStatus;
    final String errorCode;
    final String message;
}