package com.study.guestbookreplyproject.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.study.guestbookreplyproject.entity.BoardEntity;
import com.study.guestbookreplyproject.entity.QBoardEntity;
import com.study.guestbookreplyproject.entity.QMemberEntity;
import com.study.guestbookreplyproject.entity.QReplyEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {
    public SearchBoardRepositoryImpl() {
        super(BoardEntity.class); // BoardEntity의 멤버 변수에 접근하기 위함.
    }
    @Override
    public BoardEntity search() {
        log.info("Call search Method");

        QBoardEntity qBoardEntity = QBoardEntity.boardEntity;
        QReplyEntity qReplyEntity = QReplyEntity.replyEntity;
        QMemberEntity qMemberEntity = QMemberEntity.memberEntity;

        JPQLQuery<BoardEntity> jpqlQuery = from(qBoardEntity);
        jpqlQuery.leftJoin(qMemberEntity).on(qBoardEntity.writer.eq(qMemberEntity));
        jpqlQuery.leftJoin(qReplyEntity).on(qReplyEntity.board.eq(qBoardEntity));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(qBoardEntity, qMemberEntity.email, qReplyEntity.count());
        tuple.groupBy(qBoardEntity);

        log.info("JPQL Details\n" + tuple);

        List<Tuple> result = tuple.fetch();

        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        log.info("Call searchPage Method");

        QBoardEntity qBoardEntity = QBoardEntity.boardEntity;
        QReplyEntity qReplyEntity = QReplyEntity.replyEntity;
        QMemberEntity qMemberEntity = QMemberEntity.memberEntity;

        JPQLQuery<BoardEntity> jpqlQuery = from(qBoardEntity);
        jpqlQuery.leftJoin(qMemberEntity).on(qBoardEntity.writer.eq(qMemberEntity));
        jpqlQuery.leftJoin(qReplyEntity).on(qReplyEntity.board.eq(qBoardEntity));
        JPQLQuery<Tuple> tuple = jpqlQuery.select(qBoardEntity, qMemberEntity, qReplyEntity.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression booleanExpression = qBoardEntity.bno.gt(0L);
        booleanBuilder.and(booleanExpression);

        if(type != null) {
            String[] typeArray = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for(String types : typeArray) {
                switch (types) {
                    case "t":
                        conditionBuilder.or(qBoardEntity.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(qMemberEntity.email.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(qBoardEntity.content.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }

        tuple.where(booleanBuilder);

        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.DESC : Order.ASC;
            String property = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(BoardEntity.class, "boardEntity");
            tuple.orderBy(new OrderSpecifier<>(direction, orderByExpression.get(property)));
        });

        tuple.groupBy(qBoardEntity);
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());
        List<Tuple> result = tuple.fetch();

        log.info("Result \n" + result);

        long count = tuple.fetchCount();

        log.info("Count \n" + count);

        return new PageImpl<Object[]>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable, count);
    }
}
