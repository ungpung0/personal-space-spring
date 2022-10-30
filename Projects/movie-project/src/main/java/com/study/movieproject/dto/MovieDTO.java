package com.study.movieproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private Long movieId;

    private String title;

    @Builder.Default // @Builder.Default: 빌더 패턴을 사용하여 인스턴스를 생성할 때 특정 값으로 초기화한다.
    private List<MovieImageDTO> imageDTOList = new ArrayList<>(); // 이미지를 여러개 받을 수 있으므로 List 객체로 받음.

    private double average;

    private int reviewCount;

    private LocalDateTime regDate;

    private LocalDateTime modDate;
}
