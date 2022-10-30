package com.study.movieproject.service;

import com.study.movieproject.dto.MovieDTO;
import com.study.movieproject.dto.MovieImageDTO;
import com.study.movieproject.dto.PageRequestDTO;
import com.study.movieproject.dto.PageResultDTO;
import com.study.movieproject.entity.ImageEntity;
import com.study.movieproject.entity.MovieEntity;
import com.study.movieproject.repository.ImageRepository;
import com.study.movieproject.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor // @RequiredArgsConstructor: final, @NotNull이 붙은 필드의 생성자를 자동으로 생성한다.
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final ImageRepository imageRepository;

    @Transactional
    @Override
    public Long register(MovieDTO movieDTO) {
        // DTO 객체를 Entity 객체로 반환하여 변수에 저장한다.
        Map<String, Object> entityMap = dtoToEntity(movieDTO);
        MovieEntity movieEntity = (MovieEntity) entityMap.get("movie");
        List<ImageEntity> movieImageList = (List<ImageEntity>) entityMap.get("imgList");

        // 레포지토리에 저장한다.
        movieRepository.save(movieEntity);
        movieImageList.forEach(movieImage -> {
            imageRepository.save(movieImage);
        });

        return movieEntity.getMovieId();
    }

    @Override
    public MovieDTO getMovie(Long movieId) {
        List<Object[]> result = movieRepository.getMovieWithAll(movieId);

        MovieEntity movieEntity = (MovieEntity) result.get(0)[0];

        List<ImageEntity> imageEntityList = new ArrayList<>();
        result.forEach(array -> {
            ImageEntity image = (ImageEntity) array[1];
            imageEntityList.add(image);
        });

        Double average = (Double) result.get(0)[2];
        Long reviewCount = (Long) result.get(0)[3];

        return entityToDTO(movieEntity, imageEntityList, average, reviewCount);
    }

    @Override
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("movieId").descending());

        Page<Object[]> result = movieRepository.getListPage(pageable);

        Function<Object[], MovieDTO> function = (array -> entityToDTO(
                (MovieEntity) array[0],
                (List<ImageEntity>) (Arrays.asList((ImageEntity)array[1])),
                (Double) array[2],
                (Long) array[3])
        );

        return new PageResultDTO<>(result, function);
    }
}
