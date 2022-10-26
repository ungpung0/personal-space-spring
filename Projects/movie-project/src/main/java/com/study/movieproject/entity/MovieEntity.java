package com.study.movieproject.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity             // @Entity: 해당 클래스는 JPA가 관리하는 Entity 클래스이다.
@Builder            // @Builder: Lombok의 Builder 패턴을 활용할 수 있다.
@AllArgsConstructor // @AllArgsConstructor: 모든 필드 값을 파라미터로 받는 생성자를 생성한다.
@NoArgsConstructor  // @NoArgsConstructor: 기본 생성자를 생성한다.
@Getter
@ToString           // @ToString: toString()을 자동으로 생성한다. 객체 타입(Object)는 exclude를 통해서 제외할 수 있다.
public class MovieEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    private String title;
}
