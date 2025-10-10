package com.puzzlix.solid_task.domain.issue.dto;

import com.puzzlix.solid_task.domain.issue.Issue;
import com.puzzlix.solid_task.domain.issue.IssueStatus;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class IssueResponse {

    @Getter
    public static class FindAll{
        private final Long id;
        private final String title;
        private final IssueStatus status;
        private final String reporterName;

        // 생성자를 private 선언
        private FindAll(Issue issue){
            this.id = issue.getId();
            this.title = issue.getTitle();
            this.status = issue.getIssueStatus();

            // 우리는 현재 LAZY 로딩 전략 - issue 안에 포함된
            // User를 탐색하게 되면 추가 발생 쿼리가 발생 됨 -> N+1 문제
            this.reporterName = issue.getReporter().getName();
        }

        // 정적 팩토리 메서드 선언 (이녀석은 제네릭이 아님)
        // IssueResponse.from([issue, issue, issue]);
        // Entity 를 DTO로 변환하는 정적 팩토리 메서드를 만듦.
        public static List<FindAll> from(List<Issue> issues){
            List<FindAll> dtoList = new ArrayList<>();
            for(Issue issue : issues){
                dtoList.add(new FindAll(issue));
            }
            return dtoList;
        }
    }
}
