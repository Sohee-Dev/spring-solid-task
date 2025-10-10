package com.puzzlix.solid_task.domain.issue;

public class Issue {
    // pk
    // 타이틀
    // 내용
    // 진행 상태

    // 프로젝트 pk
    // 보고자(누가 요청 했는지)
    // 담당자

    private Long id;
    private String title;
    private String description;
    private IssueStatus issueStatus; //시작 진행중 완료 상태(범위) - Enum 타입

    // 추후 연관관계 필드
    private Long projectId;

    //누가 요청(보고)
    private Long reporterId;

    // 담당자(누군가에게 할당 되어 처리 됩니다)
    private Long assigneeId;


}
