package com.study.clubproject.controller;

import com.study.clubproject.security.dto.ClubAuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/sample/")
public class SampleController {
    @PreAuthorize("permitAll()") // 접근 권한 = 모두.
    @GetMapping("/all") // 로그인 하지 않은 사용자가 접근할 수 있는 경로.
    public void exAll() {
        log.info("exAll()...");
    }

    @GetMapping("/member") // 로그인한 사용자만 접근할 수 있는 경로.
    public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember) {
        log.info("exMember()...");

        log.info(clubAuthMember);
    }

    @PreAuthorize("hasRole('ADMIN')") // 접근 권한 = 관리자.
    @GetMapping("/admin") // 관리자만이 접근할 수 있는 경로.
    public void exAdmin() {
        log.info("exAdmin()...");
    }

    @PreAuthorize("#clubAuthMemberDTO != null && #clubAuthMemberDTO.username eq \"user95@email.com\"")
    @GetMapping("/exOnly")
    public String exMemberOnly(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMemberDTO) {
        log.info("exMemberOnly()...");

        log.info(clubAuthMemberDTO);

        return "/sample/admin";
    }
}
