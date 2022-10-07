package com.study.jpaproject.repository;

import com.study.jpaproject.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> { // auto register spring bean.
}
