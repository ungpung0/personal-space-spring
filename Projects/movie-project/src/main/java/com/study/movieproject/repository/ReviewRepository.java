package com.study.movieproject.repository;

import com.study.movieproject.entity.MemberEntity;
import com.study.movieproject.entity.MovieEntity;
import com.study.movieproject.entity.ReviewEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    // 특정 영화의 리뷰를 검색하는 메소드.
    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH) // @EntityGraph: 특정 속성을 로딩할 때 Fetch 방식을 변경한다. (EAGER LOAD)
    List<ReviewEntity> findByMovie(MovieEntity movie);

    // 특정 멤버와 리뷰를 삭제하는 메소드.
    @Modifying // @Modifying: SELECT를 제외한 쿼리에 사용한다.
    @Query("delete from ReviewEntity r where r.movie = :member") // 여러번 실행으로 낭비되는 자원을 막기 위해서 직접 작성한다.
    void deleteByMember(MemberEntity member);
}