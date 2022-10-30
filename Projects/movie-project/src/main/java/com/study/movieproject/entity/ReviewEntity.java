package com.study.movieproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = {"movie", "member"})
public class ReviewEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private int grade;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private MovieEntity movie;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberEntity member;

    public void changeGrade(int grade) {
        this.grade = grade;
    }

    public void changeText(String text) {
        this.text = text;
    }
}
