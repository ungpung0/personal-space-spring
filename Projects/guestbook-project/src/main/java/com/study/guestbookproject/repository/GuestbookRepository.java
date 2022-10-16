package com.study.guestbookproject.repository;

import com.study.guestbookproject.entity.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long>
        , QuerydslPredicateExecutor<Guestbook> { // 추가적으로 Querydsl 인터페이스 상속.

}
