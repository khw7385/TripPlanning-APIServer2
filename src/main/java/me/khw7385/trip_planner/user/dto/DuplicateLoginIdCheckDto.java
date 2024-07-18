package me.khw7385.trip_planner.user.dto;

import lombok.Builder;

public class DuplicateLoginIdCheckDto {
    @Builder
    public record Command(String loginId){}

    @Builder
    public record Response(boolean isDuplicated){}

    public static Command toCommand(String loginId){
        return Command.builder()
                .loginId(loginId)
                .build();
    }
}
