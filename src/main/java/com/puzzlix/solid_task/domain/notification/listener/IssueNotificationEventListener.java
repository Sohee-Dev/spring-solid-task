package com.puzzlix.solid_task.domain.notification.listener;

import com.puzzlix.solid_task.domain.issue.Issue;
import com.puzzlix.solid_task.domain.issue.event.IssueStatusChangedEvent;
import com.puzzlix.solid_task.domain.notification.NotificationSender;
import com.puzzlix.solid_task.domain.notification.NotificationSenderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IssueNotificationEventListener {

    private final NotificationSenderFactory notificationSenderFactory;

    // 알림 정책 - yml 파일 -> EMAIL
    @Value("${notification.policy.on-status-done}")
    private String onStatusDoneType;

    @EventListener
    public void handleIssueStatusChangeEvent(IssueStatusChangedEvent event){
        Issue issue = event.getIssue();
        String message = "이슈 #" + issue.getTitle() + " 의 상태가 " + issue.getIssueStatus() + "로 변경 되었습니다";
        // String message = "메세지 API 테스트";

        // 돈이 나가는 일이기 때문에 방어적 코드 작성
        if("DONE".equalsIgnoreCase(issue.getIssueStatus().name())){
            // 팩토리 클래스를 활용해서 현재 알림 전략을 가지고 온다.
            // yml -> EMAIL, SMS
            NotificationSender sender = notificationSenderFactory.findSender(onStatusDoneType);

            // 이메일 구현 클래스든, sms 구현 클래스든 여기서는 send만 호출하면 됨.
            sender.send(message);
        }
    }
}
