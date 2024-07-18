package me.khw7385.trip_planner.user.service.impl;

import me.khw7385.trip_planner.user.dto.DuplicateLoginIdCheckDto;
import me.khw7385.trip_planner.user.dto.UserSignUpDto;
import me.khw7385.trip_planner.user.entity.User;
import me.khw7385.trip_planner.user.exception.UserException;
import me.khw7385.trip_planner.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 가입 성공")
    void saveUser(){
        // given
        UserSignUpDto.Command command = UserSignUpDto.Command.builder()
                .loginId("abcd1234")
                .name("손흥민")
                .password("123456")
                .isDoneDuplicateCheck(true).build();

        given(passwordEncoder.encode(any(String.class))).willReturn("qwerasdf");
        given(userRepository.save(any(User.class))).willReturn(User.builder().loginId("abcd1234").password("qwerasdf").name("손흥민").build());
        // when
        UserSignUpDto.Response response = userService.saveUser(command);

        // then
        assertThat(response.password()).isEqualTo("qwerasdf");
    }

    @Test
    @DisplayName("아이디 중복 체크 하지 않고 회원 저장")
    void saveUserNoDuplicateCheck(){
        // given
        UserSignUpDto.Command command = UserSignUpDto.Command.builder()
                .loginId("abcd1234")
                .name("손흥민")
                .password("123456")
                .isDoneDuplicateCheck(false).build();
        //when
        UserException userException = catchThrowableOfType(() -> userService.saveUser(command), UserException.class);

        //then
        assertThat(userException.getExceptionType().getErrorCode()).isEqualTo("U001");
    }

    @Test
    @DisplayName("아이디 중복 체크 시 존재하는 아이디 X")
    void checkDuplicatedLoginIdPresent(){
        //given
        DuplicateLoginIdCheckDto.Command command = DuplicateLoginIdCheckDto.Command.builder()
                .loginId("abcd1234")
                .build();
        given(userRepository.findByLoginId(any(String.class))).willReturn(Optional.of(User.builder().build()));
        //when
        DuplicateLoginIdCheckDto.Response response = userService.checkDuplicatedLoginId(command);
        //then
        assertThat(response.isDuplicated()).isTrue();
    }

    @Test
    @DisplayName("아이디 중보 체크 시 존재하는 아이디 O")
    void checkDuplicatedLoginIdNotPresent(){
        //given
        DuplicateLoginIdCheckDto.Command command = DuplicateLoginIdCheckDto.Command.builder()
                .loginId("abcd1234")
                .build();
        given(userRepository.findByLoginId(any(String.class))).willReturn(Optional.empty());
        //when
        DuplicateLoginIdCheckDto.Response response = userService.checkDuplicatedLoginId(command);
        //then
        assertThat(response.isDuplicated()).isFalse();
    }
}
