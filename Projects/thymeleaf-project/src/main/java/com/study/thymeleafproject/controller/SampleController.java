package com.study.thymeleafproject.controller;

import com.study.thymeleafproject.dto.SampleDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller                   // @Controller: 이 클래스가 Controller 클래스 임을 명시한다.
@RequestMapping("/sample") // @RequestMapping("URL"): 경로와 컨트롤러를 연결하는 매핑 역할을 한다.
@Log4j2                       // @Log4j2: Lombok의 로그 라이브러리의 일종이다.
public class SampleController {

    @GetMapping("/example1") // @GetMapping: HTTP GET 요청을 특정 메소드에 매핑한다.
    public void example1() {
        log.info("example1 Log...");
    }

    @GetMapping({"/example2", "/exLink"}) // @GetMapping 속성을 {}로 지정하면 여러 URL에 뿌려줄 수 있다.
    public void exampleModel(Model model) { // Model 객체는 request.setAttribute와 비슷한 역할을 수행하며, 스프링이 모델을 자동생성한다.

        List<SampleDTO> list = IntStream.rangeClosed(1, 20).asLongStream().mapToObj(i -> { // asLongStream으로 IntStream의 int 요소를 long 타입으로 변환할 수 있다.
           SampleDTO dto = SampleDTO.builder()
                   .sampleId(i).first("First..." + i).last("Last..." + i).registerTime(LocalDateTime.now()).build();
           return dto;
        }).collect(Collectors.toList()); // collect()로 요소들을 필터링하여 List 객체로 수집하였다.
        model.addAttribute("list", list); // 마지막으로 Model 객체에 담아서 전송한다.
    }

    @GetMapping({"/exInline"})
    public String exampleInline(RedirectAttributes redirectAttributes) { // 리다이렉션에 사용할 속성.
        log.info("exampleInline Log...");

        SampleDTO dto = SampleDTO.builder()
                .sampleId(100L).first("First...100").last("Last...100").registerTime(LocalDateTime.now()).build();
        redirectAttributes.addFlashAttribute("result", "success");
        redirectAttributes.addFlashAttribute("dto", dto);
        return "redirect:/sample/example3"; // example3 페이지로 리다이렉션.
    }

    @GetMapping("/example3")
    public void example3() {
        log.info("example3 Log...");
    }

    @GetMapping("/exLayout1")
    public void exLayout1() {
        log.info("exLayout1 Log...");
    }

}
