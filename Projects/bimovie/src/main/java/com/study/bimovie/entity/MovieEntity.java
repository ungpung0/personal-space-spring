package com.study.bimovie.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "tbl_movie")
public class MovieEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    private String title;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY
            , mappedBy = "movie"         // mappedBy      : 양방향 연관관계에서 주인이 아님을 명시한다.
            , cascade = CascadeType.ALL  // casacde       : 상위 객체가 하위 객체에 전파(Propagate)하는 옵션.
            , orphanRemoval = true)      // orphanRemoval : 참조가 없는 하위 객체를 삭제 옵션.
    private List<PosterEntity> posterList = new ArrayList<>();

    public void addPoster(PosterEntity poster) {
        poster.setIdx(this.posterList.size());
        poster.setMovie(this);
        posterList.add(poster);
    }

    private void changeIdx() {
        for(int i = 0; i < posterList.size(); i++)
            posterList.get(i).setIdx(i);
    }

    public void removePoster(Long ino) {
        Optional<PosterEntity> result = posterList.stream().filter(p -> p.getIno() == ino).findFirst();

        result.ifPresent(p -> {
            p.setMovie(null);     // 참조관계를 끊는다.
            posterList.remove(p); // 객체에서 삭제한다.
        });

        changeIdx(); // 인덱스를 최신화한다.
    }

}
