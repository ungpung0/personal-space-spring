package com.study.guestbookreplyproject.service;

import com.study.guestbookreplyproject.dto.ReplyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ReplyServiceTests {
    @Autowired
    private ReplyService replyService;

    @Test
    public void testGetList() {
        Long bno = 90L;

        List<ReplyDTO> replyDTOList = replyService.getList(bno);

        replyDTOList.forEach(replyDTO -> System.out.println(replyDTO));
    }
}
