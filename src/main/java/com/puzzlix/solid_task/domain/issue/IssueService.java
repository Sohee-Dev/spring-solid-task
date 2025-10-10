package com.puzzlix.solid_task.domain.issue;

import com.puzzlix.solid_task.domain.issue.dto.IssueRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // IoC
@RequiredArgsConstructor
public class IssueService {

    //구체 클래스가 아닌, IssueRepository 라는 역할(인터페이스)에만 의존한다.
    private final IssueRepository issueRepository;

    // 이슈 생성 로직
    public Issue createIssue(IssueRequest .Create request){
        Issue newIssue = new Issue();
        newIssue.setTitle(request.getTitle());
        newIssue.setDescription(request.getDescription());
        newIssue.setReporterId(request.getReporterId());
        // 이슈 --> TODO
        newIssue.setIssueStatus(IssueStatus.TODO);

        return issueRepository.save(newIssue);
    }

    // 모든 이슈 조회
    public List<Issue> findIssues(){
        return issueRepository.findAll();
    }
}
