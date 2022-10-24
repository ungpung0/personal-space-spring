package com.study.guestbookreplyproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")
public class ReplyEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;
    private String text;
    private String replyer;
    @ManyToOne(fetch = FetchType.LAZY)
    private BoardEntity board; // Board와 다대일 관계인 board. (기본키를 참조)
}
