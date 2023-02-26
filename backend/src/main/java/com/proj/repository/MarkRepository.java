package com.proj.repository;

import com.proj.domain.Mark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    List<Mark> findAllByOrderByCreationTimeDesc();
}
