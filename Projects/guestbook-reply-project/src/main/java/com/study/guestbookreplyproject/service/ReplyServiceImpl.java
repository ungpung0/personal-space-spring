package com.study.guestbookreplyproject.service;

import com.study.guestbookreplyproject.dto.ReplyDTO;
import com.study.guestbookreplyproject.entity.BoardEntity;
import com.study.guestbookreplyproject.entity.ReplyEntity;
import com.study.guestbookreplyproject.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;

    @Override
    public Long register(ReplyDTO replyDTO) {
        ReplyEntity replyEntity = dtoConverseEntity(replyDTO);

        replyRepository.save(replyEntity);

        return replyEntity.getRno();
    }

    @Override
    public List<ReplyDTO> getList(Long bno) {
        List<ReplyEntity> result = replyRepository
                .getReplyEntitiesByBoardOrderByRno(BoardEntity.builder().bno(bno).build());

        return result.stream().map(reply -> entityConverseDto(reply)).collect(Collectors.toList());
    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        ReplyEntity replyEntity = dtoConverseEntity(replyDTO);

        replyRepository.save(replyEntity);
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }

}
