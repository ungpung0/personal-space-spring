package com.study.guestbookproject.service;

import com.study.guestbookproject.dto.GuestbookDTO;
import com.study.guestbookproject.dto.PageRequestDTO;
import com.study.guestbookproject.dto.PageResultDTO;
import com.study.guestbookproject.entity.Guestbook;
import com.study.guestbookproject.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service                 // 비즈니스 로직을 담당하는 서비스 클래스.
@Log4j2
@RequiredArgsConstructor // final, @NotNull 필드의 생성자를 생성하는 어노테이션.
public class GuestbookImplement implements GuestbookService {

    private final GuestbookRepository guestbookRepository; // JPA 처리를 위한 Repository 주입.

    @Override
    public Long register(GuestbookDTO guestbookDTO) {
        log.info("DTO-------------------------");
        log.info(guestbookDTO);

        Guestbook guestbook = dtoToEntity(guestbookDTO); // DTO Entity 변환 메소드 호출.
        log.info(guestbook);

        guestbookRepository.save(guestbook);
        return guestbook.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("gno").descending());
        Page<Guestbook> result = guestbookRepository.findAll(pageable);

        Function<Guestbook, GuestbookDTO> function = (guestbook -> entityToDto(guestbook));
        return new PageResultDTO<>(result, function);
    }
}
