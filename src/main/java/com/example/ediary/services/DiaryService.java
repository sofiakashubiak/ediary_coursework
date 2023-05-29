package com.example.ediary.services;

import com.example.ediary.models.Diary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiaryService{
    private List<Diary> students = new ArrayList<>();

    {
        students.add(new Diary());
    }

}
