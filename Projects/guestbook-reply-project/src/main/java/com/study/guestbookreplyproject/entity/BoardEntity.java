package com.study.guestbookreplyproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer") // 지연로딩을 위해서 writer를 제외한다.
public class BoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;
    private String title;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩을 적용한다.
    private MemberEntity writer; // Member와 다대일 관계인 writer. (기본키를 참조)

}
