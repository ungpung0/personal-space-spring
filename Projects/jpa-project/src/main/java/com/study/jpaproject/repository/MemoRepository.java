package com.study.jpaproject.repository;

import com.study.jpaproject.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> { // auto register spring bean.

    List<Memo> findByMemoIdBetweenOrderByMemoIdDesc(Long from, Long to); // query method example, it works accord to the method name.

    Page<Memo> findByMemoIdBetween(Long from, Long to, Pageable pageable); // sort can be replaced with param.

    void deleteMemoByMemoIdLessThan(Long id); // delete query method.
}
