package com.study.guestbookproject.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.study.guestbookproject.entity.Guestbook;
import com.study.guestbookproject.entity.QGuestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    @Test // 단일 항목 검색 테스트.
    public void selectOne() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno"));

        QGuestbook qGuestbook = QGuestbook.guestbook;                         // Q도메인 클래스를 불러온다.
        BooleanBuilder booleanBuilder = new BooleanBuilder();                 // 비어있을 경우를 대비하여 BooleanBuilder를 생성한다.
        BooleanExpression booleanExpression = qGuestbook.title.contains("1"); // BooleanExpression으로 WHERE 절에서 사용할 파라미터를 받아온다.
        booleanBuilder.and(booleanExpression);                                // 둘을 사용하여 동적쿼리를 생성한다.

        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder, pageable);
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

    @Test // 다중 항목 검색 테스트.
    public void selectMulti() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        QGuestbook qGuestbook = QGuestbook.guestbook;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expressionTitle = qGuestbook.title.contains("1");
        BooleanExpression expressionContent = qGuestbook.content.contains("1");
        BooleanExpression expressionSum = expressionTitle.or(expressionContent); // OR 절 사용.

        booleanBuilder.and(expressionSum);
        booleanBuilder.and(qGuestbook.gno.gt(0L)); // gno 값이 0보다 클 때.

        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder, pageable);
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }
}
