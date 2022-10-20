package com.study.guestbookreplyproject.repository;

import com.study.guestbookreplyproject.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

}
