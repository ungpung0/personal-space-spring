package com.study.guestbookreplyproject.repository;

import com.study.guestbookreplyproject.entity.BoardEntity;
import com.study.guestbookreplyproject.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {
    @Modifying // 댓글 삭제 JPQL.
    @Query("DELETE FROM ReplyEntity r WHERE r.board.bno =:bno")
    void deleteByBno(@Param("bno") Long bno);

    // 댓글 조회 JPQL.
    List<ReplyEntity> getReplyEntitiesByBoardOrderByRno(BoardEntity boardEntity);
}
