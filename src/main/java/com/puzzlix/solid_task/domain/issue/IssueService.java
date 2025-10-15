package com.puzzlix.solid_task.domain.issue;

import com.puzzlix.solid_task.domain.issue.dto.IssueRequest;
import com.puzzlix.solid_task.domain.issue.event.IssueStatusChangedEvent;
import com.puzzlix.solid_task.domain.project.Project;
import com.puzzlix.solid_task.domain.project.ProjectRepository;
import com.puzzlix.solid_task.domain.user.Role;
import com.puzzlix.solid_task.domain.user.User;
import com.puzzlix.solid_task.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service // IoC
@RequiredArgsConstructor
@Transactional
public class IssueService {

    //구체 클래스가 아닌, IssueRepository 라는 역할(인터페이스)에만 의존한다.
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    // 이벤트 발생자(스프링이 제공)
    // 이 객체를 통해서 터플리케이션의 다른 부분에 "어떤 이벤트가 발생 했다" 라는 것을 알릴 수 있다.
    private final ApplicationEventPublisher eventPublisher;

    public Issue updateIssueStatus(Long issueId, IssueStatus status, String requestUserEmail, Role userRole){
        // 인가 처리
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(()-> new NoSuchElementException("해당 ID의 이슈를 찾을 수 없습니다"));

        // 관리자가 아니거나 담당자가 아니면 상태를 변경 못함
        if(userRole != Role.ADMIN && issue.getAssignee().getEmail().equals(requestUserEmail)){
            throw new SecurityException("이슈 상태를 변경할 권한이 없습니다");
        }
        // 더티 체킹
        issue.setIssueStatus(status);
        if(status == IssueStatus.DONE){
            // 이벤트 발생(방송)
            eventPublisher.publishEvent(new IssueStatusChangedEvent(issue));
        }
        return issue;
    }

    // 이슈 업데이트
    public Issue updateIssue(Long issueId, IssueRequest.Update request, String requestUserEmail){
        User requestUser = userRepository.findByEmail(requestUserEmail)
                .orElseThrow(() -> new NoSuchElementException("요청한 사용자를 찾을 수 없습니다"));

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 이슈를 찾을 수 없습니다"));

        // 인가 처리 -> 관리자라면 수정 가능 하게 변경
        // 인가 로직
        boolean isAdmin = requestUser.getRole() == Role.ADMIN;
        boolean isReporter = requestUser.getId().equals(issue.getReporter().getId());

        if(!isAdmin && !isReporter){
            throw new SecurityException("이슈를 수정할 권한이 없습니다");
        }

        // 넘어온 값이 담당자 할당 여부에 따로 분기 처리 되어야 함
        if(request.getAssigneeId() != null){
            User assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new NoSuchElementException("해당 ID의 담당자를 찾을 수 없습니다"));
            // 담당자 할당
            issue.setAssignee(null);
        }


        issue.setTitle(request.getTitle());
        issue.setDescription(request.getDescription());

        // JPA 변경 감지(Dirty Checking 덕분에 save() 명시적으로 호출 하지 않아도
        // 트랜잭션이 끝날 때 변경된 내용이 DB에 자동으로 반영 된다.
        return issue;
    }

    // 이슈 삭제 로직
    // 인가 처리 ADMIN만 삭제 가능
    public void deleteIssue(Long issueId, String requestUserEmail){
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 이슈를 찾을 수 없습니다"));

        // 1. 이메일로 사용자 조회
        // 2. 사용자의 ROLE 확인 해서 ADMIN 일 경우 삭제 가능
        // 3. 추가 로직 - 보고자(Reporter)도 삭제할 수 있는 기능을 추가(실수로 등록 시 삭제 할 수 있도록)
        User user = userRepository.findByEmail(requestUserEmail)
                .orElseThrow(() -> new NoSuchElementException("해당 유저를 찾을 수 없습니다"));

        boolean isAdmin = user.getRole() == Role.ADMIN;
        boolean isReporter = issue.getReporter().getEmail().equals(user.getEmail());

        if(!isAdmin || !isReporter){
            throw new SecurityException("삭제 할 권한이 없습니다");
        }

        issueRepository.deleteById(issueId);
        // 추후 고민...
    }

    // 이슈 생성 로직
    public Issue createIssue(IssueRequest.Create request){

        // 보고자 ID -> 실제 회원이 있는가?
        User reporter = userRepository.findById(request.getReporterId())
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 사용자를 찾을 수 없습니다"));
        // 프로젝트 ID 검증
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new NoSuchElementException("해당 ID의 프로젝트를 찾을 수 없습니다"));

        Issue newIssue = new Issue();
        newIssue.setTitle(request.getTitle());
        newIssue.setDescription(request.getDescription());
        newIssue.setReporter(reporter);
        newIssue.setProject(project);
        // 이슈 --> TODO
        newIssue.setIssueStatus(IssueStatus.TODO);

        return issueRepository.save(newIssue);
    }

    // 모든 이슈 조회
    @Transactional(readOnly = true)
    public List<Issue> findIssues(){
        return issueRepository.findAll();
    }
}
