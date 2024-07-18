package me.khw7385.trip_planner.user.service.impl;

import lombok.RequiredArgsConstructor;
import me.khw7385.trip_planner.user.dto.DuplicateLoginIdCheckDto;
import me.khw7385.trip_planner.user.dto.UserSignUpDto;
import me.khw7385.trip_planner.user.entity.User;
import me.khw7385.trip_planner.user.exception.UserException;
import me.khw7385.trip_planner.user.exception.type.UserExceptionType;
import me.khw7385.trip_planner.user.repository.UserRepository;
import me.khw7385.trip_planner.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserSignUpDto.Response saveUser(UserSignUpDto.Command command) {
        if(!command.isDoneDuplicateCheck()){
            throw new UserException(UserExceptionType.NOT_DUPLICATE_CHECK_LOGIN_ID);
        }
        User user = User.builder()
                .loginId(command.userId())
                .password(passwordEncoder.encode(command.password()))
                .loginId(command.userId())
                .build();

        User saveUser = userRepository.save(user);
        return UserSignUpDto.Response.builder()
                .loginId(saveUser.getLoginId())
                .password(saveUser.getPassword())
                .name(saveUser.getName())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public DuplicateLoginIdCheckDto.Response checkDuplicatedLoginId(DuplicateLoginIdCheckDto.Command command) {
        boolean isDuplicated = userRepository.findByLoginId(command.loginId()).isPresent();
        return DuplicateLoginIdCheckDto.Response.builder()
                .isDuplicated(isDuplicated)
                .build();
    }
}
