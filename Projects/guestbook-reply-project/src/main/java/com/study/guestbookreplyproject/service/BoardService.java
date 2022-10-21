package com.study.guestbookreplyproject.service;

import com.study.guestbookreplyproject.dto.BoardDTO;
import com.study.guestbookreplyproject.dto.PageRequestDTO;
import com.study.guestbookreplyproject.dto.PageResultDTO;
import com.study.guestbookreplyproject.entity.BoardEntity;
import com.study.guestbookreplyproject.entity.MemberEntity;

public interface BoardService {
    Long register(BoardDTO boardDTO);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    BoardDTO getOne(Long bno);

    void removeWithReplies(Long bno);

    void modify(BoardDTO boardDTO);

    default BoardEntity dtoConverseEntity(BoardDTO boardDTO) { // BoardDTO를 BoardEntity로 변환하는 메소드.
        MemberEntity memberEntity = MemberEntity.builder().email(boardDTO.getWriterEmail()).build();

        BoardEntity boardEntity = BoardEntity.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(memberEntity)
                .build();

        return boardEntity;
    }

    default BoardDTO entityConverseDto(BoardEntity boardEntity, MemberEntity memberEntity, Long replyCount) { // BoardEntity를 BoardDTO로 변환하는 메소드.
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(boardEntity.getBno())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .regDate(boardEntity.getRegDate())
                .modDate(boardEntity.getModDate())
                .writerEmail(memberEntity.getEmail())
                .writerName(memberEntity.getName())
                .replyCount(replyCount.intValue())
                .build();

        return boardDTO;
    }
}
