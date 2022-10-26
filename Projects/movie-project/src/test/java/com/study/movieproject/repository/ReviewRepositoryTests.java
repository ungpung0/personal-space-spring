package com.study.movieproject.repository;

import com.study.movieproject.entity.MemberEntity;
import com.study.movieproject.entity.MovieEntity;
import com.study.movieproject.entity.ReviewEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertReviews() {
        IntStream.rangeClosed(1, 200).forEach(i -> {
            // movieId 무작위로 선택. (1 ~ 100)
            Long mno = (long)(Math.random() * 100) + 1;
            MovieEntity movie = MovieEntity.builder().movieId(mno).build();

            // memberId 무작위로 선택. (1 ~ 100)
            Long mid = (long)(Math.random() * 100) + 1;
            MemberEntity member = MemberEntity.builder().memberId(mid).build();

            // 리뷰 데이터 삽입.
            int grade = (int)(Math.random() * 5) + 1;
            ReviewEntity review = ReviewEntity.builder()
                    .member(member)
                    .movie(movie)
                    .grade(grade)
                    .text("ReviewTest" + i)
                    .build();
            reviewRepository.save(review);
        });
    }
}
