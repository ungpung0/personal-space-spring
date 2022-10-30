package com.study.movieproject.service;

import com.study.movieproject.dto.ReviewDTO;
import com.study.movieproject.entity.MovieEntity;
import com.study.movieproject.entity.ReviewEntity;
import com.study.movieproject.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewDTO> getListOfMovie(Long movieId) {
        MovieEntity movie = MovieEntity.builder().movieId(movieId).build();

        List<ReviewEntity> result = reviewRepository.findByMovie(movie);

        return result.stream().map(review -> entityToDto(review)).collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDTO reviewDTO) {
        ReviewEntity review = dtoToEntity(reviewDTO);

        reviewRepository.save(review);

        return review.getReviewId();
    }

    @Override
    public void modify(ReviewDTO reviewDTO) {
        Optional<ReviewEntity> result = reviewRepository.findById(reviewDTO.getReviewId());

        if(result.isPresent()) {
            ReviewEntity review = result.get();
            review.changeGrade(reviewDTO.getGrade());
            review.changeText(reviewDTO.getText());

            reviewRepository.save(review);
        }
    }

    @Override
    public void remove(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
