package me.khw7385.trip_planner.user.controller;

import lombok.RequiredArgsConstructor;
import me.khw7385.trip_planner.user.dto.DuplicateLoginIdCheckDto;
import me.khw7385.trip_planner.user.dto.UserSignUpDto;
import me.khw7385.trip_planner.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/save")
    public ResponseEntity<UserSignUpDto.Response> signUp(@RequestBody UserSignUpDto.Request request){
        UserSignUpDto.Response response = userService.saveUser(request.toCommand());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/check_duplicate_login_id")
    public ResponseEntity<DuplicateLoginIdCheckDto.Response> checkDuplicateLoginId(@RequestParam String loginId){
        return ResponseEntity.ok(userService.checkDuplicatedLoginId(DuplicateLoginIdCheckDto.toCommand(loginId)));
    }
}
