package com.study.guestbookproject.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> { // 제네릭 타입 DTO, EN.

    private int totalPage;          // 총 페이지 수.
    private int page;               // 현재 페이지.
    private int size;               // 목록 크기.
    private int start, end;         // 시작 페이지, 끝 페이지.
    private boolean prev, next;     // 이전, 다음 여부.
    private List<DTO> dtoList;      // dto 리스트.
    private List<Integer> pageList; // 페이지 번호 목록.

    // 페이지 목록 생성.
    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();
        int tempEnd = (int)(Math.ceil(page / 10.0)) * 10;
        start = tempEnd - 9;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        next = totalPage > tempEnd;
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

    // Entity 객체를 DTO 타입으로 변환하는 기능을 수행.
    public PageResultDTO(Page<EN> result, Function<EN, DTO> function) {
        dtoList = result.stream().map(function).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable()); // 페이지 목록 메서드 호출.
    }

}
