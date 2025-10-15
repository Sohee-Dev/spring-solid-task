package com.puzzlix.solid_task.domain.notification;

/**
 * 알림 전략을 설계 한다 - 구현 클래스에 강제 시킬 추상 메서드 설계
 * */
public interface NotificationSender {
    void send(String message);
    boolean supports(String type);

}
