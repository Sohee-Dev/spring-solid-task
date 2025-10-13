package com.puzzlix.solid_task.domain.user.login;

import com.puzzlix.solid_task.domain.user.User;
import com.puzzlix.solid_task.domain.user.dto.UserRequest;

public interface LoginStrategy {
    // 모든 로그인 전략은 login 메서드를 구현해야 한다.
    User login(UserRequest.Login request);
    // 모든 로그인 전략은 자신이 어떤 타입인지 알려 줘야 한다.
    boolean supports(String type);
}
