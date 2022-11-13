package com.study.bimovie.repository;

import com.study.bimovie.entity.MovieEntity;
import com.study.bimovie.entity.PosterEntity;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MovieRepositoryTests {
    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void testInsert() {
        log.info("Test Insert...");

        MovieEntity movie = MovieEntity.builder().title("극한직업").build();

        movie.addPoster(PosterEntity.builder().fname("극한직업1.jpg").build());
        movie.addPoster(PosterEntity.builder().fname("극한직업2.jpg").build());

        movieRepository.save(movie);

        log.info(movie.getMno());
    }

    @Test
    public void insertMovies() {
        IntStream.rangeClosed(10, 100).forEach(i -> {
            MovieEntity movie = MovieEntity.builder().title("세계명작선" + i).build();

            movie.addPoster(PosterEntity.builder().fname("세계명작선" + i + "_포스터1.jpg").build());
            movie.addPoster(PosterEntity.builder().fname("세계명작선" + i + "_포스터2.jpg").build());

            movieRepository.save(movie);
        });
    }

    @Test
    @Transactional
    @Commit
    public void testAddPoster() {
        MovieEntity movie = movieRepository.getOne(1L);

        movie.addPoster(PosterEntity.builder().fname("극한직업3.jpg").build());

        movieRepository.save(movie);
    }

    @Test
    @Transactional
    @Commit
    public void testRemovePoster() {
        MovieEntity movie = movieRepository.getOne(1L);

        movie.removePoster(2L);

        movieRepository.save(movie);
    }

    @Test
    public void testPagingOne() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<MovieEntity> result = movieRepository.findAll(pageable);

        result.getContent().forEach(m -> {
            log.info(m.getMno());
            log.info(m.getTitle());
            log.info(m.getPosterList().size()); // tbl_poster 테이블에 접근하여 오류가 발생.
            log.info("\n");
        });
    }

    @Test
    public void testPagingTwo() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<MovieEntity> result = movieRepository.findAllTwo(pageable);

        result.getContent().forEach(m -> {
            log.info(m.getMno());
            log.info(m.getTitle());
            log.info(m.getPosterList().size());
            log.info("\n");
        });
    }

    @Test
    public void testPagingTri() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Object[]> result = movieRepository.findAllTri(pageable);

        result.getContent().forEach(arr -> {
            log.info(Arrays.toString(arr));
            log.info("\n");
        });
    }
}
