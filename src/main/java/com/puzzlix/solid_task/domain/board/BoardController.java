package com.puzzlix.solid_task.domain.board;

import com.puzzlix.solid_task._global.dto.CommonResponseDto;
import com.puzzlix.solid_task.domain.board.dto.BoardRequest;
import com.puzzlix.solid_task.domain.board.dto.BoardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    /**
     * Board 전체 목록 조회 API (페이징 처리 - 10개씩 최신순 정렬)
     * GET api/boards/
     * */

    @GetMapping
    public ResponseEntity<?> getBoards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        // 전체 게시글 리스트 조회
        Page<BoardResponse.FindAll> boards = boardService.findBoards(page, size);

        return ResponseEntity.ok(CommonResponseDto.success(boards));
    }

    /**
     * Board 상세조회 API
     * GET api/boards/{id}
     * */

    @GetMapping("/{id}")
    public ResponseEntity<?> getBoardDetail(@PathVariable(name = "id") Long boardId){
        // id로 게시글 조회
        BoardResponse.FindById board = boardService.findBoards(boardId);

        return ResponseEntity.ok(CommonResponseDto.success(board));
    }

    /**
     * Board 생성 API
     * POST api/boards
     * */
    @PostMapping
    public ResponseEntity<?> createBoard(@RequestBody BoardRequest.CreateAndUpdate request){
        BoardResponse.FindById createBoard = boardService.createBoard(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponseDto.success(createBoard));
    }

    /**
     * Board 수정 API
     * PUT api/boards/{id}
     * */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBoard(
            @PathVariable(name ="id") Long boardId,
            @RequestBody BoardRequest.CreateAndUpdate request
    ){
        BoardResponse.FindById board = boardService.updateBoard(boardId, request);

        return ResponseEntity.ok(CommonResponseDto.success(board, "게시글이 성공적으로 변경 되었습니다"));
    }


    /**
     * Board 삭제 API
     * DELETE api/boards/{id}
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable(name = "id") Long boardId,
                                         @RequestAttribute("userEmail") String userEmail){
        boardService.deleteIssue(boardId, userEmail);

        return ResponseEntity.ok(CommonResponseDto.success(null, "게시글이 성공적으로 삭제 되었습니다"));
    }
}
