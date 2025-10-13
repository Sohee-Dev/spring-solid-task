package com.puzzlix.solid_task.domain.user.login;

import com.puzzlix.solid_task.domain.user.User;
import com.puzzlix.solid_task.domain.user.dto.UserRequest;

/**
 * 도전 과제
 * */
public class GoogleLoginStrategy implements LoginStrategy{
    @Override
    public User login(UserRequest.Login request) {
        // TODO 도전 과제
        // 1. 클라이언트에게 받은 구글 토큰을 가져 옴
        // 2. 구글 서버로부터 사용자 정보(이메일 이름)
        // 3. 해당 이메일을 우리 db조회 후 없으면 자동 회원 가입 있으면 로그인 처리
        return null;
    }

    @Override
    public boolean supports(String type) {
        return "GOOGLE".equalsIgnoreCase(type);
    }
}
