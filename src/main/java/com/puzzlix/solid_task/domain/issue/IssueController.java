package com.puzzlix.solid_task.domain.issue;

import com.puzzlix.solid_task._global.dto.CommonResponseDto;
import com.puzzlix.solid_task.domain.issue.dto.IssueRequest;
import com.puzzlix.solid_task.domain.issue.dto.IssueResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    /**
     * 이슈 생성 API
     * Post /api/issues
     * */
    @PostMapping
    public ResponseEntity<CommonResponseDto<Issue>> createIssue(@RequestBody IssueRequest.Create request){

       Issue createdIssue = issueService.createIssue(request);

       return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponseDto.success(createdIssue));
    }

    /**
     * 이슈 목록 조회 API
     * GET /api/issues
     * */
    @GetMapping
    public ResponseEntity<CommonResponseDto<List<IssueResponse.FindAll>>> getIssues(){
        // 서비스에서 조회 요청
        List<Issue> issues = issueService.findIssues();

        // 조회된 도메인 이슈 리스트를 DTO로 변환
        List<IssueResponse.FindAll> responseDtos = IssueResponse.FindAll.from(issues);

        return ResponseEntity.ok(CommonResponseDto.success(responseDtos));
    }

}
