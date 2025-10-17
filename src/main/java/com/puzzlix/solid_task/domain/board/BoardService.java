package com.puzzlix.solid_task.domain.board;

import com.puzzlix.solid_task.domain.board.dto.BoardRequest;
import com.puzzlix.solid_task.domain.board.dto.BoardResponse;
import com.puzzlix.solid_task.domain.user.Role;
import com.puzzlix.solid_task.domain.user.User;
import com.puzzlix.solid_task.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    // 게시글 생성 로직
    public BoardResponse.FindById createBoard(BoardRequest.CreateAndUpdate request){
        // 실제 작성자 검증
        User writer = userRepository.findById(request.getWriterId())
                .orElseThrow(() -> new NoSuchElementException("해당 관리자를 찾을 수 없습니다"));

        boolean isAdmin = writer.getRole() == Role.ADMIN;

        // 관리자만 작성 할 수 있도록 처리
        if(!isAdmin){
            throw new SecurityException("작성 할 권한이 없습니다");
        }
        Board newBoard = new Board();
        newBoard.setTitle(request.getTitle());
        newBoard.setContent(request.getContent());
        newBoard.setWriter(writer);
        boardRepository.save(newBoard);
        return new BoardResponse.FindById(newBoard);
    }

    // 게시글 수정 로직
    public BoardResponse.FindById updateBoard(Long boardId, BoardRequest.CreateAndUpdate request){

        User requestUser = userRepository.findById(request.getWriterId())
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다"));

        Board modBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("게시글을 찾을 수 없습니다"));

        boolean isAdmin = requestUser.getRole() == Role.ADMIN;

        if(!isAdmin){
            throw new SecurityException("게시글을 수정 할 권한이 없습니다");
        }
        modBoard.setTitle(request.getTitle());
        modBoard.setContent(request.getContent());
        boardRepository.save(modBoard);

        return new BoardResponse.FindById(modBoard);
    }

    // 게시글 삭제 로직
    public void deleteIssue(Long boardId, String requestUserEmail) {

        User user = userRepository.findByEmail(requestUserEmail)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다"));
        System.out.println("User: " + user.toString());

        // delete는 조회해서 없어도 오류가 안뜨기 때문에 Board는 조회를 안해도 됨
        boolean isAdmin = user.getRole() == Role.ADMIN;

        if(!isAdmin){
            throw new SecurityException("게시글을 삭제 할 권한이 없습니다");
        }
        boardRepository.deleteById(boardId);

    }

    // 모든 게시글 조회
    @Transactional(readOnly = true)
    public Page<BoardResponse.FindAll> findBoards(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Board> boards = boardRepository.findAllByOrderByCreatedAtDesc(pageable);
        return boards.map(BoardResponse.FindAll::from);
    }

    // 게시글 상세 조회(id로)
    @Transactional(readOnly = true)
    public BoardResponse.FindById findBoards(Long boardId){
         Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("해당 게시글을 찾을 수 없습니다"));

        return new BoardResponse.FindById(board);
    }
}
