package com.puzzlix.solid_task.domain.board;

import com.puzzlix.solid_task.domain.board.dto.BoardRequest;
import com.puzzlix.solid_task.domain.user.Role;
import com.puzzlix.solid_task.domain.user.User;
import com.puzzlix.solid_task.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    // 게시글 생성 로직
    public Board createBoard(BoardRequest.CreateAndUpdate request){
        // 실제 작성자 검증
        User writer = userRepository.findById(request.getWriterId())
                .orElseThrow(() -> new NoSuchElementException("해당 관리자를 찾을 수 없습니다"));

        boolean isAdmin = writer.getRole() == Role.ADMIN;

        // 관리자만 작성 할 수 있도록 처리
        if(isAdmin){
            Board newBoard = new Board();
            newBoard.setContent(request.getContent());
            newBoard.setContent(request.getContent());
            newBoard.setWriter(writer);
            return boardRepository.save(newBoard);
        }

        throw new SecurityException("작성 할 권한이 없습니다");

    }

    // 게시글 수정 로직
    public Board updateBoard(Long boardId, BoardRequest.CreateAndUpdate request){

        User requestUser = userRepository.findById(request.getWriterId())
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다"));

        Board modBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("게시글을 찾을 수 없습니다"));

        boolean isAdmin = requestUser.getRole() == Role.ADMIN;

        if(isAdmin){
             modBoard.setTitle(request.getTitle());
             modBoard.setContent(request.getContent());
             return boardRepository.save(modBoard);
        }

        throw new SecurityException("게시글을 수정 할 권한이 없습니다");
    }

    // 게시글 삭제 로직
    public void deleteIssue(Long boardId, String requestUserEmail) {

        User user = userRepository.findByEmail(requestUserEmail)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다"));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("게시글을 찾을 수 없습니다"));

        boolean isAdmin = user.getRole() == Role.ADMIN;

        if(isAdmin){
            boardRepository.deleteById(boardId);
        }
        throw new SecurityException("게시글을 삭제 할 권한이 없습니다");
    }
}
