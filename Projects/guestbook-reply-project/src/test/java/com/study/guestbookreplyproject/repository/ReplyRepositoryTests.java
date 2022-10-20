package com.study.guestbookreplyproject.repository;

import com.study.guestbookreplyproject.entity.BoardEntity;
import com.study.guestbookreplyproject.entity.ReplyEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    ReplyRepository replyRepository;

    @Test // 무작위 board에 해당하는 300개의 데이터를 삽입한다.
    public void insertTest() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            long bno = (long)(Math.random() * 100) + 1;
            BoardEntity boardEntity = BoardEntity.builder().bno(bno).build();

            ReplyEntity replyEntity = ReplyEntity.builder()
                    .text("Reply" + i)
                    .board(boardEntity)
                    .replyer("guest")
                    .build();

            replyRepository.save(replyEntity);
        });
    }

    @Test
    public void readTest1() {
        Optional<ReplyEntity> result = replyRepository.findById(601L);

        ReplyEntity replyEntity = result.get();

        System.out.println(replyEntity);
        System.out.println(replyEntity.getBoard());
    }

}
