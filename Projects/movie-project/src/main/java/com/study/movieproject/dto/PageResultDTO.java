package com.study.movieproject.dto;

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
    private int pageTotal;
    private int size;
    private int indexStart;
    private int indexEnd;
    private boolean previous;
    private boolean next;
    private List<DTO> dtoList;
    private List<Integer> pageList;

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber();
        this.size = pageable.getPageSize();
        int tempEnd = (int)(Math.ceil(page / 10.0)) * 10;
        indexStart = tempEnd - 9;
        indexEnd = pageTotal > tempEnd ? tempEnd : pageTotal;
        previous = indexStart > 1;
        next = pageTotal > tempEnd;
        pageList = IntStream.rangeClosed(indexStart, indexEnd).boxed().collect(Collectors.toList());
    }

    public PageResultDTO(Page<EN> result, Function<EN, DTO> function) {
        dtoList = result.stream().map(function).collect(Collectors.toList());
        pageTotal = result.getTotalPages();
        makePageList(result.getPageable());
    }
}
