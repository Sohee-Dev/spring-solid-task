package com.puzzlix.solid_task.domain.notification;

public class SmsNotificationSender implements NotificationSender{
    @Override
    public void send(String message) {
        // 외부 API 연동 문자 발송 처리 (오늘의 도전과제) - 무료 api 찾아서 구축해보기
        System.out.println("[SMS 발송]" + message);
    }

    @Override
    public boolean supports(String type) {
        return "SMS".equalsIgnoreCase(type);
    }
}
