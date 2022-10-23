package com.study.guestbookreplyproject.repository.search;

import com.study.guestbookreplyproject.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface SearchBoardRepository {
    BoardEntity search();

    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
