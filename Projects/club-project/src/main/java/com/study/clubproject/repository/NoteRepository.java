package com.study.clubproject.repository;

import com.study.clubproject.entity.Note;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    @EntityGraph(attributePaths = "writer", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT n FROM Note n WHERE n.num = :num")
    Optional<Note> getWithWriter(Long num);

    @EntityGraph(attributePaths = {"writer"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT n FROM Note n WHERE n.writer.email = :email")
    List<Note> getList(String email);
}
