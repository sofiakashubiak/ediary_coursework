package com.example.ediary.models;

import lombok.*;

@Data
@AllArgsConstructor
public class DiaryEditFormResponse {
    private String englishDescription;
    private int englishScore;
    private boolean wasPresentOnEnglish;

    private String physicsDescription;
    private int physicsScore;
    private boolean wasPresentOnPhysics;

    private String programmingDescription;
    private int programmingScore;
    private boolean wasPresentOnProgramming;
}
