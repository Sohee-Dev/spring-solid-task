package com.puzzlix.solid_task.domain.notification;

import org.springframework.stereotype.Component;

@Component
public class EmailNotificationSender implements NotificationSender{
    @Override
    public void send(String message) {
        // 이메일 외부 API 연동 기능 처리
        System.out.println("[이메일 발송]" + message);
    }

    @Override
    public boolean supports(String type) {
        // 이메일이라는 타입의 요청을 처리할 수 있다고 선언
        return "EMAIL".equalsIgnoreCase(type);
    }
}
