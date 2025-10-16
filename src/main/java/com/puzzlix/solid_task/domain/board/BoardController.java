package com.puzzlix.solid_task.domain.board;

import com.puzzlix.solid_task._global.dto.CommonResponseDto;
import com.puzzlix.solid_task.domain.board.dto.BoardRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    /**
     * Board 생성 API
     * POST api/boards
     * */
    @PostMapping
    public ResponseEntity<?> createBoard(@RequestBody BoardRequest.CreateAndUpdate request){
        Board createBoard = boardService.createBoard(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponseDto.success(createBoard));
    }

    /**
     * Board 수정 API
     * PUT api/boards/{id}
     * */
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateBoard(
//            @PathVariable(name ="id") Long boardId,
//            @RequestBody BoardRequest.CreateAndUpdate request
//    ){
//
//    }
}
