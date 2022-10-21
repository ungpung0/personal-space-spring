package com.study.guestbookreplyproject.service;

import com.study.guestbookreplyproject.dto.BoardDTO;
import com.study.guestbookreplyproject.dto.PageRequestDTO;
import com.study.guestbookreplyproject.dto.PageResultDTO;
import com.study.guestbookreplyproject.entity.BoardEntity;
import com.study.guestbookreplyproject.entity.MemberEntity;
import com.study.guestbookreplyproject.repository.BoardRepository;
import com.study.guestbookreplyproject.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO boardDTO) {
        log.info(boardDTO);

        BoardEntity boardEntity = dtoConverseEntity(boardDTO);

        boardRepository.save(boardEntity);

        return boardEntity.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);

        Function<Object[], BoardDTO> function = (entity -> entityConverseDto(
                (BoardEntity) entity[0], (MemberEntity) entity[1], (Long) entity[2]));

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(
                pageRequestDTO.getPageable(Sort.by("bno").descending()));

        return new PageResultDTO<>(result, function);
    }

    @Override
    public BoardDTO getOne(Long bno) {
        Object result = boardRepository.getBoardByBno(bno);

        Object[] array = (Object[]) result;

        return entityConverseDto((BoardEntity) array[0], (MemberEntity) array[1], (Long) array[2]);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {
        replyRepository.deleteByBno(bno);

        boardRepository.deleteById(bno);
    }

    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {
        BoardEntity boardEntity = boardRepository.getOne(boardDTO.getBno());

        boardEntity.changeTitle(boardDTO.getTitle());

        boardEntity.changeContent(boardDTO.getContent());

        boardRepository.save(boardEntity);
    }
}