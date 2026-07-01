package org.example.usersystem.service;

import org.example.usersystem.dto.LoginRequest;
import org.example.usersystem.dto.SignupRequest;
import org.example.usersystem.dto.UpdateProfileRequest;
import org.example.usersystem.entity.User;
import org.example.usersystem.exception.InvalidPasswordException;
import org.example.usersystem.exception.InvalidUsernameException;
import org.example.usersystem.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User signup(SignupRequest request) {

        if(request.getUsername().contains(" ")) {
            throw new InvalidUsernameException(
                    "Username cannot contain spaces");
        }

        if(!request.getUsername()
                .equals(request.getUsername().toLowerCase())) {

            throw new InvalidUsernameException(
                    "Username cannot contain capital letters");
        }

        if(request.getPassword().length() < 8) {

            throw new InvalidPasswordException(
                    "Password must be at least 8 characters");
        }

        if(repo.findByUsername(
                request.getUsername()).isPresent()) {

            throw new InvalidUsernameException(
                    "Username already exists");
        }

        User user = new User(
                request.getName(),
                request.getUsername(),
                request.getPassword());

        return repo.save(user);
    }

    public User login(LoginRequest request) {

        User user = repo.findByUsername(
                        request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid username"));

        if(!user.getPassword()
                .equals(request.getPassword())) {

            throw new RuntimeException(
                    "Invalid password");
        }

        return user;
    }

    public User updateProfile(
            Long userId,
            UpdateProfileRequest request) {

        User user = repo.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));

        if(request.getUsername().contains(" ")) {
            throw new InvalidUsernameException(
                    "Username cannot contain spaces");
        }

        if(!request.getUsername()
                .equals(request.getUsername().toLowerCase())) {

            throw new InvalidUsernameException(
                    "Username cannot contain capital letters");
        }

        if(request.getPassword().length() < 8) {

            throw new InvalidPasswordException(
                    "Password must be at least 8 characters");
        }

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        return repo.save(user);
    }

    public void deleteProfile(Long userId) {

        User user = repo.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));

        repo.delete(user);
    }
}