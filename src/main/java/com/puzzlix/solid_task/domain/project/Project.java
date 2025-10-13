package com.puzzlix.solid_task.domain.project;

import com.puzzlix.solid_task.domain.issue.Issue;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = "issues")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    //fk 주인이 아님을 명시
    @OneToMany(mappedBy = "project")
    private List<Issue> issues = new ArrayList<>();
}
