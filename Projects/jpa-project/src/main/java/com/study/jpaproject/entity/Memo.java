package com.study.jpaproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity                     // this class is an 'entity class'.
@Table(name = "tbl_memo")   // table name.
@ToString                   // auto create toString() method.
@Getter                     // auto create getter() method.
@Builder                    // auto apply builder pattern.
@AllArgsConstructor         // auto create every type param constructor.
@NoArgsConstructor          // auto create non-param constructor.
public class Memo {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // set PK generation strategy.
    private Long memo;

    @Column(length = 200, nullable = false) // set Column with options.
    private String memoText;
}
