package com.puzzlix.solid_task.domain.issue.event;

import com.puzzlix.solid_task.domain.issue.Issue;
import lombok.Getter;

/**
 * 스프링에서 제공하는 스프링 이벤트를 사용할 예정
 * -- 느슨한 커플링을 구현하기 위해 많이 채택이 된다.
 * -- 이벤트 클래스로 설계 한다
 * */

@Getter
public class IssueStatusChangedEvent {
    private final Issue issue;

    public IssueStatusChangedEvent(Issue issue){
        this.issue = issue;
    }
}
