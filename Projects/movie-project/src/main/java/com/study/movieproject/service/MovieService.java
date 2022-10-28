package com.study.movieproject.service;

import com.study.movieproject.dto.MovieDTO;
import com.study.movieproject.dto.MovieImageDTO;
import com.study.movieproject.entity.ImageEntity;
import com.study.movieproject.entity.MovieEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {
    Long register(MovieDTO movieDTO);

    default Map<String, Object> dtoToEntity(MovieDTO movieDTO) { // Movie, MovieImage 객체를 Map으로 반환한다.
        Map<String, Object> resultEntity = new HashMap<>();

        // MovieEntity 객체에 MovieDTO 객체값을 삽입.
        MovieEntity movie = MovieEntity.builder()
                .movieId(movieDTO.getMovieId())
                .title(movieDTO.getTitle())
                .build();
        resultEntity.put("movie", movie);

        // ImageEntity를 담은 List 객체에 MovieImageDTO 객체값을 삽입.
        List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();
        if(imageDTOList != null && imageDTOList.size() > 0) { // 받아온 DTO가 존재할 경우에만 변환한다.
            List<ImageEntity> imageList = imageDTOList.stream().map(movieImageDTO -> {
                ImageEntity image = ImageEntity.builder()
                        .path(movieImageDTO.getImgPath())
                        .imgName(movieImageDTO.getImgName())
                        .uuid(movieImageDTO.getUuid())
                        .movie(movie)
                        .build();
                return image;
            }).collect(Collectors.toList());
            resultEntity.put("imgList", imageList);
        }

        return resultEntity;
    }

}
