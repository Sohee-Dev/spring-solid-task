package com.puzzlix.solid_task.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일 중복 여부 확인 - 메서드 쿼리(Query Method)
    Optional<User> findByEmail(String email);

}
