package com.study.thymeleafproject.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data                      // @Data: 기본적인 Getter, Setter 메소드를 자동으로 생성한다.
@Builder(toBuilder = true) // @Builder: 객체 생성을 점층적으로 수행하는 Builder 패턴을 자동으로 선언해준다.
public class SampleDTO {

    private Long sampleId;

    private String first;

    private String last;

    private LocalDateTime registerTime;

}
