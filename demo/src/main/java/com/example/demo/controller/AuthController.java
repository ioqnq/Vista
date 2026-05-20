package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.RegisterForm;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterForm registerForm, Model model) {
        try {
            userService.registerUser(registerForm);
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("registerForm", registerForm);
            return "register";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String showEditProfilePage(Authentication authentication, Model model) {
        if (authentication == null) {
            return "redirect:/login";
        }
        User user = userService.findByEmail(authentication.getName());
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);
        return "edit-profile";
    }

    @PostMapping("/profile/edit")
    public String processProfileUpdate(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            Authentication authentication) {

        if (authentication == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(authentication.getName());
        if (user == null) {
            return "redirect:/login";
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);

        if (profileImage != null && !profileImage.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + "/uploads/profiles/";
            File folder = new File(uploadDir);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String uniqueFilename = UUID.randomUUID().toString() + "_" + profileImage.getOriginalFilename();
            try {
                profileImage.transferTo(new File(uploadDir + uniqueFilename));

                user.setProfileImageUrl("/uploads/profiles/" + uniqueFilename);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload profile picture", e);
            }
        }

        userService.save(user);

        return "redirect:/profile";
    }
}
