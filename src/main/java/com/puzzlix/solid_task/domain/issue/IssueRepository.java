package com.puzzlix.solid_task.domain.issue;

import java.util.List;
import java.util.Optional;

/**
 * 저장소 역할(규칙)을 정의하는 인터페이스
 * */

public interface IssueRepository {
    Issue save(Issue issue);
    Optional<Issue> findById(Long id);
    List<Issue> findAll();
}
