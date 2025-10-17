package com.puzzlix.solid_task.domain.issue;

import com.puzzlix.solid_task._global.dto.CommonResponseDto;
import com.puzzlix.solid_task.domain.issue.dto.IssueRequest;
import com.puzzlix.solid_task.domain.issue.dto.IssueResponse;
import com.puzzlix.solid_task.domain.user.Role;
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
     * 특정 이슈 상태 변경 API (담당자가 진행 상태 변경)
     * 주소 설계 - http://localhost:8080/api/issues/{id}/status?=DONE
     * HTTP 메서드 - GET, DELETE는 body가 없다
     * 나머지는 body가 있다 PATCH는 굳이 필요없으면 body를 작성 안해도 됨
     * */
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateIssueStatus(
            @PathVariable(name = "id") Long issueId,
            @RequestParam("status") IssueStatus newStatus,
            @RequestAttribute("userEmail") String userEmail,
            @RequestAttribute("userRole") Role userRole
    ){

        IssueResponse.FindById issue = issueService.updateIssueStatus(issueId, newStatus, userEmail, userRole);
        // 서비스 호출
        return ResponseEntity.ok(CommonResponseDto
                .success(issue, "이슈 상태가 성공적으로 변경 되었습니다"));
    }


    /**
     * 이슈 수정 API
     * PUT /api/issues/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponseDto<IssueResponse.FindById>> updateIssue(
            @PathVariable(name = "id") Long id,
            @RequestBody IssueRequest.Update request,
            @RequestAttribute("userEmail") String userEmail) {

        IssueResponse.FindById issue =  issueService.updateIssue(id, request, userEmail);

        return ResponseEntity
                .ok(CommonResponseDto.success(issue,
                        "이슈가 성공적으로 변경 되었습니다"));
    }

    /**
     * 이슈 삭제 API
     * DELETE/api/issues/{id}
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIssue(
            @PathVariable(name="id") Long id,
            @RequestAttribute("userEmail") String userEmail,
            @RequestAttribute("userRole") Role userRole){
//        if(userRole != Role.ADMIN){
//            return ResponseEntity.status(HttpStatus.FORBIDDEN)
//                    .body(CommonResponseDto.error("삭제 권한이 없습니다"));
//        }
        issueService.deleteIssue(id, userEmail);
        return ResponseEntity.ok(CommonResponseDto
                .success(null, "이슈가 성공적으로 삭제 되었습니다"));
    }

    /**
     * 이슈 생성 API
     * POST /api/issues
     * */
    @PostMapping
    public ResponseEntity<?> createIssue(@RequestBody IssueRequest.Create request){

       IssueResponse.FindById createdIssue = issueService.createIssue(request);

       return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponseDto.success(createdIssue));
    }

    /**
     * 이슈 목록 조회 API
     * GET /api/issues
     * */
    @GetMapping
    public ResponseEntity<CommonResponseDto<List<IssueResponse.FindAll>>> getIssues(){
        // 서비스에서 조회 요청
        // 조회된 도메인 이슈 리스트를 DTO로 변환 --> Service에서 DTO로 반환하도록 변경
        List<IssueResponse.FindAll> responseDtos = issueService.findIssues();

        return ResponseEntity.ok(CommonResponseDto.success(responseDtos));
    }

}
