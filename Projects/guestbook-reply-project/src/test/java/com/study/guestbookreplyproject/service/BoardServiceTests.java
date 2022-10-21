package com.study.guestbookreplyproject.service;

import com.study.guestbookreplyproject.dto.BoardDTO;
import com.study.guestbookreplyproject.dto.PageRequestDTO;
import com.study.guestbookreplyproject.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTests {
    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister() {
        BoardDTO boardDTO = BoardDTO.builder()
                .title("TestRegister")
                .content("TestRegister")
                .writerEmail("user55@aaa.com")
                .build();

        Long bno = boardService.register(boardDTO);
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        for(BoardDTO boardDTO : result.getDtoList()) {
            System.out.println(boardDTO);
        }
    }

    @Test
    public void testGetOne() {
        Long bno = 100L;

        BoardDTO boardDTO = boardService.getOne(bno);

        System.out.println(boardDTO);
    }

    @Test
    public void testRemove() {
        Long bno = 1L;

        boardService.removeWithReplies(bno);
    }

    @Test
    public void testModify() {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(2L)
                .title("TestChange")
                .content("TestChange")
                .build();

        boardService.modify(boardDTO);
    }

}
