package com.study.guestbookproject.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.study.guestbookproject.dto.GuestbookDTO;
import com.study.guestbookproject.dto.PageRequestDTO;
import com.study.guestbookproject.dto.PageResultDTO;
import com.study.guestbookproject.entity.Guestbook;
import com.study.guestbookproject.entity.QGuestbook;
import com.study.guestbookproject.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> result = guestbookRepository.findById(gno);
        return result.isPresent() ? entityToDto(result.get()) : null; // 엔티티 객체를 DTO 객체로 변환해야 한다.
    }

    @Override
    public void remove(Long gno) {
        guestbookRepository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO guestbookDTO) {
        Optional<Guestbook> result = guestbookRepository.findById(guestbookDTO.getGno());
        if(result.isPresent()) {
            Guestbook guestbook = result.get();
            guestbook.changeTitle(guestbookDTO.getTitle());
            guestbook.changeContent(guestbookDTO.getContent());
            guestbookRepository.save(guestbook);
        }
    }


    private BooleanBuilder getSearch(PageRequestDTO pageRequestDTO) {
        // 검색 타입, 키워드 가져오기.
        String type = pageRequestDTO.getType();
        String keyword = pageRequestDTO.getKeyword();

        // Querydsl 사용하기.
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGuestbook qGuestbook = QGuestbook.guestbook;
        BooleanExpression booleanExpression = qGuestbook.gno.gt(0L);
        booleanBuilder.and(booleanExpression);

        // 검색 조건이 없을 경우 booleanBuilder 사용.
        if(type == null || type.trim().length() == 0)
            return booleanBuilder;

        // 검색 조건 설정하기.
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if(type.contains("t"))
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        if(type.contains("c"))
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        if(type.contains("w"))
            conditionBuilder.or(qGuestbook.writer.contains(keyword));

        // 조건 통합하기.
        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }
    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("gno").descending());
        BooleanBuilder booleanBuilder = getSearch(pageRequestDTO);
        Page<Guestbook> result = guestbookRepository.findAll(booleanBuilder, pageable);

        Function<Guestbook, GuestbookDTO> function = (guestbook -> entityToDto(guestbook));
        return new PageResultDTO<>(result, function);
    }

}
