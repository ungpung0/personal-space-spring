package com.study.bimovie.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "movie")
@Table(name = "tbl_poster")
public class PosterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ino;

    private String fname;

    private int idx;

    @ManyToOne(fetch = FetchType.LAZY)
    private MovieEntity movie;

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }
}
