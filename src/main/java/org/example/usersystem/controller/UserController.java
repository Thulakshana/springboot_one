package org.example.usersystem.controller;

import org.example.usersystem.dto.LoginRequest;
import org.example.usersystem.dto.SignupRequest;
import org.example.usersystem.dto.UpdateProfileRequest;
import org.example.usersystem.entity.User;
import org.example.usersystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/signup")
    public User signup(
            @RequestBody SignupRequest request) {

        return service.signup(request);
    }

    @PostMapping("/login")
    public String login(
            @RequestBody LoginRequest request,
            HttpSession session) {

        User user = service.login(request);

        session.setAttribute("user", user);

        return "Login Successful";
    }

    @GetMapping("/profile")
    public User profile(
            HttpSession session) {

        User user =
                (User) session.getAttribute("user");

        if(user == null) {
            throw new RuntimeException(
                    "Please login first");
        }

        return user;
    }

    @PutMapping("/profile")
    public User updateProfile(
            @RequestBody UpdateProfileRequest request,
            HttpSession session) {

        User user =
                (User) session.getAttribute("user");

        if(user == null) {
            throw new RuntimeException(
                    "Please login first");
        }

        User updatedUser =
                service.updateProfile(
                        user.getId(),
                        request);

        session.setAttribute(
                "user",
                updatedUser);

        return updatedUser;
    }

    @DeleteMapping("/profile")
    public String deleteProfile(
            HttpSession session) {

        User user =
                (User) session.getAttribute("user");

        if(user == null) {
            throw new RuntimeException(
                    "Please login first");
        }

        service.deleteProfile(user.getId());

        session.invalidate();

        return "Profile Deleted";
    }

    @PostMapping("/logout")
    public String logout(
            HttpSession session) {

        session.invalidate();

        return "Logout Successful";
    }
}