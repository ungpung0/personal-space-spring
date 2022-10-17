package com.study.guestbookproject.controller;

import com.study.guestbookproject.dto.PageRequestDTO;
import com.study.guestbookproject.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService guestbookService;

    @GetMapping("/")
    public String index() {
        log.info("index log");
        return "redirect:/guestbook/list"; // list 페이지로 리다이렉션.
    }
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list log");
        model.addAttribute("result", guestbookService.getList(pageRequestDTO));
        // result란 이름으로 목록 리스트 전달.
    }

}
