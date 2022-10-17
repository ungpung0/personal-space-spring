package com.study.guestbookproject.service;

import com.study.guestbookproject.dto.GuestbookDTO;
import com.study.guestbookproject.dto.PageRequestDTO;
import com.study.guestbookproject.dto.PageResultDTO;
import com.study.guestbookproject.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    private GuestbookService guestbookService;

    @Test // 회원가입 테스트.
    public void testRegister() {
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("Sample Writer...")
                .build();
        System.out.println(guestbookService.register(guestbookDTO));
    }

    @Test // 목록 테스트 1.
    public void testList1() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
        PageResultDTO<GuestbookDTO, Guestbook> pageResultDTO = guestbookService.getList(pageRequestDTO);

        // 목록 출력.
        for(GuestbookDTO guestbookDTO : pageResultDTO.getDtoList())
            System.out.println(guestbookDTO);
    }

    @Test // 목록 테스트 2.
    public void testList2() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
        PageResultDTO<GuestbookDTO, Guestbook> pageResultDTO = guestbookService.getList(pageRequestDTO);

        // 출력 테스트.
        System.out.println("PREV" + pageResultDTO.isPrev());
        System.out.println("NEXT" + pageResultDTO.isNext());
        System.out.println("TOTAL" + pageResultDTO.getTotalPage());
        System.out.println("-------------------------");
        for(GuestbookDTO guestbookDTO : pageResultDTO.getDtoList())
            System.out.println(guestbookDTO);
        System.out.println("-------------------------");
        pageResultDTO.getPageList().forEach(i -> System.out.println(i));
    }

}
