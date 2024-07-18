package me.khw7385.trip_planner.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.khw7385.trip_planner.user.dto.DuplicateLoginIdCheckDto;
import me.khw7385.trip_planner.user.dto.UserSignUpDto;
import me.khw7385.trip_planner.user.exception.UserException;
import me.khw7385.trip_planner.user.exception.type.UserExceptionType;
import me.khw7385.trip_planner.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @DisplayName("회원 가입 성공")
    @Test
    void signUp() throws Exception {
        //given
        UserSignUpDto.Request request = UserSignUpDto.Request.builder()
                .name("손흥민")
                .loginId("abcd1234")
                .password("123456")
                .isDoneDuplicateCheck(true)
                .build();
        given(userService.saveUser(any(UserSignUpDto.Command.class))).willReturn(UserSignUpDto.Response.builder().build());
        //when
        ResultActions resultActions = mockMvc.perform(post("/api/user/save")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isCreated());
    }

    @DisplayName("회원 가입 실패")
    @Test
    void signUpEx() throws Exception {
        //given
        UserSignUpDto.Request request = UserSignUpDto.Request.builder()
                .name("손흥민")
                .loginId("abcd1234")
                .password("123456")
                .isDoneDuplicateCheck(false)
                .build();

        given(userService.saveUser(any(UserSignUpDto.Command.class))).willThrow(new UserException(UserExceptionType.NOT_DUPLICATE_CHECK_LOGIN_ID));
        //when
        ResultActions resultActions = mockMvc.perform(post("/api/user/save")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON));
        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("U001"));
    }

    @DisplayName("아이디 중복 체크")
    @Test
    void checkDuplicateLoginId() throws Exception {
        given(userService.checkDuplicatedLoginId(any(DuplicateLoginIdCheckDto.Command.class))).willReturn(DuplicateLoginIdCheckDto.Response.builder().build());

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/user/check_duplicate_login_id").queryParam("loginId", "abcd1234"));

        //then
        resultActions.andExpect(status().isOk());
    }
}