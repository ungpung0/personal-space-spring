package com.study.guestbookreplyproject.repository;

import com.study.guestbookreplyproject.entity.BoardEntity;
import com.study.guestbookreplyproject.entity.MemberEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    BoardRepository boardRepository;
    @Test // 100 개의 데이터 삽입.
    public void insertTest() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            MemberEntity memberEntity = MemberEntity.builder().email("user" + i + "@aaa.com").build();

            BoardEntity boardEntity = BoardEntity.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .writer(memberEntity)
                    .build();

            boardRepository.save(boardEntity);
        });
    }

    @Transactional // 지연 로딩과 같이 데이터 베이스의 연결이 다시 필요할 때 사용한다.
    @Test // bno = 1 조회하기.
    public void testRead1() {
        Optional<BoardEntity> result = boardRepository.findById(1L);

        BoardEntity boardEntity = result.get();

        System.out.println(boardEntity);
        System.out.println(boardEntity.getWriter()); // getWriter()로 해당 member를 출력한다.
    }

    @Test // Repository에서 생성한 LEFT JOIN 예제를 출력한다.
    public void testReadWithWriter() {
        Object result = boardRepository.getBoardWithWriter(100L);
        Object[] array = (Object[]) result;

        System.out.println("");
        System.out.println(Arrays.toString(array));
    }

    @Test // Repository에서 생성한 ON 예제를 출력한다.
    public void testGetBoardWithReply() {
        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for(Object[] data : result) {
            System.out.println(Arrays.toString(data));
        }
    }

    @Test
    public void testGetWithReplyCount() {
        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {
            Object[] array = (Object[]) row;
            System.out.println(Arrays.toString(array));
        });
    }

    @Test
    public void testRead3() {
        Object result = boardRepository.getBoardByBno(100L);

        Object[] array = (Object[]) result;

        System.out.println(Arrays.toString(array));
    }

}
