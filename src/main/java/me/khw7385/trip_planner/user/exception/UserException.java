package me.khw7385.trip_planner.user.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.khw7385.trip_planner.base.BaseException;
import me.khw7385.trip_planner.base.BaseExceptionType;

@Data
@AllArgsConstructor
public class UserException extends BaseException {
    private BaseExceptionType exceptionType;
}
