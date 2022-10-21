package com.study.guestbookreplyproject.repository;

import com.study.guestbookreplyproject.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {
    @Modifying
    @Query("DELETE FROM ReplyEntity r WHERE r.board.bno =:bno")
    void deleteByBno(@Param("bno") Long bno);
}
