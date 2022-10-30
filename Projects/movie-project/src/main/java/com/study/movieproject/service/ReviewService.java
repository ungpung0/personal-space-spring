package com.study.movieproject.service;

import com.study.movieproject.dto.ReviewDTO;
import com.study.movieproject.entity.MemberEntity;
import com.study.movieproject.entity.MovieEntity;
import com.study.movieproject.entity.ReviewEntity;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getListOfMovie(Long movieId);

    Long register(ReviewDTO reviewDTO);

    void modify(ReviewDTO reviewDTO);

    void remove(Long reviewId);

    default ReviewEntity dtoToEntity(ReviewDTO reviewDTO) {
        ReviewEntity review = ReviewEntity.builder()
                .reviewId(reviewDTO.getReviewId())
                .movie(MovieEntity.builder().movieId(reviewDTO.getMovieId()).build())
                .member(MemberEntity.builder().memberId(reviewDTO.getMemberId()).build())
                .grade(reviewDTO.getGrade())
                .text(reviewDTO.getText())
                .build();

        return review;
    }

    default ReviewDTO entityToDto(ReviewEntity reviewEntity) {
        ReviewDTO review = ReviewDTO.builder()
                .reviewId(reviewEntity.getReviewId())
                .movieId(reviewEntity.getMovie().getMovieId())
                .memberId(reviewEntity.getMember().getMemberId())
                .nickname(reviewEntity.getMember().getNickName())
                .email(reviewEntity.getMember().getEmail())
                .grade(reviewEntity.getGrade())
                .text(reviewEntity.getText())
                .regDate(reviewEntity.getRegDate())
                .modDate(reviewEntity.getModDate())
                .build();

        return review;
    }
}
