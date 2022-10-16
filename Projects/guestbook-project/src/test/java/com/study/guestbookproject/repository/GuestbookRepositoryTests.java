package com.study.guestbookproject.repository;

import com.study.guestbookproject.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test // 300 개의 데이터 저장 테스트.
    public void insertDummies() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            Guestbook guestbook = Guestbook.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer("Writer..." + (i % 10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });
    }

    @Test // 데이터 수정 테스트.
    public void updateTest() {
        Optional<Guestbook> result = guestbookRepository.findById(300L);
        if(result.isPresent()) {
            Guestbook guestbook = result.get();
            guestbook.changeTitle("ChangedTitle...");
            guestbook.changeContent("ChangedContent...");
            guestbookRepository.save(guestbook);
        }
    }
}
