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
     * 이슈 수정 API
     * PUT /api/issues/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponseDto<IssueResponse.FindById>> updateIssue(
            @PathVariable(name = "id") Long id,
            @RequestBody IssueRequest.Update request) {

        Issue issue =  issueService.updateIssue(id, request);
        IssueResponse.FindById findByIdDto = new IssueResponse.FindById(issue);
        return ResponseEntity
                .ok(CommonResponseDto.success(findByIdDto,
                        "이슈가 성공적으로 변경 되었습니다"));
    }

    /**
     * 이슈 생성 API
     * POST /api/issues
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
