package com.puzzlix.solid_task.domain.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationSenderFactory {

    private final List<NotificationSender> senderList;

    // 들어온 type에 맞는 Sender를 찾아서 반환
    public NotificationSender findSender(String type){
        for(NotificationSender sender : senderList){
            if(sender.supports(type)){
                return sender;
            }
        }
        throw new IllegalArgumentException("지원하지 않는 알림 방식입니다: " + type);
    }

}
