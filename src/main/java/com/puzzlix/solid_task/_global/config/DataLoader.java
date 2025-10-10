package com.puzzlix.solid_task._global.config;

import com.puzzlix.solid_task.domain.issue.Issue;
import com.puzzlix.solid_task.domain.issue.IssueRepository;
import com.puzzlix.solid_task.domain.issue.IssueStatus;
import com.puzzlix.solid_task.domain.project.Project;
import com.puzzlix.solid_task.domain.project.ProjectRepository;
import com.puzzlix.solid_task.domain.user.User;
import com.puzzlix.solid_task.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Profile("local")
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final IssueRepository issueRepository;

    @Override
    public void run(String... args) throws Exception {
        // 샘플 데이터를 추가할 수 있다.
        // User 2개, Project 1개
        // Issue 샘플 만들어보기

        User testUser1 = userRepository.save(new User(null, "홍길동", "test1@naver.com", "1234",new ArrayList<>()));
        User testUser2 = userRepository.save(new User(null, "이순신", "test2@naver.com", "1234",new ArrayList<>()));

        Project testProject = projectRepository.save(new Project(null, "SOLID Task 프로젝트", "SOLID 개념 학습", new ArrayList<>()));

        issueRepository.save(new Issue(null, "로그인 기능 구현", "JWT 필요", IssueStatus.TODO, testProject, testUser1, null, new ArrayList<>()));
        issueRepository.save(new Issue(null, "검색 기능 구현 요청", "이슈 전체 목록에 검색 기능이 필요합니다", IssueStatus.TODO, testProject, testUser1, null, new ArrayList<>()));
    }
}
