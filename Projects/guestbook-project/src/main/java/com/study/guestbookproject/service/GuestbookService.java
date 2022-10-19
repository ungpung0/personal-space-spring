package com.study.guestbookproject.service;

import com.study.guestbookproject.dto.GuestbookDTO;
import com.study.guestbookproject.dto.PageRequestDTO;
import com.study.guestbookproject.dto.PageResultDTO;
import com.study.guestbookproject.entity.Guestbook;

public interface GuestbookService {

    Long register(GuestbookDTO guestbookDTO); // 회원가입.

    GuestbookDTO read(Long gno); // 조회.

    void remove(Long gno); // 삭제.

    void modify(GuestbookDTO guestbookDTO); // 수정.

    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO pageRequestDTO);

    // DTO를 Entity로 변환하는 메소드.
    default Guestbook dtoToEntity(GuestbookDTO guestbookDTO) {
        Guestbook guestbook = Guestbook.builder()
                .gno(guestbookDTO.getGno())
                .title(guestbookDTO.getTitle())
                .content(guestbookDTO.getContent())
                .writer(guestbookDTO.getWriter())
                .build();
        return guestbook;
    }

    // Entity를 DTO로 변환하는 메소드.
    default GuestbookDTO entityToDto(Guestbook guestbook) {
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .gno(guestbook.getGno())
                .title(guestbook.getTitle())
                .content(guestbook.getContent())
                .writer(guestbook.getWriter())
                .regDate(guestbook.getRegDate())
                .modDate(guestbook.getModDate())
                .build();
        return guestbookDTO;
    }

}
