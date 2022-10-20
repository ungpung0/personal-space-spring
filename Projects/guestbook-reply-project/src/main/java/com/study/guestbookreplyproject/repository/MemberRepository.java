package com.study.guestbookreplyproject.repository;

import com.study.guestbookreplyproject.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {

}
