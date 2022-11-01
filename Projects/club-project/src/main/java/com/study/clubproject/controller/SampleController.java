package com.study.clubproject.controller;

import com.study.clubproject.security.dto.ClubAuthMemberDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/sample/")
public class SampleController {
    @GetMapping("/all") // 로그인 하지 않은 사용자가 접근할 수 있는 경로.
    public void exAll() {
        log.info("exAll()...");
    }

    @GetMapping("/member") // 로그인한 사용자만 접근할 수 있는 경로.
    public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember) {
        log.info("exMember()...");

        log.info(clubAuthMember);
    }

    @GetMapping("/admin") // 관리자만이 접근할 수 있는 경로.
    public void exAdmin() {
        log.info("exAdmin()...");
    }
}
