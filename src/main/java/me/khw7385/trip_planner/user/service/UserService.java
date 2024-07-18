package me.khw7385.trip_planner.user.service;

import me.khw7385.trip_planner.user.dto.DuplicateLoginIdCheckDto;
import me.khw7385.trip_planner.user.dto.UserSignUpDto;

public interface UserService {

    UserSignUpDto.Response saveUser(UserSignUpDto.Command command);

    DuplicateLoginIdCheckDto.Response checkDuplicatedLoginId(DuplicateLoginIdCheckDto.Command command);
}
