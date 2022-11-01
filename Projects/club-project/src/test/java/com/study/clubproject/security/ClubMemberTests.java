package com.study.clubproject.security;

import com.study.clubproject.entity.ClubMember;
import com.study.clubproject.entity.ClubMemberRole;
import com.study.clubproject.repository.ClubMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ClubMemberTests {
    @Autowired
    ClubMemberRepository clubMemberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertTest() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            ClubMember clubMember = ClubMember.builder()
                    .email("USER" + i + "@email.com")
                    .name("USER" + i)
                    .formSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();

            clubMember.addMemberRole(ClubMemberRole.USER); // 기본값 USER 설정.

            if(i > 80)
                clubMember.addMemberRole(ClubMemberRole.MANAGER);
            if(i > 90)
                clubMember.addMemberRole(ClubMemberRole.ADMIN);

            clubMemberRepository.save(clubMember);
        });
    }

    @Test
    public void readTest() {
        Optional<ClubMember> result = clubMemberRepository.findByEmail("USER95@email.com", false);

        ClubMember clubMember = result.get();

        System.out.println(clubMember);
    }
}
