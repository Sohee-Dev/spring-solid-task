package com.puzzlix.solid_task.domain.notification;

import com.solapi.sdk.SolapiClient;
import com.solapi.sdk.message.exception.SolapiMessageNotReceivedException;
import com.solapi.sdk.message.model.Message;
import com.solapi.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Component;

@Component
public class SmsNotificationSender implements NotificationSender{
    @Override
    public void send(String message) {
        // 외부 API 연동 문자 발송 처리 (오늘의 도전과제) - 무료 api 찾아서 구축해보기
        // 솔라피 API 사용 -> sdk 의존성 추가하여 구현
        System.out.println("[SMS 발송]" + message);

        DefaultMessageService messageService =  SolapiClient.INSTANCE.createInstance("api_key", "api_secretKey");
        // Message 패키지가 중복될 경우 com.solapi.sdk.message.model.Message로 치환하여 주세요
        Message sol_message = new Message();
        sol_message.setFrom("phone_number"); // 보내는 사람 번호
        sol_message.setTo("phone_number"); // 받는사람 번호
        sol_message.setText(message);

        try {
            // send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
            messageService.send(sol_message);
        } catch (SolapiMessageNotReceivedException exception) {
            // 발송에 실패한 메시지 목록을 확인할 수 있습니다!
            System.out.println(exception.getFailedMessageList());
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public boolean supports(String type) {
        return "SMS".equalsIgnoreCase(type);
    }
}
