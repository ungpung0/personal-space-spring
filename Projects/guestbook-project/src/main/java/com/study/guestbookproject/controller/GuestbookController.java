package com.study.guestbookproject.controller;

import com.study.guestbookproject.dto.GuestbookDTO;
import com.study.guestbookproject.dto.PageRequestDTO;
import com.study.guestbookproject.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/register")
    public void register() {
        log.info("register log");
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO guestbookDTO, RedirectAttributes redirectAttributes) {
        log.info("register post log");
        Long gno = guestbookService.register(guestbookDTO);
        redirectAttributes.addFlashAttribute("msg", gno); // 모달 창을 띄우는 용도로 사용.
        return "redirect:/guestbook/list";
    }

}
