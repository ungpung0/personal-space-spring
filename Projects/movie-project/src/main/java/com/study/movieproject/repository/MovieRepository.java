package com.study.movieproject.repository;

import com.study.movieproject.entity.MovieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    // 영화, 별점 평균, 리뷰 횟수를 검색하는 쿼리.
    @Query("select m, i, avg(coalesce(r.grade, 0)), count(distinct r) from MovieEntity m"
            + " left outer join ImageEntity i on i.movie = m"
            + " left outer join ReviewEntity r on r.movie = m group by m") // coalesce(A, B): NULL 값일 경우 B를 반환한다.
    Page<Object[]> getListPage(Pageable pageable);

    // 특정 영화를 검색하는 쿼리.
    @Query("select m, i, avg(coalesce(r.grade, 0)), count(r) "
            + "from MovieEntity m left outer join ImageEntity i on i.movie = m "
            + "left outer join ReviewEntity r on r.movie = m "
            + "where m.movieId = :mid group by i")
    List<Object[]> getMovieWithAll(Long mid);
}