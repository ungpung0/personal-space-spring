package com.study.guestbookreplyproject.repository;

import com.study.guestbookreplyproject.entity.MemberEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test // 100 개의 데이터 삽입.
    public void insertTest() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            MemberEntity memberEntity = MemberEntity.builder()
                    .email("user" + i + "@aaa.com")
                    .name("name" + i)
                    .password("1111")
                    .build();

            memberRepository.save(memberEntity);
        });
    }


}
