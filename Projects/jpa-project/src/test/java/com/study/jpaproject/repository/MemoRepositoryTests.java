package com.study.jpaproject.repository;

import com.study.jpaproject.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest // for integration test.
public class MemoRepositoryTests {

    @Autowired // dependence (Spring bean) injection.
    MemoRepository memoRepository;

    @Test
    public void testClass() {
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    public void testInsertDummies() {
        // insert memoText (sample + 1 ~ 100).
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect() {
        Long mno = 100L;
        Optional<Memo> result = memoRepository.findById(mno); // using Optional, can avoid NPE(NullPointException).

        System.out.println("####################");
        if(result.isPresent()) { // == member != null
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Transactional // for transaction function.
    @Test
    public void testSelect2() {
        Long mno = 100L;
        Memo memo = memoRepository.getOne(mno);
        System.out.println("####################");
        System.out.println(memo);
    }

    @Test
    public void testUpdate() {
        Memo memo = Memo.builder().memoId(100L).memoText("Update Text").build();
        System.out.println(memoRepository.save(memo));
    }

    @Test
    public void testDelete() {
        Long mno = 100L;
        memoRepository.deleteById(mno);
    }

    // paging test
    @Test
    public void testPageDefault() {
        Pageable pageable = PageRequest.of(0, 10); // PageRequest class implements Pageable interface.
        Page<Memo> result = memoRepository.findAll(pageable); // Page<Entity> type.
        System.out.println(result);
        System.out.println("####################");
        System.out.println("Total Pages: " + result.getTotalPages());    // print total pages.
        System.out.println("Total Count: " + result.getTotalElements()); // print page count.
        System.out.println("Page Number: " + result.getNumber());        // print current page number.
        System.out.println("Page Size: " + result.getSize());            // print page size.
        System.out.println("Next Page: " + result.hasNext());            // print has next page? via boolean.
        System.out.println("First Page: " + result.isFirst());           // print is first page? via boolean.
        System.out.println("####################");
        for(Memo memo : result.getContent()) // print current page contents.
            System.out.println(memo);
    }

    @Test
    public void testSort() {
        Sort sort = Sort.by("memoId").descending(); // Sort type.
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Memo> result = memoRepository.findAll(pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Test
    public void testQueryMethod() {
        List<Memo> list = memoRepository.findByMemoIdBetweenOrderByMemoIdDesc(70L, 80L);
        for(Memo memo : list)
            System.out.println(memo);
    }

    @Test
    public void testQueryMethodWithPageable() {
        Sort sort = Sort.by("memoId").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Memo> result = memoRepository.findByMemoIdBetween(10L, 50L, pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Commit // required to check changes via db.
    @Transactional
    @Test
    public void testDeleteQueryMethod() {
        memoRepository.deleteMemoByMemoIdLessThan(10L);
    }

}
