package com.study.guestbookproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Guestbook extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gno;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 1500, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    // 게시글 수정을 위한 메소드 선언.
    public void changeTitle(String title) {
        this.title = title;
    }
    public void changeContent(String content) {
        this.content = content;
    }

}
