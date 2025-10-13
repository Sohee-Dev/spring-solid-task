package com.puzzlix.solid_task.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class UserRequest {

    // 회원 가입 UserRequest.SignUp a = new UserRequest.SignUp()
    @Getter
    @Setter
    public static class Signup{

        @NotEmpty
        @Size(min = 2, max = 20)
        private String name;

        @NotEmpty
        @Email // 이메일 형식 검증
        private String email;

        @NotEmpty
        @Size(min = 4, max = 20)
        private String password;
    }

    // 로그인
    @Getter
    @Setter
    public static class Login{
        @Email
        @NotEmpty
        private String email;

        @NotEmpty
        private String password;

    }
}
