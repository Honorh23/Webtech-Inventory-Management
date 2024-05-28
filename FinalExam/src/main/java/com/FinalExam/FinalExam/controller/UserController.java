package com.FinalExam.FinalExam.controller;

import com.FinalExam.FinalExam.model.User;
import com.FinalExam.FinalExam.repository.UserRepository;
import com.FinalExam.FinalExam.util.SimplePasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        newUser.setPassword(SimplePasswordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    @GetMapping("/users")
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @PutMapping("/users/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        return userRepository.findById(id).map(user -> {
            user.setEmail(newUser.getEmail());
            user.setPassword(SimplePasswordEncoder.encode(newUser.getPassword()));
            user.setRole(newUser.getRole());
            return userRepository.save(user);
        }).orElseThrow(NoSuchElementException::new);
    }

    @DeleteMapping("/users/{id}")
    String deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException();
        }
        userRepository.deleteById(id);
        return "User deleted successfully";
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody User loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user != null && user.getPassword().equals(SimplePasswordEncoder.encode(loginRequest.getPassword()))) {
            return user;
        } else {
            throw new NoSuchElementException("Invalid credentials");
        }
    }
}
