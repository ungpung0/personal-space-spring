package com.study.movieproject.controller;

import com.study.movieproject.dto.MovieDTO;
import com.study.movieproject.dto.PageRequestDTO;
import com.study.movieproject.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/movie")
@Log4j2
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    @GetMapping("/register")
    public void register() {

    }

    @PostMapping("/register")
    public String register(MovieDTO movieDTO, RedirectAttributes redirectAttributes) {
        log.info("movieDTO: " + movieDTO);

        Long movieId = movieService.register(movieDTO);
        redirectAttributes.addFlashAttribute("msg", movieId);

        return "redirect:/movie/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("pageRequestDTO: " + pageRequestDTO);

        model.addAttribute("result", movieService.getList(pageRequestDTO));
    }

    @GetMapping({"/read", "/modify"})
    public void read(long movieId, @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO, Model model) {
        log.info("movieId: " + movieId);

        MovieDTO movieDTO = movieService.getMovie(movieId);

        model.addAttribute("dto: " + movieDTO);
    }
}
