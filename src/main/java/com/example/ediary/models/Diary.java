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

    private String mathDescription;
    private int mathScore;


    private String englishDescription;
    private int englishScore;


    private String physicsDescription;
    private int physicsScore;


    private String programmingDescription;
    private int programmingScore;

    @OneToOne(mappedBy = "diary")
    @ToString.Exclude
    private User user;

}
