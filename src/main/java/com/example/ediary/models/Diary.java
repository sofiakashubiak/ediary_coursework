package com.example.ediary.models;

import lombok.*;

import javax.persistence.*;


@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String englishDescription;
    private int englishScore;
    private boolean wasPresentOnEnglish;

    private String physicsDescription;
    private int physicsScore;
    private boolean wasPresentOnPhysics;

    private String programmingDescription;
    private int programmingScore;
    private boolean wasPresentOnProgramming;

    @OneToOne(mappedBy = "diary")
    @ToString.Exclude
    private User user;

}
