package com.study.guestbookproject.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass // 객체 입장에서 공통 매핑 정보가 필요할 때 사용한다. 단독으로 테이블을 생성치 않고 상속하여 사용한다.
@EntityListeners(value = {AuditingEntityListener.class}) // 식별자, 날짜와 같은 공통 정보를 감시하여(Auditing) 처리한다.
@Getter
public class BaseEntity {

    @CreatedDate // 최초 등록한 날짜.
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate // 마지막으로 수정한 날짜.
    @Column(name = "moddate")
    private LocalDateTime modDate;
}
