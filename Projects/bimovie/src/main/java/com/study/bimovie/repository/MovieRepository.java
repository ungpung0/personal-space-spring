package com.study.bimovie.repository;

import com.study.bimovie.entity.MovieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    @EntityGraph(attributePaths = "posterList", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT m FROM MovieEntity m")
    Page<MovieEntity> findAllTwo(Pageable pageable);

    @Query("SELECT m, p, COUNT(p) FROM MovieEntity m LEFT JOIN PosterEntity p ON p.movie = m GROUP BY p.movie")
    Page<Object[]> findAllTri(Pageable pageable);

    @Query("SELECT m, p, COUNT(p) FROM MovieEntity m LEFT JOIN PosterEntity p ON p.movie = m WHERE p.idx = 1 GROUP BY p.movie")
    Page<Object[]> findAllFour(Pageable pageable);
}
