package com.study.movieproject.repository;

import com.study.movieproject.entity.ImageEntity;
import com.study.movieproject.entity.MovieEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class MovieRepositoryTests {

    @Autowired // 해당 객체의 타입에 해당하는 Bean을 주입한다.
    MovieRepository movieRepository;

    @Autowired
    ImageRepository imageRepository;

    @Transactional // @Transactional: Spring의 트랜잭션 처리를 지원하는 어노테이션. JPA의 update, delete 수행에 필요하다.
    @Commit // @Commit: 트랜잭션 처리가 끝나고 롤백을 방지한다.
    @Test
    public void insertMovies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            // 영화 데이터 삽입.
            MovieEntity movie = MovieEntity.builder().title("Movie" + i).build();
            movieRepository.save(movie);

            // 이미지 데이터 삽입.
            int count = (int)(Math.random() * 5) + 1; // 1 ~ 5개.
            IntStream.rangeClosed(0, count).forEach(j -> {
                ImageEntity image = ImageEntity.builder()
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgName("Image" + j + ".jpg").build();
                imageRepository.save(image);
            });
        });
    }

    @Test
    public void selectListPage() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "movieId"));

        Page<Object[]> result = movieRepository.getListPage(pageRequest);

        for(Object[] object : result.getContent()) {
            System.out.println(Arrays.toString(object));
        }
    }

    @Test
    public void selectMoviesWithAll() {
        List<Object[]> result = movieRepository.getMovieWithAll(94L);

        System.out.println(result);

        for(Object[] object : result) {
            System.out.println(Arrays.toString(object));
        }
    }
}
