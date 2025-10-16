package com.puzzlix.solid_task.domain.board.dto;

import lombok.Getter;
import lombok.Setter;

public class BoardRequest {

    @Getter
    @Setter
    public static class CreateAndUpdate{
        String title;
        String content;
        Long writerId;
        // 날짜는 등록시 서버에서 알아서 처리 => BaseEntity
    }
}
