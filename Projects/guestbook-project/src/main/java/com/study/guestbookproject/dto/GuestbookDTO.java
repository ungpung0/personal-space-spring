package com.study.guestbookproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data // Getter, Setter, ToString 등등.
public class GuestbookDTO {

    private Long gno;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
