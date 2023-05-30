package com.example.ediary.repository;

import com.example.ediary.models.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Diary findDiaryById(Long id);
}
