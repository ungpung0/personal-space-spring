package com.study.movieproject.controller;

import com.study.movieproject.dto.ReviewDTO;
import com.study.movieproject.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Log4j2
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{memberId}/all")
    public ResponseEntity<List<ReviewDTO>> getList(@PathVariable("memberId")Long memberId) {
        log.info("Get List...");

        List<ReviewDTO> reviewDTOList = reviewService.getListOfMovie(memberId);

        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
    }

    @PostMapping("/{memberId}")
    public ResponseEntity<Long> addReivew(@RequestBody ReviewDTO reviewDTO) {
        log.info("Add Review...");

        Long reviewId = reviewService.register(reviewDTO);

        return new ResponseEntity<>(reviewId, HttpStatus.OK);
    }

    @PutMapping("/{memberId}/{reviewId}")
    public ResponseEntity<Long> modifyReview(@PathVariable Long reviewId, @RequestBody ReviewDTO reviewDTO) {
        log.info("Modify Review...");

        reviewService.modify(reviewDTO);

        return new ResponseEntity<>(reviewId, HttpStatus.OK);
    }

    @DeleteMapping("/{memberId}/{reviewId}")
    public ResponseEntity<Long> removeReview(@PathVariable Long reviewId) {
        log.info("Remove Review...");

        reviewService.remove(reviewId);

        return new ResponseEntity<>(reviewId, HttpStatus.OK);
    }
}
