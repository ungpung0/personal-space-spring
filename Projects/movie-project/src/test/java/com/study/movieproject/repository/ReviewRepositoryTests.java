package com.study.movieproject.repository;

import com.study.movieproject.entity.MemberEntity;
import com.study.movieproject.entity.MovieEntity;
import com.study.movieproject.entity.ReviewEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;
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

    @Test
    public void selectFindByMovies() {
        MovieEntity movie = MovieEntity.builder().movieId(94L).build();

        List<ReviewEntity> result = reviewRepository.findByMovie(movie);

        result.forEach(review -> {
            System.out.print(review.getReviewId());
            System.out.print("\t" + review.getGrade());
            System.out.print("\t" + review.getText());
            System.out.print("\t" + review.getMember().getEmail());
            System.out.println("");
        });
    }

    @Commit
    @Transactional
    @Test
    public void deleteByMember() {
        Long mid = 1L;
        MemberEntity member = MemberEntity.builder().memberId(mid).build();

        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);
    }
}
