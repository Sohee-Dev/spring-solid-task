package com.puzzlix.solid_task.domain.board.dto;

import com.puzzlix.solid_task.domain.board.Board;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BoardResponse {

    @Getter
    public static class FindAll{
        private final Long id;
        private final String title;
        private final String content;
        private final String writer;


        private FindAll(Board board){
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.writer = board.getWriter().getName();
        }

        public static List<FindAll> from(List<Board> boards){
            List<FindAll> dtoList = new ArrayList<>();
            for(Board board :  boards){
                dtoList.add(new FindAll(board));
            }
            return dtoList;
        }
    }

    public static class FindById{
        private final Long id;
        private final String title;
        private final String content;
        private final String writer;
        private final LocalDateTime regdate;
        private final LocalDateTime moddate;

        public FindById(Board board){
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.writer = board.getWriter().getName();
            this.regdate = board.getCreatedAt();
            this.moddate = board.getModifiedAt();
        }
    }


}
