package com.example.ediary.controllers;

import com.example.ediary.models.Diary;
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

        model.addAttribute("diary",
                userRepository.findById(id).get().getDiary());
        return "diaryDetails";
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
