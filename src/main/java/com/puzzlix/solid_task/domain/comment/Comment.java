package com.puzzlix.solid_task.domain.comment;

import com.puzzlix.solid_task.domain.issue.Issue;
import com.puzzlix.solid_task.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = {"issue", "writer"}) // 순환 참조 방지(양방향 매핑시 조심해야 될 부분)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    // 어디 이슈에 댓글이 달렸는가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    private Issue issue;

    // 누가작성했는가?
    // 이걸전략, 레이지 전략
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;
}
