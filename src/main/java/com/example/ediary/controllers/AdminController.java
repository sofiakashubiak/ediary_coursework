package com.example.ediary.controllers;

import com.example.ediary.models.Diary;
import com.example.ediary.models.DiaryEditFormResponse;
import com.example.ediary.models.Team;
import com.example.ediary.models.User;
import com.example.ediary.repository.DiaryRepository;
import com.example.ediary.repository.TeamRepository;
import com.example.ediary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
@RequestMapping("/adminPage")
public class AdminController {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final DiaryRepository diaryRepository;

    @Autowired
    public AdminController(UserRepository userRepository,
                           TeamRepository teamRepository,
                           DiaryRepository diaryRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.diaryRepository = diaryRepository;
    }

    @ModelAttribute("unapprovedUsers")
    public List<User> setUnapprovedUsers() {
        return userRepository.findAllByApproved(false);
    }

    @GetMapping()
    public String adminPage() {
        return "adminPage";
    }

    @GetMapping("/approvedUsers")
    public String approvedUsers(Model model) {
        List<User> approvedUsers = userRepository.findAllByApproved(true);
        model.addAttribute("approvedUsers", approvedUsers);
        return "approvedUsers";
    }

    @GetMapping("/teams")
    public String teams(Model model) {
        List<Team> teams = teamRepository.findAll();
        model.addAttribute("teams", teams);
        return "teamPage";
    }

    @GetMapping("/studentDiaryById/{id}")
    public String studentDiaryById(@PathVariable("id") Long id,
                                   Model model) {
        Diary diary = userRepository.findById(id).get().getDiary();
        System.out.println("\n" + diary + "\n");

        DiaryEditFormResponse diaryEditFormResponse = new DiaryEditFormResponse(
                diary.getEnglishDescription(),
                diary.getEnglishScore(),
                diary.isWasPresentOnEnglish(),
                diary.getPhysicsDescription(),
                diary.getPhysicsScore(),
                diary.isWasPresentOnPhysics(),
                diary.getProgrammingDescription(),
                diary.getProgrammingScore(),
                diary.isWasPresentOnProgramming()
        );


        model.addAttribute("diaryEditFormResponse", diaryEditFormResponse);

        return "editDiary";
    }

    @PostMapping("/studentDiaryById/{id}")
    public String processStudentDiaryEditing(@PathVariable("id") Long id,
                                                @ModelAttribute("diaryEditFormResponse")
                                                 Diary editedDiary) {

        User user = userRepository.findById(id).get();

        Diary diary = user.getDiary();

        diary.setEnglishScore(editedDiary.getEnglishScore());
        diary.setPhysicsScore(editedDiary.getPhysicsScore());
        diary.setProgrammingScore(editedDiary.getProgrammingScore());

        diary.setEnglishDescription(editedDiary.getEnglishDescription());
        diary.setPhysicsDescription(editedDiary.getPhysicsDescription());
        diary.setProgrammingDescription(editedDiary.getProgrammingDescription());

        diary.setWasPresentOnPhysics(editedDiary.isWasPresentOnPhysics());
        diary.setWasPresentOnProgramming(editedDiary.isWasPresentOnProgramming());
        diary.setWasPresentOnEnglish(editedDiary.isWasPresentOnEnglish());

        user.setDiary(diary);
        userRepository.save(user);

        return "redirect:/adminPage/teams";

    }

    @PatchMapping("approveUser/{id}")
    public String approveUser(@PathVariable("id") Long id) {

        User user = userRepository.findById(id).orElseThrow();
        user.setApproved(true);

        Optional<Team> teamObject = teamRepository.findByName(user.getTeamName());

        if (teamObject.isEmpty()) {
            var newTeam = Team.builder()
                    .name(user.getTeamName())
                    .build();
            teamRepository.save(newTeam);
        }

        Diary diary = new Diary();
        diaryRepository.save(diary);
        user.setDiary(diaryRepository.findDiaryById(diary.getId()));

        teamObject = teamRepository.findByName(user.getTeamName());
        user.setTeam(teamObject.get());

        userRepository.save(user);
        return "redirect:/adminPage";
    }

    @DeleteMapping("unApproveUser/{id}")
    public String unApproveUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/adminPage";
    }
}
