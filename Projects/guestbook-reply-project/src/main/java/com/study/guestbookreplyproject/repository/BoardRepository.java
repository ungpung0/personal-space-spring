package com.study.guestbookreplyproject.repository;

import com.study.guestbookreplyproject.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    // LEFT JOIN 조회 JPQL 예제.
    @Query("SELECT b, w FROM BoardEntity b LEFT JOIN b.writer w WHERE b.bno =:bno")
    Object getBoardWithWriter(@Param("bno") Long bno); // SQL 문장에 파라미터를 전달할 때 사용한다.

    // ON 조회 JPQL 예제.
    @Query("SELECT b, r FROM BoardEntity b LEFT JOIN ReplyEntity r ON r.board = b WHERE b.bno =:bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    // List.html에 필요한 데이터 조회 JPQL 1.
    @Query(value = "SELECT b, w, COUNT(r) "
            + "FROM BoardEntity b "
            + "LEFT JOIN b.writer w "
            + "LEFT JOIN ReplyEntity r ON r.board = b "
            + "GROUP BY b"
            , countQuery = "SELECT COUNT(b) FROM BoardEntity b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    // List.html에 필요한 데이터 조회 JPQL 2.
    @Query("SELECT b, w, COUNT(r) "
            + "FROM BoardEntity b LEFT JOIN b.writer w "
            + "LEFT OUTER JOIN ReplyEntity r ON r.board = b "
            + "WHERE b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);

}
