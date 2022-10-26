package com.study.movieproject.repository;

import com.study.movieproject.entity.MemberEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMembers() {
        // 멤버 데이터 삽입.
        IntStream.rangeClosed(1, 100).forEach(i -> {
            MemberEntity member = MemberEntity.builder()
                    .email("email" + i + "@test.com")
                    .password("1111")
                    .nickName("reviewer" + i)
                    .build();
            memberRepository.save(member);
        });
    }


}
