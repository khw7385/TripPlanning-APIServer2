package me.khw7385.trip_planner.user.dto;

import lombok.Builder;

public class UserSignUpDto {
    @Builder
    public record Request(String userId, String password, String name, boolean isDoneDuplicateCheck){
        public Command toCommand(){
            return Command.builder()
                    .userId(userId)
                    .password(password)
                    .name(name)
                    .isDoneDuplicateCheck(isDoneDuplicateCheck)
                    .build();
        }
    }

    @Builder
    public record Command(String userId, String password, String name, boolean isDoneDuplicateCheck){
    }
    @Builder
    public record Response(String loginId, String password, String name){}
}
