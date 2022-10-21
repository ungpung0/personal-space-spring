package com.study.guestbookreplyproject.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {
    private int page;
    private int totalPage;
    private int size;
    private int start;
    private int end;
    private boolean prev;
    private boolean next;
    private List<DTO> dtoList;
    private List<Integer> pageList;
    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();
        int tempEnd = (int)(Math.ceil(page / 10.0)) * 10;
        start = tempEnd - 9;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        prev = start > 1;
        next = totalPage > tempEnd;
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
    public PageResultDTO(Page<EN> result, Function<EN, DTO> function) {
        dtoList = result.stream().map(function).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }
}
