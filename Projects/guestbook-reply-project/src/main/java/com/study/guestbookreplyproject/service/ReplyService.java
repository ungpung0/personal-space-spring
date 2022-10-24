package com.study.guestbookreplyproject.service;

import com.study.guestbookreplyproject.dto.ReplyDTO;
import com.study.guestbookreplyproject.entity.BoardEntity;
import com.study.guestbookreplyproject.entity.ReplyEntity;

import java.util.List;

public interface ReplyService {
    Long register(ReplyDTO replyDTO);
    List<ReplyDTO> getList(Long bno);
    void modify(ReplyDTO replyDTO);
    void remove(Long rno);

    default ReplyEntity dtoConverseEntity(ReplyDTO replyDTO) {
        BoardEntity boardEntity = BoardEntity.builder().bno(replyDTO.getBno()).build();

        ReplyEntity replyEntity = ReplyEntity.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .board(boardEntity)
                .build();

        return replyEntity;
    }

    default ReplyDTO entityConverseDto(ReplyEntity replyEntity) {
        ReplyDTO replyDTO = ReplyDTO.builder()
                .rno(replyEntity.getRno())
                .text(replyEntity.getText())
                .replyer(replyEntity.getReplyer())
                .regDate(replyEntity.getRegDate())
                .modDate(replyEntity.getModDate())
                .build();

        return replyDTO;
    }

}
