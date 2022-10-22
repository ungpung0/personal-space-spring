package com.study.guestbookreplyproject.repository.search;

import com.querydsl.jpa.JPQLQuery;
import com.study.guestbookreplyproject.entity.BoardEntity;
import com.study.guestbookreplyproject.entity.QBoardEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {
    public SearchBoardRepositoryImpl() {
        super(BoardEntity.class); // BoardEntity의 멤버 변수에 접근하기 위함.
    }
    @Override
    public BoardEntity search() {
        log.info("Call search Method");

        QBoardEntity qBoardEntity = QBoardEntity.boardEntity;
        JPQLQuery<BoardEntity> jpqlQuery = from(qBoardEntity);
        jpqlQuery.select(qBoardEntity).where(qBoardEntity.bno.eq(2L));

        log.info("JPQL Details\n" + jpqlQuery);

        List<BoardEntity> result = jpqlQuery.fetch();

        return null;
    }
}
